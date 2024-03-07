package com.roo.trade.mvp.contract;

import com.core.domain.trade.DeFiBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import io.reactivex.Observable;

public interface DefiHotContract {
    interface View extends IView {
        SmartRefreshLayout getSmartRefreshLayout();
    }

    interface Model extends IModel {
        Observable<BaseResponse<DeFiBean>> getDeFis(int pageNum, int pageSize,
                                                        String pairNameOrContractId, String sortBy);
    }
}