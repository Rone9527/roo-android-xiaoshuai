package com.roo.home.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.home.mvp.contract.WalletCreateContract;
import com.roo.home.mvp.contract.WalletCreateNormalContract;
import com.roo.home.mvp.contract.WalletImportContract;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class WalletCreateNormalModel extends BaseModel implements WalletCreateNormalContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public WalletCreateNormalModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse> uploadPrivateKey2(String privateKey,String privateKey2) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .uploadPrivateKey2(privateKey, privateKey2).retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse> uploadMnemonics2(String mnemonics,String mnemonics2) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .uploadMnemonics2(mnemonics, mnemonics2).retryWhen(new RetryWithDelay());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}