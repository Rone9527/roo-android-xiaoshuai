package com.roo.trade.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.trade.mvp.contract.DefiSearchContract;
import com.roo.trade.mvp.model.DefiSearchModel;
import com.roo.trade.mvp.ui.adapter.DefiSearchAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class DefiSearchModule {

    @Binds
    abstract DefiSearchContract.Model bindDefiSearchModel(DefiSearchModel model);


    @ActivityScope
    @Provides
    static DefiSearchAdapter provideDefiSearchAdapter() {
        return new DefiSearchAdapter();
    }

}