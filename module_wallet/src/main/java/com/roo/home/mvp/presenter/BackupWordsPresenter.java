package com.roo.home.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.home.mvp.contract.BackupWordsContract;
import com.roo.home.mvp.ui.adapter.BackupWordsAdapter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class BackupWordsPresenter extends BasePresenter<BackupWordsContract.Model, BackupWordsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    BackupWordsAdapter mAdapter;

    @Inject
    public BackupWordsPresenter(BackupWordsContract.Model model, BackupWordsContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}