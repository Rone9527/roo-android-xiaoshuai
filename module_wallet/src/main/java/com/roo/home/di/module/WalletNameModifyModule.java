package com.roo.home.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.home.mvp.contract.WalletNameModifyContract;
import com.roo.home.mvp.model.WalletNameModifyModel;

@Module
public abstract class WalletNameModifyModule {

    @Binds
    abstract WalletNameModifyContract.Model bindWalletNameModifyModel(WalletNameModifyModel model);
}