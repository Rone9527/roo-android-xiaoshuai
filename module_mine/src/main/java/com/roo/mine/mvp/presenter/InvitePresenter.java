package com.roo.mine.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.mine.mvp.contract.InviteContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class InvitePresenter extends BasePresenter<InviteContract.Model, InviteContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public InvitePresenter(InviteContract.Model model, InviteContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}