package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.home.di.module.AssetSelectModule;
import com.roo.home.mvp.contract.AssetSelectContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.ui.activity.AssetSelectActivity;;

@ActivityScope
@Component(modules = AssetSelectModule.class, dependencies = AppComponent.class)
public interface AssetSelectComponent {
    void inject(AssetSelectActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AssetSelectComponent.Builder view(AssetSelectContract.View view);

        AssetSelectComponent.Builder appComponent(AppComponent appComponent);

        AssetSelectComponent build();
    }
}