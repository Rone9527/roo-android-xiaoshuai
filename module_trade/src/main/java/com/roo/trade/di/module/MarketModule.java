package com.roo.trade.di.module;

import com.roo.trade.mvp.contract.MarketContract;
import com.roo.trade.mvp.model.MarketModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MarketModule {

    @Binds
    abstract MarketContract.Model bindMarketModel(MarketModel model);
}