package com.roo.dapp.mvp.contract;

import com.core.domain.dapp.DappBannerBean;
import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.DappTypeBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;
import com.roo.dapp.mvp.ui.adapter.DappGridAdapter;
import com.roo.dapp.mvp.ui.adapter.DappLinkAdapter;
import com.roo.dapp.mvp.ui.adapter.DappTagAdapter;

import java.util.List;

import io.reactivex.Observable;


public interface DappContract {
    interface View extends IView {
        DappGridAdapter getAdapterFavRecent();

        DappGridAdapter getAdapterHot();

        int getSelectTopIndex();

        void getBannerData(List<DappBannerBean> list);

        void getDappTypeSuccess(BaseResponse<List<DappTypeBean>> t);
    }

    interface Model extends IModel {

        Observable<BaseResponse<List<DappBean>>> getDappsHot();

        Observable<BaseResponse<List<DappTypeBean>>> getDappType();

        Observable<BaseResponse<List<DappBannerBean>>> getDappBanner(int type);
    }
}
