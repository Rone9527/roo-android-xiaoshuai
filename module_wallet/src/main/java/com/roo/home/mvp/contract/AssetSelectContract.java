package com.roo.home.mvp.contract;

import com.core.domain.link.LinkTokenBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.roo.core.model.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

public interface AssetSelectContract {
    interface View extends IView {

        void loadData(List<LinkTokenBean> dataSet);
    }

    interface Model extends IModel {

        Observable<BaseResponse> onTokenAdd(String chainCode, String address, String contractId);
    }
}