package com.roo.trade.mvp.presenter;

import com.core.domain.trade.DeFiDetailBean;
import com.core.domain.trade.DeFiPriceChartBean;
import com.core.domain.trade.DeFiTradeBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.RxUtils;
import com.roo.trade.mvp.contract.DeFiDetailActivityContract;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.internal.observers.BlockingBaseObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class DeFiDetailActivityPresenter extends BasePresenter<DeFiDetailActivityContract.Model, DeFiDetailActivityContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public DeFiDetailActivityPresenter(DeFiDetailActivityContract.Model model, DeFiDetailActivityContract.View rootView) {
        super(model, rootView);
    }

    public void getDeFiDetail(String ascription, String id) {
        mModel.getDeFiDetail(ascription, id)
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new BlockingBaseObserver<BaseResponse<DeFiDetailBean>>() {
                    @Override
                    public void onNext(@NotNull BaseResponse<DeFiDetailBean> deFiDetailBeanBaseResponse) {
                        mRootView.setDeFiDetailData(deFiDetailBeanBaseResponse.getData());
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                    }
                });
    }

    public void getDeFiTradeRecent(String ascription, String id) {
        mModel.getDeFiTradeRecent(ascription, id)
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new BlockingBaseObserver<BaseResponse<List<DeFiTradeBean>>>() {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<DeFiTradeBean>> deFiDetailBeanBaseResponse) {
                        mRootView.setDataDeFiTrade(deFiDetailBeanBaseResponse.getData());
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                    }
                });
    }

    public void getDeFiPriceChart(String ascription, String id) {
        mModel.getDeFiPriceChart(ascription, id)
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new BlockingBaseObserver<BaseResponse<List<DeFiPriceChartBean>>>() {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<DeFiPriceChartBean>> baseResponse) {
                        mRootView.setDataDeFiPriceChart(baseResponse.getData());
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}