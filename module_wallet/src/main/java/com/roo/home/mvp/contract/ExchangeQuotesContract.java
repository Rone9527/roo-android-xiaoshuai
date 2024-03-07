package com.roo.home.mvp.contract;

import com.core.domain.trade.QuoteBean;
import com.core.domain.trade.QuoteKlineBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

public interface ExchangeQuotesContract {
    interface View extends IView {
        void loadData(List<QuoteKlineBean> list);

        void loadQuotes(List<QuoteBean> list);
    }

    interface Model extends IModel {
        /**
         * @param baseAsset 币种
         */
        Observable<BaseResponse<List<QuoteKlineBean>>> getKline(String baseAsset);

        Observable<BaseResponse<List<QuoteBean>>> getPlatform(String baseAsset);
    }
}