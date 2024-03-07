package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.home.di.module.MyAssetsModule;
import com.roo.home.mvp.contract.MyAssetsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.ui.activity.MyAssetsActivity;;

@ActivityScope
@Component(modules = MyAssetsModule.class, dependencies = AppComponent.class)
public interface MyAssetsComponent {
    void inject(MyAssetsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyAssetsComponent.Builder view(MyAssetsContract.View view);

        MyAssetsComponent.Builder appComponent(AppComponent appComponent);

        MyAssetsComponent build();
    }
}