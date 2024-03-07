package com.roo.home.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.home.mvp.contract.TransferTronContract;
import com.roo.home.mvp.model.TransferTronModel;

@Module
public abstract class TransferTronModule {

    @Binds
    abstract TransferTronContract.Model bindTransferTronModel(TransferTronModel model);
}