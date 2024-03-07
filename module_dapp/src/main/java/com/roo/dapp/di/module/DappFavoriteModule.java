package com.roo.dapp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.dapp.mvp.contract.DappFavoriteContract;
import com.roo.dapp.mvp.model.DappFavoriteModel;
import com.roo.dapp.mvp.ui.adapter.DappFavoriteAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class DappFavoriteModule {

    @Binds
    abstract DappFavoriteContract.Model bindDappFavoriteModel(DappFavoriteModel model);


    @ActivityScope
    @Provides
    static DappFavoriteAdapter provideDappFavoriteAdapter() {
        return new DappFavoriteAdapter();
    }

}