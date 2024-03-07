package com.roo.home.mvp.model;

import android.app.Application;

import com.core.domain.trade.AllBalanceBean;
import com.core.domain.trade.BalanceBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.home.mvp.contract.WalletContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class WalletModel extends BaseModel implements WalletContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public WalletModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<List<BalanceBean>>> getBalance(String chainCode, String address, ArrayList<String> contractId) {

        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getBalance(chainCode, address, contractId)
                .retryWhen(new RetryWithDelay());
    }

    @Override
    public Observable<BaseResponse> getAllBalance(ArrayList<AllBalanceBean> dtos) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getAllBalances(dtos)
                .retryWhen(new RetryWithDelay());
    }
}