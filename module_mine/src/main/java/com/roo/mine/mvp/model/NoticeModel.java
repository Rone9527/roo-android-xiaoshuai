package com.roo.mine.mvp.model;

import android.app.Application;

import com.core.domain.mine.MessageSystem;
import com.core.domain.mine.TransferRecordBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.mine.mvp.contract.NoticeContract;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class NoticeModel extends BaseModel implements NoticeContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public NoticeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<TransferRecordBean.DataBean>> getTxDetail(String chainCode, String txId, String index) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getTransactionSingle(chainCode.toLowerCase(), txId, index).retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse<MessageSystem.DataBean>> getSystemMessage(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getSystemMessage(id).retryWhen(new RetryWithDelay());
    }
}