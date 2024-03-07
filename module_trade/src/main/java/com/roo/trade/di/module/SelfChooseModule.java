package com.roo.trade.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.trade.mvp.contract.SelfChooseContract;
import com.roo.trade.mvp.model.SelfChooseModel;

@Module
public abstract class SelfChooseModule {

    @Binds
    abstract SelfChooseContract.Model bindSelfChooseModel(SelfChooseModel model);
}