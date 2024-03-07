package com.roo.dapp.mvp.model;

import android.app.Application;

import com.core.domain.mine.TransactionResult;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.dapp.mvp.contract.DappBrowserTronContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

@FragmentScope
public class DappBrowserTronModel extends BaseModel implements DappBrowserTronContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DappBrowserTronModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<TransactionResult> broadcastTransaction(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .broadcastTransaction(body)
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<ResponseBody> TriggerSmartContract(RequestBody body) {
        return null;
    }
}