package com.roo.dapp.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.dapp.mvp.contract.DappFavoriteContract;

import javax.inject.Inject;

@ActivityScope
public class DappFavoriteModel extends BaseModel implements DappFavoriteContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public DappFavoriteModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}