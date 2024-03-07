package com.roo.home.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.home.mvp.contract.TronBandwidthEnergyContract;
import com.roo.home.mvp.model.TronBandwidthEnergyModel;

@Module
public abstract class TronBandwidthEnergyModule {

    @Binds
    abstract TronBandwidthEnergyContract.Model bindTronBandwidthEnergyModel(TronBandwidthEnergyModel model);
}