package com.roo.mine.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.mine.mvp.contract.AddressBookContract;
import com.roo.mine.mvp.ui.adapter.AddressBookAdapter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class AddressBookPresenter extends BasePresenter<AddressBookContract.Model, AddressBookContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AddressBookAdapter mAdapter;

    @Inject
    public AddressBookPresenter(AddressBookContract.Model model, AddressBookContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}