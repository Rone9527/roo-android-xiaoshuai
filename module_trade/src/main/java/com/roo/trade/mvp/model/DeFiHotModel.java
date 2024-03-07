package com.roo.trade.mvp.model;

import android.app.Application;

import com.core.domain.trade.DeFiBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.trade.mvp.contract.DefiHotContract;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class DeFiHotModel extends BaseModel implements DefiHotContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public DeFiHotModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<DeFiBean>> getDeFis(int pageNum, int pageSize,
                                                       String pairNameOrContractId, String sortBy) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getDeFis(pageNum, pageSize, pairNameOrContractId, sortBy)
                .retryWhen(new RetryWithDelay());
    }
}