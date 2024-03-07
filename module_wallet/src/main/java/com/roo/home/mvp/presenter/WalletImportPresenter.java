package com.roo.home.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RetrofitFactory;
import com.roo.core.utils.RxUtils;
import com.roo.home.mvp.contract.WalletImportContract;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class WalletImportPresenter extends BasePresenter<WalletImportContract.Model, WalletImportContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public WalletImportPresenter(WalletImportContract.Model model, WalletImportContract.View rootView) {
        super(model, rootView);
    }

    //获取私钥2
    public void getPrivateKey2(String privateKey) {
        RxUtils.transform(RetrofitFactory.getRetrofit(ApiService.class).getPrivateKey2(privateKey))
                .retryWhen(new RetryWithDelay())
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse t) {
                        LogManage.e(JSON.toJSONString(t));
                        String jsonStr = t.getData().toString();
                        mRootView.importWalletByPK(jsonStr);
                    }

                    @Override
                    public void onError(@NotNull Throwable t) {
                        super.onError(t);
                        LogManage.e(JSON.toJSONString(t.getMessage()));
                    }
                });
    }

    //获取私钥2
    public void getMnemonics2(String mnemonics) {
        RxUtils.transform(RetrofitFactory.getRetrofit(ApiService.class).getMnemonics2(mnemonics))
                .retryWhen(new RetryWithDelay())
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse t) {
                        LogManage.e(JSON.toJSONString(t));
                        String jsonStr = t.getData().toString();
                        jsonStr = jsonStr.replaceAll(", ",",");
                        mRootView.importWalletByMnemonics(jsonStr);
                    }

                    @Override
                    public void onError(@NotNull Throwable t) {
                        super.onError(t);
                        LogManage.e(JSON.toJSONString(t.getMessage()));
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}