package com.roo.mine.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.mine.mvp.contract.AddressBookEditContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class AddressBookEditPresenter extends BasePresenter<AddressBookEditContract.Model, AddressBookEditContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public AddressBookEditPresenter(AddressBookEditContract.Model model, AddressBookEditContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}