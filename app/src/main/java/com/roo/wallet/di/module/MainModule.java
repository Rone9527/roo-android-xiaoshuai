package com.roo.wallet.di.module;

import com.roo.wallet.mvp.contract.MainContract;
import com.roo.wallet.mvp.model.MainModel;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class MainModule {

    @Binds
    abstract MainContract.Model bindMainModel(MainModel model);
}