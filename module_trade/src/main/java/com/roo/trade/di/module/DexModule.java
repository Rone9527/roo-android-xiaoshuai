package com.roo.trade.di.module;

import com.roo.trade.mvp.contract.DexContract;
import com.roo.trade.mvp.model.DexModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DexModule {

    @Binds
    abstract DexContract.Model bindDexModel(DexModel model);
}