package com.roo.wallet.di.module;

import com.roo.wallet.mvp.contract.SplashContract;
import com.roo.wallet.mvp.model.SplashModel;

import dagger.Binds;
import dagger.Module;



@Module
public abstract class SplashModule {

    @Binds
    abstract SplashContract.Model bindSplashModel(SplashModel model);
}