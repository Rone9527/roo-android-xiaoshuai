package com.roo.mine.mvp.model;

import android.app.Application;

import com.core.domain.mine.LegalCurrencyBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.mine.mvp.contract.LegalCurrencyContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class LegalCurrencyModel extends BaseModel implements LegalCurrencyContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public LegalCurrencyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<LegalCurrencyBean>>> getLegalList() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getLegalList().retryWhen(new RetryWithDelay());
    }


}