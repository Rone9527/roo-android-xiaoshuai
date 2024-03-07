package com.roo.trade.mvp.presenter;

import android.text.TextUtils;

import com.core.domain.trade.DeFiDataBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.daoManagers.DeFiDaoManager;
import com.roo.core.utils.RxUtils;
import com.roo.trade.mvp.contract.SelfChooseContract;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.internal.observers.BlockingBaseObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@FragmentScope
public class SelfChoosePresenter extends BasePresenter<SelfChooseContract.Model, SelfChooseContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public SelfChoosePresenter(SelfChooseContract.Model model, SelfChooseContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    public void getDeFiSelfChoose() {
        String ids = DeFiDaoManager.getAllIdentity();
        if (TextUtils.isEmpty(ids)) {
            mRootView.setData(new ArrayList<>());
            return;
        }
        mModel.getDeFiSelfChoose(ids)
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new BlockingBaseObserver<BaseResponse<List<DeFiDataBean>>>() {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<DeFiDataBean>> deFiDetailBeanBaseResponse) {
                        mRootView.setData(deFiDetailBeanBaseResponse.getData());
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        mRootView.setData(new ArrayList<>());
                    }
                });
    }
}