package com.roo.home.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.home.mvp.contract.WalletCreateNormalContract;
import com.roo.home.mvp.model.WalletCreateNormalModel;

@Module
public abstract class WalletCreateNormalModule {

    @Binds
    abstract WalletCreateNormalContract.Model bindWalletCreateModel(WalletCreateNormalModel model);
}