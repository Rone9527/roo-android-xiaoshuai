package com.roo.home.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.home.mvp.contract.AssetIntroduceContract;
import com.roo.home.mvp.model.AssetIntroduceModel;

@Module
public abstract class AssetIntroduceModule {

    @Binds
    abstract AssetIntroduceContract.Model bindAssetIntroduceModel(AssetIntroduceModel model);
}