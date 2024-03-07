package com.roo.home.mvp.presenter;

import com.core.domain.trade.QuoteBean;
import com.core.domain.trade.QuoteKlineBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.RxUtils;
import com.roo.home.mvp.contract.ExchangeQuotesContract;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class ExchangeQuotesPresenter extends BasePresenter<ExchangeQuotesContract.Model, ExchangeQuotesContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public ExchangeQuotesPresenter(ExchangeQuotesContract.Model model, ExchangeQuotesContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    public void getKline(String baseAsset) {
        mModel.getKline(baseAsset)
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<QuoteKlineBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<QuoteKlineBean>> t) {
                        mRootView.loadData(t.getData().isEmpty() ? new ArrayList<>() : t.getData());
                    }
                });
    }

    public void getPlatform(String baseAsset) {
        mModel.getPlatform(baseAsset)
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<QuoteBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<QuoteBean>> data) {
                        mRootView.loadQuotes(data.getData());
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                    }
                });
    }
}