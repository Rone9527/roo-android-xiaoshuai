package com.roo.mine.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.core.domain.mine.SysConfigBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.component.Upgrade;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.utils.IntentUtils;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.mine.R;
import com.roo.mine.di.component.DaggerAboutUsComponent;
import com.roo.mine.mvp.contract.AboutUsContract;
import com.roo.mine.mvp.presenter.AboutUsPresenter;
import com.roo.router.Router;

import org.simple.eventbus.EventBus;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class AboutUsActivity extends BaseActivity<AboutUsPresenter> implements AboutUsContract.View {
    public static final String KEY_DOT_VISIBLE = "KEY_DOT_VISIBLE";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAboutUsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    public static void start(Context context, SysConfigBean sysConfigBean, boolean dotVisibility) {
        Router.newIntent(context)
                .to(AboutUsActivity.class)
                .putSerializable(Constants.KEY_DEFAULT, sysConfigBean)
                .putBoolean(KEY_DOT_VISIBLE, dotVisibility)
                .launch();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about_us;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar(getString(R.string.about_us), this);
        SysConfigBean sysConfigBean = (SysConfigBean) getIntent().getSerializableExtra(Constants.KEY_DEFAULT);

        TextView tvVersionName = findViewById(R.id.tvVersionName);
        String versionName = Kits.Package.getVersionName(this);
        tvVersionName.setText(MessageFormat.format("v {0}", versionName));

        findViewById(R.id.ivRoundOrange).setVisibility(
                getIntent().getBooleanExtra(KEY_DOT_VISIBLE, false)
                        ? View.VISIBLE : View.GONE);

        RxView.clicks(findViewById(R.id.llVersion)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    CC.obtainBuilder(Upgrade.NAME)
                            .setContext(this)
                            .addParam(Upgrade.Param.PACKAGE_NAME, getPackageName())
                            .addParam(Upgrade.Param.VERSION_NAME, versionName)
                            .addParam(Upgrade.Param.NEVER_IGNORE, true)
                            .setActionName(Upgrade.Action.check)
                            .build().callAsyncCallbackOnMainThread(new IComponentCallback() {
                        @Override
                        public void onResult(CC cc, CCResult result) {
                            Object paramItem = result.getDataItem(Upgrade.Result.RESULT);
                            if (paramItem instanceof Boolean) {
                                findViewById(R.id.ivRoundOrange).setVisibility((Boolean) paramItem ? View.VISIBLE : View.GONE);
                            } else if (paramItem instanceof String) {
                                String resultStr = (String) paramItem;
                                if ("0".equals(resultStr)) {
                                    ToastUtils.show(R.string.latest_version);
                                }
                            }
                        }
                    });
                });

        for (SysConfigBean.ValueBean valueBean : sysConfigBean.getValue()) {

            if (valueBean.getCode().equals("userAgreement")) {
                findViewById(R.id.llUserAgreement).setVisibility(View.VISIBLE);
                RxView.clicks(findViewById(R.id.llUserAgreement)).throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(o -> {
                            indexToWebView(valueBean);
                        });
            }

            if (valueBean.getCode().equals("privacyAgreement")) {
                findViewById(R.id.llPrivacyPolicy).setVisibility(View.VISIBLE);
                RxView.clicks(findViewById(R.id.llPrivacyPolicy)).throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(o -> {
                            indexToWebView(valueBean);
                        });
            }

            if (valueBean.getCode().equals("website")) {
                findViewById(R.id.llWebsite).setVisibility(View.VISIBLE);
                TextView tvWebsite = findViewById(R.id.tvWebsite);
                tvWebsite.setText(valueBean.getValue());
                RxView.clicks(findViewById(R.id.llWebsite)).throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(o -> {
                            indexToBrowser(valueBean);
                        });
            }

            if (valueBean.getCode().equals("telegram")) {
                findViewById(R.id.llTelegram).setVisibility(View.VISIBLE);
                TextView tvTelegram = findViewById(R.id.tvTelegram);
                tvTelegram.setText(getShortUrl(valueBean.getValue()));


                RxView.clicks(findViewById(R.id.llTelegram)).throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(o -> {
                            indexToBrowser(valueBean);
                        });
            }

            if (valueBean.getCode().equals("github")) {
                findViewById(R.id.llGithub).setVisibility(View.VISIBLE);
                TextView tvGithub = findViewById(R.id.tvGithub);
                tvGithub.setText(getShortUrl(valueBean.getValue()));

                RxView.clicks(findViewById(R.id.llGithub)).throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(o -> {
                            indexToBrowser(valueBean);
                        });
            }

            if (valueBean.getCode().equals("twitter")) {
                findViewById(R.id.llTwitter).setVisibility(View.VISIBLE);
                TextView tvTwitter = findViewById(R.id.tvTwitter);
                tvTwitter.setText(getShortUrl(valueBean.getValue()));

                RxView.clicks(findViewById(R.id.llTwitter)).throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(o -> {
                            indexToBrowser(valueBean);
                        });
            }
        }

    }

    private void indexToWebView(SysConfigBean.ValueBean valueBean) {
        HashMap<String, Object> param = new HashMap<>();
        param.put(Constants.KEY_URL, valueBean.getValue());
        param.put(Constants.KEY_TITLE, valueBean.getName());
        EventBus.getDefault().post(param, EventBusTag.GOTO_WEBVIEW);
    }

    private void indexToBrowser(SysConfigBean.ValueBean valueBean) {
        String url = valueBean.getValue();
        IntentUtils.openUrl(this, url);
    }

    private String getShortUrl(String url) {
        if (url.startsWith("http://")) {
            return url.replace("http://", "");
        }
        if (url.startsWith("https://")) {
            return url.replace("https://", "");
        }
        return url;
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }
}