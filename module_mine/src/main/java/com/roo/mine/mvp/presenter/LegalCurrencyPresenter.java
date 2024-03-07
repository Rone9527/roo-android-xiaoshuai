package com.roo.mine.mvp.presenter;


import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.core.domain.mine.LegalCurrencyBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.RxUtils;
import com.roo.mine.mvp.contract.LegalCurrencyContract;
import com.roo.mine.mvp.ui.adapter.LegalCurrencyAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class LegalCurrencyPresenter extends BasePresenter<LegalCurrencyContract.Model, LegalCurrencyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    LegalCurrencyAdapter mAdapter;
    @Inject
    Gson mGson;

    @Inject
    public LegalCurrencyPresenter(LegalCurrencyContract.Model model, LegalCurrencyContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAdapter = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void getLegalList() {
        String json = SharedPref.getString(Constants.KEY_OBJ_LEGAL_LIST);
        if (!TextUtils.isEmpty(json)) {
            List<LegalCurrencyBean> dataSet = new Gson().fromJson(json, new TypeToken<List<LegalCurrencyBean>>() {
            }.getType());
            mAdapter.setNewData(dataSet);
        }
        mModel.getLegalList().compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<LegalCurrencyBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<LegalCurrencyBean>> t) {
                        List<LegalCurrencyBean> data = t.getData();
                        SharedPref.putString(Constants.KEY_OBJ_LEGAL_LIST, mGson.toJson(data));
                        mAdapter.setNewData(data);
                    }
                });
    }

}