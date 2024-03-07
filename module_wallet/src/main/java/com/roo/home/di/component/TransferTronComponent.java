package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.home.di.module.TransferTronModule;
import com.roo.home.mvp.contract.TransferTronContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.ui.activity.TransferTronActivity;;

@ActivityScope
@Component(modules = TransferTronModule.class, dependencies = AppComponent.class)
public interface TransferTronComponent {
    void inject(TransferTronActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TransferTronComponent.Builder view(TransferTronContract.View view);

        TransferTronComponent.Builder appComponent(AppComponent appComponent);

        TransferTronComponent build();
    }
}