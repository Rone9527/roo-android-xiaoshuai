package com.roo.wallet.mvp.contract;

import com.core.domain.link.LinkTokenBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

public interface SplashContract {
    interface View extends IView {

        void gotoNextPage();

    }

    interface Model extends IModel {

        Observable<BaseResponse<List<LinkTokenBean>>> getLinkListCoin();

    }
}
