package com.roo.home.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.home.mvp.contract.ExchangeQuotesContract;
import com.roo.home.mvp.model.ExchangeQuotesModel;

@Module
public abstract class ExchangeQuotesModule {

    @Binds
    abstract ExchangeQuotesContract.Model bindExchangeQuotesModel(ExchangeQuotesModel model);
}