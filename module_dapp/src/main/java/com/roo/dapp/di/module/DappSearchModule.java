package com.roo.dapp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.dapp.mvp.contract.DappSearchContract;
import com.roo.dapp.mvp.model.DappSearchModel;
import com.roo.dapp.mvp.ui.adapter.DappSearchAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class DappSearchModule {

    @Binds
    abstract DappSearchContract.Model bindDappSearchModel(DappSearchModel model);


    @ActivityScope
    @Provides
    static DappSearchAdapter provideDappSearchAdapter() {
        return new DappSearchAdapter();
    }

}