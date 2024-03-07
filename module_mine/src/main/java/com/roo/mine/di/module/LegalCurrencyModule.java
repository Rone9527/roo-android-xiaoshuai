package com.roo.mine.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.mvp.contract.LegalCurrencyContract;
import com.roo.mine.mvp.model.LegalCurrencyModel;
import com.roo.mine.mvp.ui.adapter.LegalCurrencyAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class LegalCurrencyModule {

    @Binds
    abstract LegalCurrencyContract.Model bindLegalCurrencyModel(LegalCurrencyModel model);

    @ActivityScope
    @Provides
    static LegalCurrencyAdapter provideLegalCurrencyAdapter() {
        return new LegalCurrencyAdapter();
    }

}