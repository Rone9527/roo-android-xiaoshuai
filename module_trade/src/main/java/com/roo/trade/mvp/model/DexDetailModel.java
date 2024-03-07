package com.roo.trade.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.trade.mvp.contract.DexDetailContract;

import javax.inject.Inject;

@ActivityScope
public class DexDetailModel extends BaseModel implements DexDetailContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public DexDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}