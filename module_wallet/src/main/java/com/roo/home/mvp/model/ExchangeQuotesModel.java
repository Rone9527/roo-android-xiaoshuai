package com.roo.home.mvp.model;

import android.app.Application;

import com.core.domain.trade.QuoteBean;
import com.core.domain.trade.QuoteKlineBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.home.mvp.contract.ExchangeQuotesContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class ExchangeQuotesModel extends BaseModel implements ExchangeQuotesContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public ExchangeQuotesModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<QuoteKlineBean>>> getKline(String baseAsset) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getKline(baseAsset)
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse<List<QuoteBean>>> getPlatform(String baseAsset) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getPlatform(baseAsset)
                .retryWhen(new RetryWithDelay());
    }
}