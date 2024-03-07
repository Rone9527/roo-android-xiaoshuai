package com.roo.mine.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.mine.mvp.contract.LanguageContract;
import com.roo.mine.mvp.ui.adapter.LanguageAdapter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class LanguagePresenter extends BasePresenter<LanguageContract.Model, LanguageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    LanguageAdapter mAdapter;

    @Inject
    public LanguagePresenter(LanguageContract.Model model, LanguageContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}