package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.link.TokenResourceBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.home.R;
import com.roo.home.di.component.DaggerAssetIntroduceComponent;
import com.roo.home.mvp.contract.AssetIntroduceContract;
import com.roo.home.mvp.presenter.AssetIntroducePresenter;
import com.roo.router.Router;

import org.simple.eventbus.EventBus;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 资产介绍
 */

public class AssetIntroduceActivity extends BaseActivity<AssetIntroducePresenter> implements AssetIntroduceContract.View {

    private LinkTokenBean.TokensBean tokensBean;
    private ResourceAdapter adapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAssetIntroduceComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    public static void start(Context context, LinkTokenBean.TokensBean tokensBean) {
        Router.newIntent(context)
                .to(AssetIntroduceActivity.class)
                .putParcelable(Constants.KEY_DEFAULT, tokensBean)
                .launch();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_assetintroduce;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tokensBean = getIntent().getParcelableExtra(Constants.KEY_DEFAULT);
        ViewHelper.initTitleBar(tokensBean.getSymbol().concat(getString(R.string.asset_introduction)), this);
        adapter = new ResourceAdapter();
        ViewHelper.initRecyclerView(adapter, this);
        mPresenter.getResource(tokensBean.getChainCode(), tokensBean.isMain() ? "0x0000000000000000000000000000000000000000" : tokensBean.getContractId());
        adapter.setOnItemClickListener((adapter, view, position) -> {
            HashMap<String, Object> param = new HashMap<>();
            TokenResourceBean.ResourcesDTO resourcesDTO = (TokenResourceBean.ResourcesDTO) adapter.getItem(position);
            param.put(Constants.KEY_URL, resourcesDTO.getUrl());
            EventBus.getDefault().post(param, EventBusTag.GOTO_WEBVIEW);
        });
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);

    }

    @Override
    public void loadData(TokenResourceBean dataSet) {
        setDataForUI(dataSet);
    }

    private void setDataForUI(TokenResourceBean tokenResourceBean) {
        if (tokensBean.isMain()) {
            findViewById(R.id.rlContractAddress).setVisibility(View.GONE);
        } else {
            ((TextView) findViewById(R.id.tvContractAddress)).setText(tokensBean.getContractId());
            RxView.clicks(findViewById(R.id.ivCopy)).throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(o -> {
                        GlobalUtils.copyToClipboard(tokensBean.getContractId(), this);
                        ToastUtils.show(getString(R.string.copy_success));
                    });
        }

        if (Kits.Empty.check(tokenResourceBean.getDetail())) {
            findViewById(R.id.llAboutCoin).setVisibility(View.GONE);
        }

        ((TextView) findViewById(R.id.tvAboutCoin)).setText(MessageFormat.format(getString(R.string.about_coin), tokensBean.getSymbol()));
        if (Kits.Empty.check(tokenResourceBean)) {
            findViewById(R.id.llResource).setVisibility(View.GONE);
            findViewById(R.id.rlBottom).setVisibility(View.GONE);
            return;
        }
        ((TextView) findViewById(R.id.tvIntroduction)).setText(tokenResourceBean.getDetail());
        if (Kits.Empty.check(tokenResourceBean.getResources())) {
            findViewById(R.id.llResource).setVisibility(View.GONE);
        } else {
            adapter.setNewData(tokenResourceBean.getResources());
        }
        if (tokenResourceBean.isUpper()) {
            ((TextView) findViewById(R.id.tvCirculation)).setText(tokenResourceBean.getTotalSupply().concat(getString(R.string.unlimited_supply)));
        } else {
            ((TextView) findViewById(R.id.tvCirculation)).setText(tokenResourceBean.getTotalSupply());
        }
    }

    private static class ResourceAdapter extends BaseQuickAdapter<TokenResourceBean.ResourcesDTO, BaseViewHolderImpl> {
        public ResourceAdapter() {
            super(R.layout.item_token_resource);
        }

        @Override
        protected void convert(BaseViewHolderImpl helper, TokenResourceBean.ResourcesDTO item) {
            helper.setText(R.id.tvName, item.getName());
            if (item.getIcon().startsWith("http") || item.getIcon().startsWith("https")) {
                helper.setImageUrl(R.id.ivLogo, item.getIcon());
            } else {
                switch (item.getIcon()) {
                    case "website":
                        helper.setImageResource(R.id.ivLogo, R.drawable.ic_coin_website);
                        break;
                    case "whitePaper":
                        helper.setImageResource(R.id.ivLogo, R.drawable.ic_white_paper);
                        break;
                    case "facebook":
                        helper.setImageResource(R.id.ivLogo, R.drawable.ic_facebook);
                        break;
                    case "reddit":
                        helper.setImageResource(R.id.ivLogo, R.drawable.ic_reddit);
                        break;
                    case "telegram":
                        helper.setImageResource(R.id.ivLogo, R.drawable.ic_telegram);
                        break;
                    case "github":
                        helper.setImageResource(R.id.ivLogo, R.drawable.ic_github);
                        break;
                    case "twitter":
                        helper.setImageResource(R.id.ivLogo, R.drawable.ic_twitter);
                        break;
                    default:
                        helper.setImageResource(R.id.ivLogo, R.drawable.ic_common_error_pic_circle);
                        break;
                }
            }


        }
    }
}