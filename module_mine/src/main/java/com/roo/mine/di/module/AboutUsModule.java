package com.roo.mine.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.mine.mvp.contract.AboutUsContract;
import com.roo.mine.mvp.model.AboutUsModel;

@Module
public abstract class AboutUsModule {

    @Binds
    abstract AboutUsContract.Model bindAboutUsModel(AboutUsModel model);
}