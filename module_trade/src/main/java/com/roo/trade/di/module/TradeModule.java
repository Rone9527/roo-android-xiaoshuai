package com.roo.trade.di.module;

import com.roo.trade.mvp.contract.TradeContract;
import com.roo.trade.mvp.model.TradeModel;
import com.jess.arms.di.scope.FragmentScope;
import com.roo.trade.mvp.ui.adapter.TradeAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class TradeModule {
    private TradeContract.View view;

    public TradeModule(TradeContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    TradeContract.View provideTradeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    TradeContract.Model provideTradeModel(TradeModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    TradeAdapter provideTradeAdapter() {
        return new TradeAdapter();
    }

}