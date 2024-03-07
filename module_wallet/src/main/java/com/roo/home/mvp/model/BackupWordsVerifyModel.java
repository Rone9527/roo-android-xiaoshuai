package com.roo.home.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.home.mvp.contract.BackupWordsVerifyContract;

import javax.inject.Inject;

@ActivityScope
public class BackupWordsVerifyModel extends BaseModel implements BackupWordsVerifyContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public BackupWordsVerifyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}