package com.roo.dapp.di.module;

import com.roo.dapp.mvp.contract.DappBrowserContract;
import com.roo.dapp.mvp.model.DappBrowserModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DappBrowserModule {

    @Binds
    abstract DappBrowserContract.Model bindDappBrowserModel(DappBrowserModel model);
}