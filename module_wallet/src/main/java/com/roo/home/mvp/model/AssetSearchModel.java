package com.roo.home.mvp.model;

import android.app.Application;

import com.core.domain.mine.WalletBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.home.mvp.contract.AssetSearchContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class AssetSearchModel extends BaseModel implements AssetSearchContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public AssetSearchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<List<WalletBean>>> getWalletList() {
        return null;
    }

    @Override
    public Observable<List<WalletBean>> getWallet(String content) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> onTokenAdd(String chainCode, String address, String contractId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .onTokenAdd(chainCode.toLowerCase(), address, contractId)
                .retryWhen(new RetryWithDelay());
    }
}