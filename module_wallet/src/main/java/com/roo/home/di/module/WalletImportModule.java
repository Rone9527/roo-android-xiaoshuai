package com.roo.home.di.module;

import com.roo.home.mvp.contract.WalletImportContract;
import com.roo.home.mvp.model.WalletImportModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class WalletImportModule {

    @Binds
    abstract WalletImportContract.Model bindWalletImportModel(WalletImportModel model);
}