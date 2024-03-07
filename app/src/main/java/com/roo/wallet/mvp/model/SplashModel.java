package com.roo.wallet.mvp.model;

import android.app.Application;

import com.core.domain.link.LinkTokenBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.wallet.mvp.contract.SplashContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class SplashModel extends BaseModel implements SplashContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public SplashModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<LinkTokenBean>>> getLinkListCoin() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getLinkListCoin().retryWhen(new RetryWithDelay());
    }

}