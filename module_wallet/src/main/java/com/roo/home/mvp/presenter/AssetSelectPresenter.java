package com.roo.home.mvp.presenter;


import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.core.domain.link.LinkTokenBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.RxUtils;
import com.roo.home.mvp.contract.AssetSelectContract;
import com.roo.home.mvp.ui.adapter.AssetSelectLinkAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class AssetSelectPresenter extends BasePresenter<AssetSelectContract.Model, AssetSelectContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AssetSelectLinkAdapter mAdapterLink;

    @Inject
    public AssetSelectPresenter(AssetSelectContract.Model model, AssetSelectContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void loadData() {
        RxUtils.makeObservable(new Callable<List<LinkTokenBean>>() {
            @Override
            public List<LinkTokenBean> call() throws Exception {
                String json = SharedPref.getString(Constants.KEY_OBJ_LINK_TOKEN);
                if (TextUtils.isEmpty(json)) {
                    return new ArrayList<>();
                }
                return new Gson().fromJson(json, new TypeToken<List<LinkTokenBean>>() {
                }.getType());
            }
        }).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<LinkTokenBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull List<LinkTokenBean> t) {
                      mRootView.loadData(t);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    public void onTokenAdd(String chainCode, String address, String contractId) {
        mModel.onTokenAdd(chainCode, address, contractId).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse t) {
                    }
                });
    }
}