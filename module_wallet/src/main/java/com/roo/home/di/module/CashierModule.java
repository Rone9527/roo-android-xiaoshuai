package com.roo.home.di.module;

import com.roo.home.mvp.contract.CashierContract;
import com.roo.home.mvp.model.CashierModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CashierModule {

    @Binds
    abstract CashierContract.Model bindCashierModel(CashierModel model);
}