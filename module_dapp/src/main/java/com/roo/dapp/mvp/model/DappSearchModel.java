package com.roo.dapp.mvp.model;

import android.app.Application;

import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.DappTypeBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.dapp.mvp.contract.DappSearchContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class DappSearchModel extends BaseModel implements DappSearchContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public DappSearchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<DappBean>>> getDapps(String keyword) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getDapps(keyword)
                .retryWhen(new RetryWithDelay());
    }
    @Override
    public Observable<BaseResponse<List<DappTypeBean>>> getDappType() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getDappType()
                .retryWhen(new RetryWithDelay());
    }
}