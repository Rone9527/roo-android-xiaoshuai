package com.roo.webview.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aries.ui.view.title.TitleBarView;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.di.component.AppComponent;
import com.roo.core.app.component.WebView;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.router.Router;
import com.roo.webview.R;
import com.roo.webview.dialog.DialogMore;
import com.roo.webview.fragment.WebFragment;

import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends I18nActivityArms {

    private WebFragment fragment;
    private TitleBarView titleBarView;
    private boolean hasDefinedTitle;

    public static void start(Context context, HashMap<String, Object> params) {
        Router.newIntent(context)
                .to(WebViewActivity.class)
                .putSerializable(WebView.Param.map, params)
                .launch();
    }

    @Override
    public void onBackPressed() {
        boolean onKeyDown = fragment.onKeyDown(KeyEvent.KEYCODE_BACK);
        if (!onKeyDown) {
            super.onBackPressed();
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_webview;
    }

    public TitleBarView getTitleBarView() {
        return titleBarView;
    }

    public boolean isHasDefinedTitle() {
        return hasDefinedTitle;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        titleBarView = ViewHelper.initTitleBar("", this);
        titleBarView.setTitleMainTextMarquee(true);

        titleBarView.getLinearLayout(Gravity.START).removeAllViews();

        titleBarView.addLeftAction(titleBarView.new ImageAction(R.drawable.ic_common_back_black,
                v -> onKeyDown(KeyEvent.KEYCODE_BACK)));

        titleBarView.addLeftAction(titleBarView.new ImageAction(R.drawable.ic_fragment_close,
                v -> finish()));

        HashMap<String, Object> arg = (HashMap<String, Object>) getIntent().getSerializableExtra(WebView.Param.map);
        if (Kits.Empty.check(arg)) {
            finish();
            return;
        }

        String key_fr = (String) arg.get(Constants.KEY_FR);
        if (!Kits.Empty.check(key_fr)) {
            switch (key_fr) {
                case Constants.WEBVIEW_FROM_DAPP_BANNER:
                    titleBarView.addRightAction(titleBarView.new ImageAction(getDrawable(R.drawable.icon_more),
                            v -> {
                                DialogMore.newInstance().setOnClickedListener(new DialogMore.OnClickedListener() {
                                    @Override
                                    public void onRefresh() {
                                        fragment.getmWebView().reload();
                                    }

                                    @Override
                                    public void onCopyLink() {
                                        String url = (String) arg.get(Constants.KEY_URL);

                                        Map<String, String> urlParams = GlobalUtils.getUrlParams(url);
                                        GlobalUtils.copyToClipboard(url, WebViewActivity.this);
                                        ToastUtils.show(getString(R.string.copy_success));
                                    }

                                    @Override
                                    public void onShare() {
                                        String url = (String) arg.get(Constants.KEY_URL);
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_SEND);
                                        intent.putExtra(Intent.EXTRA_TEXT, url);
                                        intent.setType("text/plain");
                                        //设置分享列表的标题，并且每次都显示分享列表
                                        startActivity(Intent.createChooser(intent, getString(R.string.string_share_to)));
                                    }

                                    @Override
                                    public void onDismiss() {
                                    }
                                }).show(getSupportFragmentManager(), getClass().getSimpleName());
                            }));
                    break;
            }
        }


        String url = (String) arg.get(Constants.KEY_URL);

        Map<String, String> urlParams = GlobalUtils.getUrlParams(url);

        ImmersionBar with = ImmersionBar.with(this);
        if ("false".equals(urlParams.get("statusBarColorWhite"))) {
            with.statusBarDarkFont(false);
        } else {
            with.statusBarDarkFont(true);
        }
        with.init();

        String title = (String) arg.get(Constants.KEY_TITLE);
        if (!TextUtils.isEmpty(title)) {
            titleBarView.setTitleMainText(title);
            hasDefinedTitle = true;
        }

        fragment = WebFragment.newInstance(url);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment).commit();
    }

    public boolean onKeyDown(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (fragment.getmWebView().canGoBack()) {
                fragment.getmWebView().goBack();
                return true;
            } else {
                finishAfterTransition();
            }
        }
        return false;
    }
}
