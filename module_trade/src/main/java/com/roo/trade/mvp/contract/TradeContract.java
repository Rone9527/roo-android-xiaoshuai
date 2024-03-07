package com.roo.trade.mvp.contract;

import com.core.domain.trade.TickerBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;


public interface TradeContract {
    interface View extends IView {

    }

    interface Model extends IModel {

        Observable<BaseResponse<List<TickerBean>>> getTicks();
    }
}
