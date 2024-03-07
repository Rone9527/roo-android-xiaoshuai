package com.roo.trade.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.trade.mvp.contract.MarketContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@FragmentScope
public class MarketPresenter extends BasePresenter<MarketContract.Model, MarketContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MarketPresenter(MarketContract.Model model, MarketContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}