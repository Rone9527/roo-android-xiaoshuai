package com.roo.home.mvp.presenter;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.mine.TransferRecordBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.Kits;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.ThreadManager;
import com.roo.home.mvp.contract.TransferRecordContract;
import com.roo.home.mvp.ui.adapter.TransferRecordAdapter;
import com.roo.home.mvp.utils.PenddingManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class TransferRecordPresenter extends BasePresenter<TransferRecordContract.Model, TransferRecordContract.View> {
    private ScheduledFuture<?> scheduledBalance;
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    TransferRecordAdapter mAdapter;
    private int pageNum;
    private String address;
    private LinkTokenBean.TokensBean tokensBean;
    List<TransferRecordBean.DataBean> dataSet = new ArrayList<>();

    public List<TransferRecordBean.DataBean> getDataSet() {
        return dataSet;
    }

    @Inject
    public TransferRecordPresenter(TransferRecordContract.Model model, TransferRecordContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAdapter = null;
        if (scheduledBalance != null && !scheduledBalance.isCancelled()) {
            scheduledBalance.cancel(true);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void initBalance() {
        scheduledBalance = ThreadManager.getScheduled().scheduleAtFixedRate(() -> {
            if (Kits.Empty.check(PenddingManager.getInstance().getPendding(tokensBean))) {
                return;
            }
        }, 100, 8000, TimeUnit.MILLISECONDS);
    }

//    public void getTransactionRecord(){
//        mModel.getTransaction(tokensBean.getChainCode(), address,
//                tokensBean.getContractId(), pageNum, Constants.PAGE_SIZE)
//                .compose(RxUtils.applySchedulersLife(mRootView))
//                .subscribe(new ErrorHandleSubscriber<BaseResponse<TransferRecordBean>>(mErrorHandler) {
//                    @Override
//                    public void onNext(@NotNull BaseResponse<TransferRecordBean> t) {
//
//                        List<TransferRecordBean.DataBean> data = t.getData().getData();
//                        mAdapter.setNewData(data);
//                        mRootView.getTransactionSuccess(data);
//                    }
//                });
//    }

    public void onRefresh() {
        pageNum = 0;
        mModel.getTransaction(tokensBean.getChainCode(), address,
                tokensBean.getContractId(), pageNum, Constants.PAGE_SIZE)
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<TransferRecordBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<TransferRecordBean> t) {

                        List<TransferRecordBean.DataBean> data = t.getData().getData();
                        mRootView.onRefreshSuccess(data);
                    }
                });
    }

    public void onLoadMore() {
        pageNum = pageNum + 1;
        mModel.getTransaction(tokensBean.getChainCode(), address,
                tokensBean.getContractId(), pageNum, Constants.PAGE_SIZE)
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<TransferRecordBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<TransferRecordBean> t) {

                        List<TransferRecordBean.DataBean> data = t.getData().getData();
                        mAdapter.setNewData(data);
                        mRootView.onLoadMoreSuccess(data);
                    }
                });
    }
//    public void getTransaction(boolean isRefresh, LinkTokenBean.TokensBean tokensBean, int filterType) {
//        if (isRefresh) {
//            pageNum = 0;
//            mRootView.getSmartRefreshLayout().resetNoMoreData();
//        } else {
//            pageNum = pageNum + 1;
//        }
//        mModel.getTransaction(tokensBean.getChainCode(), address,
//                tokensBean.getContractId(), pageNum, Constants.PAGE_SIZE)
//                .compose(RxUtils.applySchedulersLife(mRootView))
//                .subscribe(new ErrorHandleSubscriber<BaseResponse<TransferRecordBean>>(mErrorHandler) {
//                    @Override
//                    public void onNext(@NotNull BaseResponse<TransferRecordBean> t) {
//                        List<TransferRecordBean.DataBean> data = t.getData().getData();
//
//                        if (isRefresh) {
//                            mRootView.getSmartRefreshLayout().finishRefresh();
//                        } else {
//                            mRootView.getSmartRefreshLayout().finishLoadMore();
//                        }
//                        if (data.size() != Constants.PAGE_SIZE) {
//                            mRootView.getSmartRefreshLayout().finishLoadMoreWithNoMoreData();
//                        }
//
//                        if (isRefresh) {
//                            PenddingManager.getInstance().checkPendding(mRootView.getTokensBean(), data);
//                            ArrayList<TransferRecordBean.DataBean> pendding =
//                                    PenddingManager.getInstance().getPendding(mRootView.getTokensBean());
//                            if (!Kits.Empty.check(pendding)) {
//                                data.addAll(0, pendding);
//                            }
//                            dataSet.clear();
//                            dataSet.addAll(data);
//                        } else {
//                            dataSet.addAll(data);
//                        }
//
//                        notifyDataSetChanged(isRefresh, filterType);
//                    }
//                });
//    }

    public void notifyDataSetChanged(boolean isRefresh, int filterType) {
        if (isRefresh) {
            mAdapter.getData().clear();
            mAdapter.getData().addAll(dataSet);
        } else {
            mAdapter.getData().addAll(dataSet);
        }
        mAdapter.getFilter().filter(String.valueOf(filterType));
    }

    // 0===全部
    // 1===转入
    // 2===转出

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTokensBean(LinkTokenBean.TokensBean tokensBean) {
        this.tokensBean = tokensBean;
    }

}