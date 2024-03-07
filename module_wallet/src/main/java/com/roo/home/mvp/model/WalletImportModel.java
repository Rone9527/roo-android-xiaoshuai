package com.roo.home.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.home.mvp.contract.WalletImportContract;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class WalletImportModel extends BaseModel implements WalletImportContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public WalletImportModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse> getPrivateKey2(String getPrivateKey) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getPrivateKey2(getPrivateKey).retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse> getMnemonics2(String Mnemonics) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getMnemonics2(Mnemonics).retryWhen(new RetryWithDelay());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}