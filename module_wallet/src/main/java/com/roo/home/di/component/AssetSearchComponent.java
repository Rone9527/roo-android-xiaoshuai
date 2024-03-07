package com.roo.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.di.module.AssetSearchModule;
import com.roo.home.mvp.contract.AssetSearchContract;
import com.roo.home.mvp.ui.activity.AssetSearchActivity;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = AssetSearchModule.class, dependencies = AppComponent.class)
public interface AssetSearchComponent {
    void inject(AssetSearchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AssetSearchComponent.Builder view(AssetSearchContract.View view);

        AssetSearchComponent.Builder appComponent(AppComponent appComponent);

        AssetSearchComponent build();
    }
}