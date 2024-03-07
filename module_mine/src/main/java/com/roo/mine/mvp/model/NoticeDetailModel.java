package com.roo.mine.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.mine.mvp.contract.NoticeDetailContract;

import javax.inject.Inject;

@ActivityScope
public class NoticeDetailModel extends BaseModel implements NoticeDetailContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public NoticeDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}