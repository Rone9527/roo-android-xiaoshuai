package com.roo.dapp.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.billy.cc.core.component.CC;
import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.DappHistoryBean;
import com.core.domain.link.LinkTokenBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.dialog.AddLinkHintDialog;
import com.roo.core.ui.dialog.DappTipDialog;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.KeyboardUtil;
import com.roo.core.utils.Kits;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.dapp.R;
import com.roo.dapp.di.component.DaggerDappSearchComponent;
import com.roo.dapp.mvp.contract.DappSearchContract;
import com.roo.dapp.mvp.presenter.DappSearchPresenter;
import com.roo.dapp.mvp.ui.adapter.DappSearchAdapter;
import com.roo.dapp.mvp.ui.dialog.ChooseLinkDialog;
import com.roo.dapp.mvp.utils.DappSearchManager;
import com.roo.router.Router;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class DappSearchActivity extends BaseActivity<DappSearchPresenter> implements DappSearchContract.View {
    @Inject
    DappSearchAdapter mAdapter;
    private RecyclerView recyclerView;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(DappSearchActivity.class)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDappSearchComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_dapp_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar("", this).setVisibility(View.GONE);
        int barHeight = DeviceUtils.getStatuBarHeight(this);
        findViewById(R.id.layoutRoot).setPadding(0, barHeight, 0, 0);
        EditText editText = findViewById(R.id.editText);

        RxView.clicks(findViewById(R.id.ivBack)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> finish());

        ImageView ivCancel = findViewById(R.id.ivCancel);
        RxView.clicks(ivCancel).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> editText.setText(""));
        TextView tvIndexUrl = findViewById(R.id.tvIndexUrl);

        RxTextView.textChanges(editText).skipInitialValue()
                .debounce(200, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applySchedulers())
                .map(CharSequence::toString)
                .subscribe(t -> {
                    String text = Kits.Text.getText(editText);
                    if (TextUtils.isEmpty(text)) {
                        ivCancel.setVisibility(View.INVISIBLE);
                    } else {
                        //if (URLUtil.isHttpsUrl(text) || URLUtil.isHttpUrl(text)) {
                        findViewById(R.id.layoutIndexUrl).setVisibility(View.VISIBLE);
                        tvIndexUrl.setText(text);
                        //} else {
                        //    findViewById(R.id.layoutIndexUrl).setVisibility(View.GONE);
                        //}
                        ivCancel.setVisibility(View.VISIBLE);
                        mPresenter.getLocalDapp(text);
                    }
                });
        RxView.clicks(tvIndexUrl).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    onUrlClick(Kits.Text.getTextTrim(tvIndexUrl));
                });

        editText.setOnEditorActionListener((text, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
                KeyboardUtil.hideKeyboard(editText);
                mPresenter.getDapps(Kits.Text.getTextTrim(editText));
                return true;
            }
            return false;
        });

        recyclerView = ViewHelper.initRecyclerView(mAdapter, this);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (ClickUtil.clickInFronzen()) {
                return;
            }
            int id = view.getId();
            if (id == R.id.layoutRootHistory) {
                DappHistoryBean item = (DappHistoryBean) mAdapter.getItem(position);
                editText.setText(item.getName());
            }
            if (id == R.id.ivDeleteAll) {

                DappSearchManager.getInstance().remove();
                mPresenter.getSearchHistoryDapp();

            } else if (id == R.id.ivDelete) {

                DappHistoryBean item = (DappHistoryBean) mAdapter.getItem(position);
                DappSearchManager.getInstance().remove(item.getName());
                mPresenter.getSearchHistoryDapp();

            } else if (id == R.id.layoutRoot) {

                DappBean item = (DappBean) mAdapter.getItem(position);
                UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(DappSearchActivity.this);
                ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
                if (!userWallet.getListMainChainCode().contains(item.getChain())) {
                    AddLinkHintDialog.newInstance(item.getChain()).setOnClickListener(() -> {
                        CC.obtainBuilder(Wallet.NAME)
                                .setContext(this)
                                .setActionName(Wallet.Action.AssetSelectActivity)
                                .build().call();
                    }).show(getSupportFragmentManager(), getClass().getSimpleName());
                    return;
                }
                for (LinkTokenBean.TokensBean tokensBean : tokenList) {
                    if (tokensBean.getChainCode().equalsIgnoreCase(item.getChain())) {
                        if (SharedPref.getBoolean(Constants.KEY_SHOW_DAPP_TIPS, false)) {
                            DappHostActivity.start(DappSearchActivity.this, tokensBean, item);
                            mPresenter.saveHistory();

                        } else {

                            DappTipDialog.newInstance(item.getName(), item.getIcon())
                                    .setOnClickListener(() -> {
                                        DappHostActivity.start(DappSearchActivity.this, tokensBean, item);
                                        mPresenter.saveHistory();
                                    }).show(getSupportFragmentManager(), getClass().getSimpleName());
                        }
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    private void onUrlClick(String url) {
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);
        if (null == userWallet) {
            ToastUtils.show(getString(R.string.tip_no_wallet_here));
            return;
        }
        ChooseLinkDialog.newInstance().setOnClickListener((item) -> {
            if (userWallet.getListMainChainCode().contains(item.getCode())) {
                for (LinkTokenBean.TokensBean tokensBean : userWallet.getTokenList()) {
                    if (tokensBean.getChainCode().equalsIgnoreCase(item.getCode())) {
                        DappHostActivity.start(DappSearchActivity.this, tokensBean, url);
                        break;
                    }
                }
            } else {
                ToastUtils.show(getString(R.string.toast_null_chain_wallet));
            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());


    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}