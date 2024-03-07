package com.roo.trade.mvp.model;

import android.app.Application;

import com.core.domain.trade.FinanceBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.trade.mvp.contract.FinanceContract;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class FinanceModel extends BaseModel implements FinanceContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public FinanceModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<FinanceBean>> getFinancial(String ascription, String chainCode, String name, int pageNum, int pageSize, String tag) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getFinancial(ascription, chainCode, name, pageNum, pageSize, tag)
                .retryWhen(new RetryWithDelay());
    }
}