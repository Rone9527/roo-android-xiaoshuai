package com.roo.trade.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.trade.mvp.contract.DexContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@FragmentScope
public class DexPresenter extends BasePresenter<DexContract.Model, DexContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public DexPresenter(DexContract.Model model, DexContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}