package com.roo.trade.di.module;

import com.roo.trade.mvp.contract.DexDetailContract;
import com.roo.trade.mvp.model.DexDetailModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DexDetailModule {

    @Binds
    abstract DexDetailContract.Model bindDexDetailModel(DexDetailModel model);
}