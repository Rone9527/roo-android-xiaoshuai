package com.roo.trade.mvp.presenter;

import com.core.domain.trade.FinanceBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.RxUtils;
import com.roo.trade.mvp.contract.FinanceContract;
import com.roo.trade.mvp.ui.adapter.FinanceAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@FragmentScope
public class FinancePresenter extends BasePresenter<FinanceContract.Model, FinanceContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    FinanceAdapter mAdapter;
    private int pageNum;

    @Inject
    public FinancePresenter(FinanceContract.Model model, FinanceContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    public void getFinancial(boolean isRefresh, String ascription, String chainCode, String name, String tag) {

        if (isRefresh) {
            pageNum = 1;
            mRootView.getSmartRefreshLayout().resetNoMoreData();
        } else {
            pageNum = pageNum + 1;
        }

        mModel.getFinancial(ascription, chainCode, name, pageNum, Constants.PAGE_SIZE, tag)
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<FinanceBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<FinanceBean> t) {

                        List<FinanceBean.DataDTO> data = t.getData().getData();
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