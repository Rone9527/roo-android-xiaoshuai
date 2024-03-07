package com.roo.home.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.home.mvp.contract.MyAssetsContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class MyAssetsPresenter extends BasePresenter<MyAssetsContract.Model, MyAssetsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MyAssetsPresenter(MyAssetsContract.Model model, MyAssetsContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}