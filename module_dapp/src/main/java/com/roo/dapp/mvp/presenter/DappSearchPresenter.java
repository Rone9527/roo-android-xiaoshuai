package com.roo.dapp.mvp.presenter;


import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.DappTypeBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.Kits;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.dapp.R;
import com.roo.dapp.mvp.contract.DappSearchContract;
import com.roo.dapp.mvp.ui.adapter.DappSearchAdapter;
import com.roo.dapp.mvp.utils.DappSearchManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class DappSearchPresenter extends BasePresenter<DappSearchContract.Model, DappSearchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    DappSearchAdapter mAdapter;
    private String lastKeyword;

    @Inject
    public DappSearchPresenter(DappSearchContract.Model model, DappSearchContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void getSearchHistoryDapp() {
        ArrayList<MultiItemEntity> result = new ArrayList<>(DappSearchManager.getInstance().select());
        if (Kits.Empty.check(result)) {
            mAdapter.setNewData(new ArrayList<>());
            String emptyStr = mRootView.getActivity().getString(R.string.no_data);
            ViewHelper.initEmptyView(mAdapter, mRootView.getRecyclerView(), emptyStr,
                    R.drawable.ic_common_empty_data_search);
            return;
        }
        result.add(0, () -> DappSearchAdapter.TYPE_HISTORY_TITLE);
        mAdapter.setNewData(result);
    }

    public void getDapps(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            return;
        }

        mModel.getDapps(keyword).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<DappBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<DappBean>> t) {

                        saveHistory(keyword);

                        if (Kits.Empty.check(t.getData())) {
                            mAdapter.setNewData(new ArrayList<>());
                            String emptyStr = mRootView.getActivity().getString(R.string.no_data_search);
                            ViewHelper.initEmptyView(mAdapter, mRootView.getRecyclerView(), emptyStr,
                                    R.drawable.ic_common_empty_data_search);
                            return;
                        }

                        for (DappBean dappBean : t.getData()) {
                            dappBean.setItemType(DappSearchAdapter.TYPE_SEARCH_CONTENT);
                        }
                        mAdapter.setNewData(new ArrayList<>(t.getData()));
                    }
                });
    }

    public void getLocalDapp(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            return;
        }
        String json = SharedPref.getString(Constants.KEY_OBJ_DAPP_LIST);
        if (TextUtils.isEmpty(json)) {
            mModel.getDappType().compose(RxUtils.applySchedulersLife(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<List<DappTypeBean>>>(mErrorHandler) {
                        @Override
                        public void onNext(@NotNull BaseResponse<List<DappTypeBean>> t) {

                            backupAllDapp(t);

                            doLocalSearch(keyword);
                        }
                    });
        } else {
            doLocalSearch(keyword);
        }
    }

    private void backupAllDapp(@NotNull BaseResponse<List<DappTypeBean>> t) {
        ArrayList<DappBean> dataSet = new ArrayList<>();
        for (DappTypeBean datum : t.getData()) {
            for (DappTypeBean.ListBean listBean : datum.getList()) {
                dataSet.addAll(listBean.getList());
            }
        }
        SharedPref.putString(Constants.KEY_OBJ_DAPP_LIST, new Gson().toJson(dataSet));
    }

    private void doLocalSearch(String keyword) {
        RxUtils.makeObservable(new Callable<ArrayList<DappBean>>() {
            @Override
            public ArrayList<DappBean> call() throws Exception {
                String json = SharedPref.getString(Constants.KEY_OBJ_DAPP_LIST);
                if (TextUtils.isEmpty(json)) {
                    return new ArrayList<>();
                }
                ArrayList<DappBean> list = new Gson().fromJson(json, new TypeToken<ArrayList<DappBean>>() {
                }.getType());

                ArrayList<DappBean> dataSet = new ArrayList<>();
                for (DappBean listBean : list) {
                    if (listBean.getName().toLowerCase().contains(keyword.toLowerCase())) {
                        dataSet.add(listBean);
                    }
                }
                return dataSet;
            }
        }).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<ArrayList<DappBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull ArrayList<DappBean> t) {
                        if (Kits.Empty.check(t)) {
                            getDapps(keyword);
                            return;
                        }

                        for (DappBean dappBean : t) {
                            dappBean.setItemType(DappSearchAdapter.TYPE_SEARCH_CONTENT);
                        }
                        mAdapter.setNewData(new ArrayList<>(t));

                        DappSearchPresenter.this.lastKeyword = keyword;
                    }
                });
    }

    private void saveHistory(String keyword) {
        DappSearchManager.getInstance().add(keyword);
    }

    public void saveHistory() {
        if (TextUtils.isEmpty(this.lastKeyword)) {
            return;
        }
        saveHistory(this.lastKeyword);
    }

}