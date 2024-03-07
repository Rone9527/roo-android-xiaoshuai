package com.roo.trade.mvp.model;

import android.app.Application;

import com.core.domain.trade.DeFiDataBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.trade.mvp.contract.SelfChooseContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class SelfChooseModel extends BaseModel implements SelfChooseContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public SelfChooseModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<DeFiDataBean>>> getDeFiSelfChoose(String ids) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getDeFiSelfChoose(ids)
                .retryWhen(new RetryWithDelay());
    }
}