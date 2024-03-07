package com.roo.home.mvp.model;

import android.app.Application;

import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.TransactionResult;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.home.mvp.contract.TransferTronContract;

import org.json.JSONException;
import org.json.JSONObject;
import org.tron.common.utils.ByteArray;
import org.tron.walletserver.WalletApi;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

@ActivityScope
public class TransferTronModel extends BaseModel implements TransferTronContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TransferTronModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BlockHeader> getNowBlock() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getNowBlock()
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<TransactionResult> broadcastTransaction(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .broadcastTransaction(body)
                .retryWhen(new RetryWithDelay());
    }



    public Observable<ResponseBody> createTransactionTre20(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .TriggerSmartContract(body)
                .retryWhen(new RetryWithDelay());
    }

    public Observable<ResponseBody> createTransactionTre10(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .transferAsset(body)
                .retryWhen(new RetryWithDelay());
    }

}