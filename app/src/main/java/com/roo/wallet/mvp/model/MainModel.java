package com.roo.wallet.mvp.model;

import android.app.Application;

import com.core.domain.dapp.JpushUpload;
import com.core.domain.mine.LegalCurrencyBean;
import com.core.domain.mine.MessageSystem;
import com.core.domain.mine.RateBean;
import com.core.domain.mine.TransferRecordBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.wallet.mvp.contract.MainContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<LegalCurrencyBean>>> getLegalList() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getLegalList().retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse> updateJpush(JpushUpload jpushUpload) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .updateJpush(jpushUpload).retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse<RateBean>> getRates() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getRates().retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse> getPrivateKey2(String getPrivateKey) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getPrivateKey2(getPrivateKey).retryWhen(new RetryWithDelay());
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