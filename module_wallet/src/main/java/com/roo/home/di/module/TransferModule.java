package com.roo.home.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.home.mvp.contract.TransferContract;
import com.roo.home.mvp.model.TransferModel;

@Module
public abstract class TransferModule {

    @Binds
    abstract TransferContract.Model bindTransferModel(TransferModel model);
}