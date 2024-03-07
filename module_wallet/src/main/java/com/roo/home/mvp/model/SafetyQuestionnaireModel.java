package com.roo.home.mvp.model;

import android.app.Application;

import com.core.domain.trade.BalanceBean;
import com.core.domain.wallet.QuestionBean;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.home.mvp.contract.SafetyQuestionnaireContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class SafetyQuestionnaireModel extends BaseModel implements SafetyQuestionnaireContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SafetyQuestionnaireModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<List<QuestionBean>>> getQuestionnaire() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getQuestionnaire()
                .retryWhen(new RetryWithDelay());
    }
}