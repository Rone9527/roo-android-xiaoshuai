package com.roo.home.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.home.mvp.contract.TransferDetailContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class TransferDetailPresenter extends BasePresenter<TransferDetailContract.Model, TransferDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public TransferDetailPresenter(TransferDetailContract.Model model, TransferDetailContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}