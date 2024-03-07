package com.roo.home.mvp.presenter;

import com.core.domain.link.TokenResourceBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.RxUtils;
import com.roo.home.mvp.contract.AssetIntroduceContract;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class AssetIntroducePresenter extends BasePresenter<AssetIntroduceContract.Model, AssetIntroduceContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public AssetIntroducePresenter(AssetIntroduceContract.Model model, AssetIntroduceContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    public void getResource(String chainCode, String contractId) {
        mModel.getResource(chainCode, contractId)
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<TokenResourceBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<TokenResourceBean> t) {
                        mRootView.loadData(t.getData());
                    }
                });
    }

}