package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.link.TokenResourceBean;
import com.core.domain.manager.ChainCode;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.di.component.AppComponent;
import com.lzh.easythread.AsyncCallback;
import com.roo.core.ComponentApplication;
import com.roo.core.app.api.ApiService;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.ui.dialog.AddLinkAssetDialog;
import com.roo.core.ui.widget.DialogLoading;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RetrofitFactory;
import com.roo.core.utils.ThreadManager;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TokenManager;
import com.roo.core.utils.utils.Web3jUtil;
import com.roo.home.R;
import com.roo.home.mvp.ui.dialog.ChooseLinkDialog;
import com.roo.router.Router;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import retrofit2.Call;

public class AddCustomAssetActivity extends I18nActivityArms {
    public static final int REQUEST_CODE_CUSTOMASSET = 6522;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(AddCustomAssetActivity.class)
                .requestCode(REQUEST_CODE_CUSTOMASSET)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_custom_asset;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar(R.string.add_auto_token, this);

        EditText edInput = findViewById(R.id.edInput);
        TextView tvConfirm = findViewById(R.id.tvConfirm);
        TextView tvLink = findViewById(R.id.tvLink);
        RxView.clicks(findViewById(R.id.layoutLink)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    ChooseLinkDialog.newInstance(Kits.Text.getText(tvLink))
                            .setOnClickListener((linkBean) -> {
                                tvLink.setText(linkBean.getCode());
                            }).show(getSupportFragmentManager(), getClass().getSimpleName());
                });

        InitialValueObservable<CharSequence> observableContractId = RxTextView.textChanges(edInput);
        InitialValueObservable<CharSequence> observableChain = RxTextView.textChanges(tvLink);

        Observable.combineLatest(observableContractId, observableChain, (s1, s2) -> {
            return !Kits.Empty.check(s1) && !Kits.Empty.check(s2);
        }).subscribe(new ErrorHandleSubscriber<Boolean>(ComponentApplication.getRxErrorHandler()) {
            @Override
            public void onNext(@NotNull Boolean verify) {
                tvConfirm.setEnabled(verify);
            }
        });

        RxView.clicks(tvConfirm).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    String chainCode = Kits.Text.getText(tvLink).toUpperCase();
                    String contractId = Kits.Text.getText(edInput);
                    DialogLoading.getInstance().showDialog(this);
                    ThreadManager.getFile().async(new Callable<LinkTokenBean.TokensBean>() {
                        @Override
                        public LinkTokenBean.TokensBean call() throws Exception {

                            switch (chainCode) {
                                case ChainCode.ETH:
                                case ChainCode.BSC:
                                case ChainCode.HECO:
                                case ChainCode.OEC:
                                case ChainCode.FANTOM:
                                case ChainCode.POLYGON: {
                                    String symbol = Web3jUtil.getInstance().getSymbol(chainCode, contractId);
                                    String decimal = Web3jUtil.getInstance().getDecimal("",
                                            chainCode, contractId);
                                    LinkTokenBean.TokensBean value = new LinkTokenBean.TokensBean();
                                    value.setChainCode(chainCode);
                                    value.setContractId(contractId);
                                    value.setName(symbol);
                                    value.setNameEn(symbol);
                                    value.setSymbol(symbol);
                                    value.setDecimals(Integer.parseInt(decimal));

                                    LogManage.d("自定义代币 LinkTokenBean.TokensBean >>>>>>>>" + value);
                                    return value;
                                }
                                case ChainCode.BTC:
                                    return null;
                                case ChainCode.TRON:
                                    ApiService retrofit = RetrofitFactory.getRetrofit(ApiService.class);
                                    Call<BaseResponse<TokenResourceBean>> tron = retrofit.getBlockChain(chainCode, contractId);
                                    BaseResponse<TokenResourceBean> tokenResourceBeanBaseResponse = null;
                                    try {
                                        tokenResourceBeanBaseResponse = tron.execute().body();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    TokenResourceBean data = tokenResourceBeanBaseResponse.getData();
                                    LinkTokenBean.TokensBean value = new LinkTokenBean.TokensBean();
                                    value.setChainCode(chainCode);
                                    value.setContractId(contractId);
                                    value.setName(data.getSymbol());
                                    value.setNameEn(data.getSymbol());
                                    value.setSymbol(data.getSymbol());
                                    value.setDecimals(data.getDecimals());
                                    return value;
                                default:
                                    break;
                            }
                            return null;
                        }
                    }, new AsyncCallback<LinkTokenBean.TokensBean>() {
                        @Override
                        public void onSuccess(LinkTokenBean.TokensBean tokensBean) {
                            DialogLoading.getInstance().closeDialog();
                            if (TokenManager.getInstance().isTokenHasSelected(tokensBean)) {
                                ToastUtils.show(R.string.toast_token_added);
                            } else {
                                AddLinkAssetDialog.newInstance(tokensBean.getSymbol())
                                        .setOnClickListener(() -> {
                                            Intent data = new Intent();
                                            data.putExtra(Constants.KEY_DEFAULT, tokensBean);
                                            setResult(RESULT_OK, data);
                                            addOrDeleteAsset(tokensBean);
                                            finish();
                                        }).show(getSupportFragmentManager(), getClass().getSimpleName());
                            }
                        }

                        @Override
                        public void onFailed(Throwable t) {
                            DialogLoading.getInstance().closeDialog();
                            AddLinkAssetDialog.newInstance(null)
                                    .show(getSupportFragmentManager(), getClass().getSimpleName());
                        }
                    });

        });
    }
    private void addOrDeleteAsset(LinkTokenBean.TokensBean tokensBean) {
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);

        ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
        if (TokenManager.getInstance().isTokenHasSelected(tokensBean)) {
            if (tokensBean.isMain()) {
                ToastUtils.show(R.string.toast_main_forbidden_delete);
                return;
            }
            Iterator<LinkTokenBean.TokensBean> iterator = tokenList.iterator();
            while (iterator.hasNext()) {
                LinkTokenBean.TokensBean next = iterator.next();
                if (Objects.equals(next.getChainCode(), tokensBean.getChainCode())
                        && next.getSymbol().equalsIgnoreCase(tokensBean.getSymbol())) {
                    iterator.remove();
                    break;
                }
            }
        } else {
            tokenList.add(tokensBean);

            ArrayList<LinkTokenBean.NodesBean> chainNode = TokenManager.getInstance().getChainNode();
            if (!Kits.Empty.check(chainNode)) {
                userWallet.setNodes(chainNode);
            }

        }
        userWallet.setTokenList(tokenList);
        EthereumWalletUtils.getInstance().updateWallet(this, userWallet, false);
    }
}
