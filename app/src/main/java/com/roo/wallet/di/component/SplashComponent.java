package com.roo.wallet.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.wallet.di.module.SplashModule;
import com.roo.wallet.mvp.contract.SplashContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.wallet.mvp.ui.activity.SplashActivity;



@ActivityScope
@Component(modules = SplashModule.class, dependencies = AppComponent.class)
public interface SplashComponent {
    void inject(SplashActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SplashComponent.Builder view(SplashContract.View view);

        SplashComponent.Builder appComponent(AppComponent appComponent);

        SplashComponent build();
    }
}