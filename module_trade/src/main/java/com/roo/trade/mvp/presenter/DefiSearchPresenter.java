package com.roo.trade.mvp.presenter;

import com.core.domain.trade.DeFiBean;
import com.core.domain.trade.DeFiDataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.Kits;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.trade.R;
import com.roo.trade.mvp.contract.DefiSearchContract;
import com.roo.trade.mvp.ui.adapter.DefiSearchAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class DefiSearchPresenter extends BasePresenter<DefiSearchContract.Model, DefiSearchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    DefiSearchAdapter mAdapter;
    private int pageNum = 1;


    @Inject
    public DefiSearchPresenter(DefiSearchContract.Model model, DefiSearchContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    public void getDeFi() {
        mRootView.getSmartRefreshLayout().setEnableLoadMore(false);
        mModel.getDeFis(1, Constants.PAGE_SIZE, "", "hots")
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DeFiBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<DeFiBean> t) {
                        mAdapter.setNewData(t.getData().getData());
                    }
                });
    }

    public void getDeFi(boolean isRefresh, String pairNameOrContractId) {
        if (isRefresh) {
            pageNum = 1;
            mRootView.getSmartRefreshLayout().setEnableLoadMore(true);
        } else {
            pageNum = pageNum + 1;
        }
        mModel.getDeFis(pageNum, Constants.PAGE_SIZE, pairNameOrContractId, "")
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DeFiBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<DeFiBean> t) {
                        List<DeFiDataBean> data = t.getData().getData();
                        if (isRefresh) {
                            mAdapter.setNewData(data);
                            if (Kits.Empty.check(data)) {
                                String emptyStr = mRootView.getActivity().getString(R.string.defi_search_no_data);
                                ViewHelper.initEmptyView(mAdapter, mRootView.getRecyclerView(), emptyStr,
                                        R.drawable.ic_common_empty_data_search);

                            }
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