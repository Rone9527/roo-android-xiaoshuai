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
import com.roo.core.utils.Kits;
import com.roo.core.utils.RxUtils;
import com.roo.home.mvp.contract.AssetSearchContract;
import com.roo.home.mvp.ui.adapter.AssetSearchAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class AssetSearchPresenter extends BasePresenter<AssetSearchContract.Model, AssetSearchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AssetSearchAdapter mAdapter;
    private List<LinkTokenBean.TokensBean> tokensBeans;

    @Inject
    public AssetSearchPresenter(AssetSearchContract.Model model, AssetSearchContract.View rootView) {
        super(model, rootView);
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

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void loadData() {
        RxUtils.makeObservable(new Callable<List<LinkTokenBean.TokensBean>>() {
            @Override
            public List<LinkTokenBean.TokensBean> call() throws Exception {
                String json = SharedPref.getString(Constants.KEY_OBJ_LINK_TOKEN);
                if (TextUtils.isEmpty(json)) {
                    return new ArrayList<>();
                }
                List<LinkTokenBean> tokenBeans = new Gson().fromJson(json, new TypeToken<List<LinkTokenBean>>() {
                }.getType());
                List<LinkTokenBean.TokensBean> dataSet = new ArrayList<>();
                for (LinkTokenBean linkTokenBean : tokenBeans) {
                    for (LinkTokenBean.TokensBean token : linkTokenBean.getTokens()) {
                        if (token.isIsSearch()) {
                            dataSet.add(token);
                        }
                    }
                }
                return dataSet;
            }
        }).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<LinkTokenBean.TokensBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull List<LinkTokenBean.TokensBean> t) {
                        tokensBeans = t;
                        mAdapter.setNewData(tokensBeans);
                    }
                });
    }

    public void filter(String inputStr) {
        if (Kits.Empty.check(inputStr)) {
            mAdapter.setNewData(tokensBeans);
        } else {
            ArrayList<LinkTokenBean.TokensBean> dataSet = new ArrayList<>();
            if (inputStr.length() > 32) {//代币地址
                for (LinkTokenBean.TokensBean tokensBean : tokensBeans) {
                    if (!TextUtils.isEmpty(tokensBean.getContractId())
                            && tokensBean.getContractId().toUpperCase().contains(inputStr)) {
                        dataSet.add(tokensBean);
                    }
                }
            } else {
                for (LinkTokenBean.TokensBean tokensBean : tokensBeans) {
                    if (tokensBean.getSymbol().toUpperCase().contains(inputStr)) {
                        dataSet.add(tokensBean);
                    }
                }
            }

            mAdapter.setNewData(dataSet);
        }
    }
}