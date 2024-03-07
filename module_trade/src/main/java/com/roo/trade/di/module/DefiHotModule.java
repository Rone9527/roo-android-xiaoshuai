package com.roo.trade.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.roo.trade.mvp.contract.DefiHotContract;
import com.roo.trade.mvp.model.DeFiHotModel;
import com.roo.trade.mvp.ui.adapter.DeFiAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class DefiHotModule {

    @Binds
    abstract DefiHotContract.Model bindDefiHotModel(DeFiHotModel model);

    @FragmentScope
    @Provides
    static DeFiAdapter provideDeFiAdapter() {
        return new DeFiAdapter();
    }

}