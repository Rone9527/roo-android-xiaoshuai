package com.roo.home.mvp.contract;

import android.app.Activity;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.mine.TransferRecordBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

import io.reactivex.Observable;

public interface TransferRecordContract {
    interface View extends IView {


        LinkTokenBean.TokensBean getTokensBean();

        Activity getActivity();

        void onRefreshSuccess(List<TransferRecordBean.DataBean> data);

        void onLoadMoreSuccess(List<TransferRecordBean.DataBean> data);
    }

    interface Model extends IModel {

        Observable<BaseResponse<TransferRecordBean>> getTransaction(String chainCode, String address,
                                                                               String contractId, int pageIndex, int pageSize);
    }
}