package com.roo.mine.di.module;

import com.roo.mine.mvp.model.MineModel;
import com.roo.mine.mvp.contract.MineContract;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;


@Module
public class MineModule {
    private MineContract.View view;

    public MineModule(MineContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    MineContract.View provideMineView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    MineContract.Model provideMineModel(MineModel model) {
        return model;
    }

}