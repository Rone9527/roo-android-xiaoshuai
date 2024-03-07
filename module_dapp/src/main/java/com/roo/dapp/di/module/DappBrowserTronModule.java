package com.roo.dapp.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.dapp.mvp.contract.DappBrowserTronContract;
import com.roo.dapp.mvp.model.DappBrowserTronModel;

@Module
public abstract class DappBrowserTronModule {

    @Binds
    abstract DappBrowserTronContract.Model bindDappBrowserTronModel(DappBrowserTronModel model);
}