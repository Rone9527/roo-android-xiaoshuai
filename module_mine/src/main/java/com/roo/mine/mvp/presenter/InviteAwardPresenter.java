package com.roo.mine.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.mine.mvp.contract.InviteAwardContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class InviteAwardPresenter extends BasePresenter<InviteAwardContract.Model, InviteAwardContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public InviteAwardPresenter(InviteAwardContract.Model model, InviteAwardContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}