package com.roo.home.mvp.presenter;

import android.view.View;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.home.mvp.contract.CashierContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class CashierPresenter extends BasePresenter<CashierContract.Model, CashierContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public CashierPresenter(CashierContract.Model model, CashierContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

}