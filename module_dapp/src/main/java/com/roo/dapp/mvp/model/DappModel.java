package com.roo.dapp.mvp.model;

import android.app.Application;

import com.core.domain.dapp.DappBannerBean;
import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.DappTypeBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.dapp.mvp.contract.DappContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class DappModel extends BaseModel implements DappContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public DappModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<DappBean>>> getDappsHot() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getDappsHot()
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse<List<DappTypeBean>>> getDappType() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getDappType()
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse<List<DappBannerBean>>> getDappBanner(int type) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getBanners(type)
                .retryWhen(new RetryWithDelay());
    }
}