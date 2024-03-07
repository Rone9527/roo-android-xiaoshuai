package com.roo.trade.mvp.presenter;



import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.core.domain.trade.TickerBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.utils.TickerManager;
import com.roo.trade.mvp.contract.TradeContract;
import com.roo.trade.mvp.ui.adapter.TradeAdapter;

import org.jetbrains.annotations.NotNull;
import org.simple.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@FragmentScope
public class TradePresenter extends BasePresenter<TradeContract.Model, TradeContract.View> {

    @Inject
    TradeAdapter mAdapter;
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public TradePresenter(TradeContract.Model model, TradeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void getTicks() {
        mModel.getTicks().compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<TickerBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<TickerBean>> t) {
                        TickerManager.getInstance().setTickerList(t.getData());
                        EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.DATA_TICKER);
                    }
                });

    }
}
