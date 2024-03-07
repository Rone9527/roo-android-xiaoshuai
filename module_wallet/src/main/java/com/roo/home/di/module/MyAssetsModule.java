package com.roo.home.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.home.mvp.contract.MyAssetsContract;
import com.roo.home.mvp.model.MyAssetsModel;

@Module
public abstract class MyAssetsModule {

    @Binds
    abstract MyAssetsContract.Model bindMyAssetsModel(MyAssetsModel model);
}