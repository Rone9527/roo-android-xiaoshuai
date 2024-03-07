package com.roo.trade.mvp.contract;

import com.core.domain.trade.DeFiDetailBean;
import com.core.domain.trade.DeFiPriceChartBean;
import com.core.domain.trade.DeFiTradeBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

public interface DeFiDetailActivityContract {
    interface View extends IView {
        void setDeFiDetailData(DeFiDetailBean detailData);

        void setDataDeFiTrade(List<DeFiTradeBean> list);

        void setDataDeFiPriceChart(List<DeFiPriceChartBean> list);
    }

    interface Model extends IModel {
        Observable<BaseResponse<DeFiDetailBean>> getDeFiDetail(String ascription, String id);

        Observable<BaseResponse<List<DeFiTradeBean>>> getDeFiTradeRecent(String ascription, String id);

        Observable<BaseResponse<List<DeFiPriceChartBean>>> getDeFiPriceChart(String ascription, String id);


    }
}