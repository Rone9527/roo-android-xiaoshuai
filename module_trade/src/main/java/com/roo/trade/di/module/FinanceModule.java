package com.roo.trade.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.roo.trade.mvp.contract.FinanceContract;
import com.roo.trade.mvp.model.FinanceModel;
import com.roo.trade.mvp.ui.adapter.FinanceAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class FinanceModule {

    @Binds
    abstract FinanceContract.Model bindFinanceModel(FinanceModel model);


    @FragmentScope
    @Provides
    static FinanceAdapter provideFinanceAdapter() {
        return new FinanceAdapter();
    }

}