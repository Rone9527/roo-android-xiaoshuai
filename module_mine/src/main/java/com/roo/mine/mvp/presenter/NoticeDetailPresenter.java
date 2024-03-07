package com.roo.mine.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.mine.mvp.contract.NoticeDetailContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class NoticeDetailPresenter extends BasePresenter<NoticeDetailContract.Model, NoticeDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public NoticeDetailPresenter(NoticeDetailContract.Model model, NoticeDetailContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}