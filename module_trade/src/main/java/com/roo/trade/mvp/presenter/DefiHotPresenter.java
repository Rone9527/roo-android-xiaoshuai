package com.roo.trade.mvp.presenter;

import com.core.domain.trade.DeFiBean;
import com.core.domain.trade.DeFiDataBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.RxUtils;
import com.roo.trade.mvp.contract.DefiHotContract;
import com.roo.trade.mvp.ui.adapter.DeFiAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@FragmentScope
public class DefiHotPresenter extends BasePresenter<DefiHotContract.Model, DefiHotContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    DeFiAdapter mAdapter;
    private int pageNum = 1;

    @Inject
    public DefiHotPresenter(DefiHotContract.Model model, DefiHotContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    public void getDeFi(boolean isRefresh, String pairNameOrContractId, String sortBy) {
        if (isRefresh) {
            pageNum = 1;
            mRootView.getSmartRefreshLayout().resetNoMoreData();
        } else {
            pageNum = pageNum + 1;
        }

        mModel.getDeFis(pageNum, Constants.PAGE_SIZE, pairNameOrContractId, sortBy)
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DeFiBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<DeFiBean> t) {

                        List<DeFiDataBean> data = t.getData().getData();
                        if (isRefresh) {
                            mRootView.getSmartRefreshLayout().finishRefresh();
                            mAdapter.setNewData(data);
                        } else {
                            mRootView.getSmartRefreshLayout().finishLoadMore();
                            mAdapter.addData(data);
                        }
                        if (data.size() != Constants.PAGE_SIZE) {
                            mRootView.getSmartRefreshLayout().finishLoadMoreWithNoMoreData();
                        }
                    }
                });
    }

}