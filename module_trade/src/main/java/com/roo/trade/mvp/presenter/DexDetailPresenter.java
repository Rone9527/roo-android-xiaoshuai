package com.roo.trade.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.trade.mvp.contract.DexDetailContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class DexDetailPresenter extends BasePresenter<DexDetailContract.Model, DexDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public DexDetailPresenter(DexDetailContract.Model model, DexDetailContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}