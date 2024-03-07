package com.roo.trade.mvp.model;

import android.app.Application;

import com.core.domain.trade.DeFiDetailBean;
import com.core.domain.trade.DeFiPriceChartBean;
import com.core.domain.trade.DeFiTradeBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.trade.mvp.contract.DeFiDetailActivityContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class DeFiDetailActivityModel extends BaseModel implements DeFiDetailActivityContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public DeFiDetailActivityModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<DeFiDetailBean>> getDeFiDetail(String ascription, String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getDeFiDetail(ascription, id)
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse<List<DeFiTradeBean>>> getDeFiTradeRecent(String ascription, String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getDeFiTradeRecent(ascription, id)
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse<List<DeFiPriceChartBean>>> getDeFiPriceChart(String ascription, String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getDeFiPriceChart(ascription, id)
                .retryWhen(new RetryWithDelay());
    }
}