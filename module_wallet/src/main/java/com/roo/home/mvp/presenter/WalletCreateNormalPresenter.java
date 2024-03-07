package com.roo.home.mvp.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
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
import com.roo.home.mvp.contract.WalletCreateContract;
import com.roo.home.mvp.contract.WalletCreateNormalContract;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class WalletCreateNormalPresenter extends BasePresenter<WalletCreateNormalContract.Model, WalletCreateNormalContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public WalletCreateNormalPresenter(WalletCreateNormalContract.Model model, WalletCreateNormalContract.View rootView) {
        super(model, rootView);
    }

    //获取私钥2
    public void uploadPrivateKey2(String privateKey,String privateKey2) {
        RxUtils.transform(RetrofitFactory.getRetrofit(ApiService.class).uploadPrivateKey2(privateKey,privateKey2))
                .retryWhen(new RetryWithDelay())
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse t) {
                        LogManage.e(JSON.toJSONString(t));
                        mRootView.createWalletEnd();
                    }

                    @Override
                    public void onError(@NotNull Throwable t) {
                        super.onError(t);
                        LogManage.e(JSON.toJSONString(t.getMessage()));
                    }
                });
    }

    //获取私钥2
    public void uploadMnemonics2(String mnemonics,String mnemonics2) {
        RxUtils.transform(RetrofitFactory.getRetrofit(ApiService.class).uploadMnemonics2(mnemonics,mnemonics2))
                .retryWhen(new RetryWithDelay())
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse t) {
                        LogManage.e(JSON.toJSONString(t));
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