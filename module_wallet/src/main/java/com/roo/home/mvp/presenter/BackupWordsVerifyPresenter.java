package com.roo.home.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.home.mvp.contract.BackupWordsVerifyContract;
import com.roo.home.mvp.ui.adapter.BackupWordsVerifyAdapter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class BackupWordsVerifyPresenter extends BasePresenter<BackupWordsVerifyContract.Model, BackupWordsVerifyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    BackupWordsVerifyAdapter mAdapter;

    @Inject
    public BackupWordsVerifyPresenter(BackupWordsVerifyContract.Model model, BackupWordsVerifyContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAdapter = null;
    }
}