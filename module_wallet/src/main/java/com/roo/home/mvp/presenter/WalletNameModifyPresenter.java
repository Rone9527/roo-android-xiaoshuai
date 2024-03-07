package com.roo.home.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.home.mvp.contract.WalletNameModifyContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class WalletNameModifyPresenter extends BasePresenter<WalletNameModifyContract.Model, WalletNameModifyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public WalletNameModifyPresenter(WalletNameModifyContract.Model model, WalletNameModifyContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}