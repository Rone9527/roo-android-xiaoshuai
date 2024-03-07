package com.roo.trade.di.module;

import com.roo.trade.mvp.contract.DeFiDetailActivityContract;
import com.roo.trade.mvp.model.DeFiDetailActivityModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DeFiDetailActivityModule {

    @Binds
    abstract DeFiDetailActivityContract.Model bindDeFiDetailActivityModel(DeFiDetailActivityModel model);
}