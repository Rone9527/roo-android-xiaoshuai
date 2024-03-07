package com.roo.trade.mvp.contract;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.core.domain.trade.DeFiBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import io.reactivex.Observable;

public interface DefiSearchContract {
    interface View extends IView {

        Activity getActivity();

        RefreshLayout getSmartRefreshLayout();

        RecyclerView getRecyclerView();

    }

    interface Model extends IModel {


        Observable<BaseResponse<DeFiBean>> getDeFis(int pageNum, int pageSize,
                                                    String pairNameOrContractId,
                                                    String sortBy);
    }
}