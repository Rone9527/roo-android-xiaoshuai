package com.roo.dapp.mvp.presenter;

import android.app.Application;

import com.core.domain.mine.TransactionResult;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import javax.inject.Inject;

import com.roo.core.utils.LogManage;
import com.roo.core.utils.RxUtils;
import com.roo.dapp.mvp.contract.DappBrowserTronContract;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@FragmentScope
public class DappBrowserTronPresenter extends BasePresenter<DappBrowserTronContract.Model, DappBrowserTronContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public DappBrowserTronPresenter(DappBrowserTronContract.Model model, DappBrowserTronContract.View rootView) {
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


}