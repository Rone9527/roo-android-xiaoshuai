package com.roo.home.di.module;

import com.roo.home.mvp.contract.TransferDetailContract;
import com.roo.home.mvp.model.TransferDetailModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class TransferDetailModule {

    @Binds
    abstract TransferDetailContract.Model bindTransferDetailModel(TransferDetailModel model);


}