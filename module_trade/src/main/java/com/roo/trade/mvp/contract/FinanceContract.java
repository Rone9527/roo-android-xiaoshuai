package com.roo.trade.mvp.contract;

import com.core.domain.trade.FinanceBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import io.reactivex.Observable;

public interface FinanceContract {
    interface View extends IView {
        SmartRefreshLayout getSmartRefreshLayout();
    }

    interface Model extends IModel {
        Observable<BaseResponse<FinanceBean>> getFinancial(String ascription, String chainCode, String name,
                                                           int pageNum, int pageSize, String tag);
    }

}