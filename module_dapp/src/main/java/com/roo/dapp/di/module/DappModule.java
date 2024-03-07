package com.roo.dapp.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.roo.dapp.mvp.contract.DappContract;
import com.roo.dapp.mvp.model.DappModel;
import com.roo.dapp.mvp.ui.adapter.DappAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class DappModule {
    private DappContract.View view;

    public DappModule(DappContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    DappContract.View provideDappView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    DappContract.Model provideDappModel(DappModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    DappAdapter provideDappAdapter() {
        return new DappAdapter();
    }

}