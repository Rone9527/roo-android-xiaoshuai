package com.roo.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.contract.AssetSelectContract;
import com.roo.home.mvp.model.AssetSelectModel;
import com.roo.home.mvp.ui.adapter.AssetSelectCoinAdapter;
import com.roo.home.mvp.ui.adapter.AssetSelectLinkAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AssetSelectModule {

    @Binds
    abstract AssetSelectContract.Model bindAssetSelectModel(AssetSelectModel model);

    @ActivityScope
    @Provides
    static AssetSelectLinkAdapter provideAssetSelectLinkAdapter() {
        return new AssetSelectLinkAdapter();
    }

    @ActivityScope
    @Provides
    static AssetSelectCoinAdapter provideAssetSelectCoinAdapter() {
        return new AssetSelectCoinAdapter();
    }


}