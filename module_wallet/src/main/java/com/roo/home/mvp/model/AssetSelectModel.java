package com.roo.home.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.home.mvp.contract.AssetSelectContract;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class AssetSelectModel extends BaseModel implements AssetSelectContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public AssetSelectModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> onTokenAdd(String chainCode, String address, String contractId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .onTokenAdd(chainCode.toLowerCase(),address, contractId)
                .retryWhen(new RetryWithDelay());
    }
}