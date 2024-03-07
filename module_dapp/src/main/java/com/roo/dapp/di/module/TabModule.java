package com.roo.dapp.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.dapp.mvp.contract.TabContract;
import com.roo.dapp.mvp.model.TabModel;

@Module
public abstract class TabModule {

    @Binds
    abstract TabContract.Model bindTabModel(TabModel model);
}