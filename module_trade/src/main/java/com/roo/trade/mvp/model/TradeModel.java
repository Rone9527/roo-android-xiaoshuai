package com.roo.trade.mvp.model;

import android.app.Application;

import com.core.domain.trade.TickerBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.trade.mvp.contract.TradeContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class TradeModel extends BaseModel implements TradeContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public TradeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<TickerBean>>> getTicks() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getTicks().retryWhen(new RetryWithDelay());
    }

}