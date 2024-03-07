package com.roo.home.mvp.model;

import android.app.Application;

import com.core.domain.trade.AllBalanceBean;
import com.core.domain.trade.BalanceBean;
import com.core.domain.trade.MyAssetsBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.home.mvp.contract.MyAssetsContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

@ActivityScope
public class MyAssetsModel extends BaseModel implements MyAssetsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyAssetsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

//    @Override
//    public Observable<BaseResponse<List<MyAssetsBean>>> getAllBalance(ArrayList<AllBalanceBean> dtos) {
//        return mRepositoryManager.obtainRetrofitService(ApiService.class)
//                .getAllBalances(dtos)
//                .retryWhen(new RetryWithDelay());
//    }
}