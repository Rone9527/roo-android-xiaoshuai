package com.roo.mine.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.mvp.contract.LanguageContract;
import com.roo.mine.mvp.model.LanguageModel;
import com.roo.mine.mvp.ui.adapter.LanguageAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class LanguageModule {

    @Binds
    abstract LanguageContract.Model bindLanguageModel(LanguageModel model);

    @ActivityScope
    @Provides
    static LanguageAdapter provideLanguageAdapter() {
        return new LanguageAdapter();
    }

}