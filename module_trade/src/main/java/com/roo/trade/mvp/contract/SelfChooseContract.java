package com.roo.trade.mvp.contract;

import com.core.domain.trade.DeFiDataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

public interface SelfChooseContract {
    interface View extends IView {
        void setData(List<DeFiDataBean> list);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<DeFiDataBean>>> getDeFiSelfChoose(String ids);
    }
}