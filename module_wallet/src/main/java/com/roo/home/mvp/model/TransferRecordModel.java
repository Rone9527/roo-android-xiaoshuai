package com.roo.home.mvp.model;

import android.app.Application;

import com.core.domain.mine.TransferRecordBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.home.mvp.contract.TransferRecordContract;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class TransferRecordModel extends BaseModel implements TransferRecordContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public TransferRecordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<TransferRecordBean>>
    getTransaction(String chainCode, String address,
                   String contractId, int pageNum, int pageSize) {

        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getTransaction(chainCode, address, contractId, pageNum, pageSize)
                .retryWhen(new RetryWithDelay());
    }
}