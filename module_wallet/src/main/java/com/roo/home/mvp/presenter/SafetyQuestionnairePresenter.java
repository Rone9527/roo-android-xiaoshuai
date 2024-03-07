package com.roo.home.mvp.presenter;

import android.app.Application;

import com.core.domain.trade.QuoteBean;
import com.core.domain.wallet.QuestionBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RxUtils;
import com.roo.home.mvp.contract.SafetyQuestionnaireContract;
import com.roo.home.mvp.ui.adapter.QAAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@ActivityScope
public class SafetyQuestionnairePresenter extends BasePresenter<SafetyQuestionnaireContract.Model, SafetyQuestionnaireContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    QAAdapter mAdapter;
    @Inject
    public SafetyQuestionnairePresenter(SafetyQuestionnaireContract.Model model, SafetyQuestionnaireContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getQuestionnaire() {
        mModel.getQuestionnaire()
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<QuestionBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<QuestionBean>> data) {
//                        mAdapter.setNewData(data.getData());
//                        LogManage.e("mAdapter--->"+mAdapter.getData().size());
                        mRootView.getQuestionnaireSuccess(data.getData());
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                    }
                });
    }
}