package com.roo.home.mvp.contract;

import com.core.domain.link.TokenResourceBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;

import io.reactivex.Observable;

public interface AssetIntroduceContract {
    interface View extends IView {
        void loadData(TokenResourceBean dataSet);
    }

    interface Model extends IModel {
        Observable<BaseResponse<TokenResourceBean>> getResource(String chainCode, String contractId);
    }
}