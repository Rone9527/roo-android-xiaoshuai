package com.roo.mine.mvp.model;

import android.app.Application;

import com.core.domain.mine.SysConfigBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.mine.mvp.contract.MineContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class MineModel extends BaseModel implements MineContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public MineModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<List<SysConfigBean>>> getSysConfig() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getSysConfig().retryWhen(new RetryWithDelay());
    }

}