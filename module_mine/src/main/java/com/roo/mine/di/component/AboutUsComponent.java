package com.roo.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.mine.di.module.AboutUsModule;
import com.roo.mine.mvp.contract.AboutUsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.mvp.ui.activity.AboutUsActivity;;

@ActivityScope
@Component(modules = AboutUsModule.class, dependencies = AppComponent.class)
public interface AboutUsComponent {
    void inject(AboutUsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AboutUsComponent.Builder view(AboutUsContract.View view);

        AboutUsComponent.Builder appComponent(AppComponent appComponent);

        AboutUsComponent build();
    }
}