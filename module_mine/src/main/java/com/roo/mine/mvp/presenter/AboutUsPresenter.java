package com.roo.mine.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.mine.mvp.contract.AboutUsContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class AboutUsPresenter extends BasePresenter<AboutUsContract.Model, AboutUsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public AboutUsPresenter(AboutUsContract.Model model, AboutUsContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}