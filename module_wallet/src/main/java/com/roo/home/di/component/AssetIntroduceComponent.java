package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.home.di.module.AssetIntroduceModule;
import com.roo.home.mvp.contract.AssetIntroduceContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.ui.activity.AssetIntroduceActivity;;

@ActivityScope
@Component(modules = AssetIntroduceModule.class, dependencies = AppComponent.class)
public interface AssetIntroduceComponent {
    void inject(AssetIntroduceActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AssetIntroduceComponent.Builder view(AssetIntroduceContract.View view);

        AssetIntroduceComponent.Builder appComponent(AppComponent appComponent);

        AssetIntroduceComponent build();
    }
}