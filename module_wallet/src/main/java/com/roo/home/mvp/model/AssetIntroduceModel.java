package com.roo.home.mvp.model;

import android.app.Application;

import com.core.domain.link.TokenResourceBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.home.mvp.contract.AssetIntroduceContract;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class AssetIntroduceModel extends BaseModel implements AssetIntroduceContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public AssetIntroduceModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<TokenResourceBean>> getResource(String chainCode, String contractId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getResource(chainCode, contractId)
                .retryWhen(new RetryWithDelay());
    }
}