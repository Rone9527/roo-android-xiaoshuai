package com.roo.home.mvp.model;

import android.app.Application;

import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.GasBean;
import com.core.domain.mine.TransactionResult;
import com.core.domain.trade.TransferPointBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.home.mvp.contract.TransferContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.RequestBody;

@ActivityScope
public class TransferModel extends BaseModel implements TransferContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public TransferModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<GasBean>> getGasDataSet(String chainCode) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getGas(chainCode)
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public void submitTransfer(TransferPointBean transferPointBean) {
        mRepositoryManager.obtainRetrofitService(ApiService.class)
                .submitTransfer(transferPointBean)
                .retryWhen(new RetryWithDelay());
    }


}