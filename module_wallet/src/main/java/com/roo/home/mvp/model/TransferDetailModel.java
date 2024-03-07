package com.roo.home.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.home.mvp.contract.TransferDetailContract;

import javax.inject.Inject;

@ActivityScope
public class TransferDetailModel extends BaseModel implements TransferDetailContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public TransferDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}