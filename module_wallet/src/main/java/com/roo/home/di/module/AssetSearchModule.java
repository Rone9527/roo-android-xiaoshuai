package com.roo.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.contract.AssetSearchContract;
import com.roo.home.mvp.model.AssetSearchModel;
import com.roo.home.mvp.ui.adapter.AssetSearchAdapter;
import com.roo.home.mvp.ui.adapter.AssetSelectLinkAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AssetSearchModule {

    @Binds
    abstract AssetSearchContract.Model bindAssetSearchModel(AssetSearchModel model);

    @ActivityScope
    @Provides
    static AssetSearchAdapter provideAssetSearchAdapter() {
        return new AssetSearchAdapter();
    }

}