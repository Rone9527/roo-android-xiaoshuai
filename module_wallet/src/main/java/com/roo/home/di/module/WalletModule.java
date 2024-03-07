package com.roo.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.contract.WalletContract;
import com.roo.home.mvp.model.WalletModel;
import com.roo.home.mvp.ui.adapter.WalletAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class WalletModule {
    private WalletContract.View view;

    public WalletModule(WalletContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WalletContract.View provideWalletView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WalletContract.Model provideWalletModel(WalletModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    WalletAdapter provideWalletAdapter() {
        return new WalletAdapter();
    }

}