package com.roo.home.mvp.presenter;

import android.app.Application;

import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.TransactionResult;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.utils.RxUtils;
import com.roo.home.mvp.contract.TransferTronContract;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

@ActivityScope
public class TransferTronPresenter extends BasePresenter<TransferTronContract.Model, TransferTronContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TransferTronPresenter(TransferTronContract.Model model, TransferTronContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getNowBlock() {
        mModel.getNowBlock().compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BlockHeader>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BlockHeader t) {
                        mRootView.getNowBlockDataSet(t);
                    }
                });
    }

    public void broadcastTransaction(String transaction) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), transaction);
        mModel.broadcastTransaction(body).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<TransactionResult>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull TransactionResult t) {
                        mRootView.broadcastTransactionSuccess(t);
                    }
                });
    }


    public void triggerSmartContract(String transaction) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), transaction);
        mModel.createTransactionTre20(body).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<ResponseBody>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull ResponseBody t) {
                        mRootView.getTransactionSuccess(t);
                    }
                });
    }

    public void createTransactionTrc10(String transaction) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), transaction);
        mModel.createTransactionTre10(body).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<ResponseBody>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull ResponseBody t) {
                        mRootView.createTransactionTrc10Success(t);
                    }
                });
    }

}