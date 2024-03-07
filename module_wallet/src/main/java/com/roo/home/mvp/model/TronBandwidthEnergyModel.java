package com.roo.home.mvp.model;

import android.app.Application;

import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.TransactionResult;
import com.core.domain.trade.BalanceBean;
import com.core.domain.wallet.FreezeBalanceBean;
import com.core.domain.wallet.TronAccountInfo;
import com.core.domain.wallet.TronAccountResource;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.home.mvp.contract.TronBandwidthEnergyContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@ActivityScope
public class TronBandwidthEnergyModel extends BaseModel implements TronBandwidthEnergyContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TronBandwidthEnergyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<TronAccountResource> getAccountResource(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getAccountResource(body)
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse<List<BalanceBean>>> getBalance(String chainCode, String address, ArrayList<String> contractId) {

        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getBalance(chainCode, address, contractId)
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<ResponseBody> freezeBalance(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .freezeBalance(body)
                .retryWhen(new RetryWithDelay());
    }
    @Override
    public Observable<ResponseBody> unFreezeBalance(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .unFreezeBalance(body)
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<TransactionResult> broadcastHex(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .broadcastHex(body)
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<TransactionResult> broadcastTransaction(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .broadcastTransaction(body)
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<TronAccountInfo> getTronAccountInfo(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getTronAccountInfo(body)
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BlockHeader> getNowBlock() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getNowBlock()
                .retryWhen(new RetryWithDelay());
    }
}