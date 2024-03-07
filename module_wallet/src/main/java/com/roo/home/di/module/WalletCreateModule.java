package com.roo.home.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.home.mvp.contract.WalletCreateContract;
import com.roo.home.mvp.model.WalletCreateModel;

@Module
public abstract class WalletCreateModule {

    @Binds
    abstract WalletCreateContract.Model bindWalletCreateModel(WalletCreateModel model);
}