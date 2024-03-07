package com.roo.trade.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.trade.mvp.contract.DexContract;

import javax.inject.Inject;

@FragmentScope
public class DexModel extends BaseModel implements DexContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public DexModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}