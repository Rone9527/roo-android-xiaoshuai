package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.home.di.module.TransferModule;
import com.roo.home.mvp.contract.TransferContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.ui.activity.TransferETHActivity;;

@ActivityScope
@Component(modules = TransferModule.class, dependencies = AppComponent.class)
public interface TransferComponent {
    void inject(TransferETHActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TransferComponent.Builder view(TransferContract.View view);

        TransferComponent.Builder appComponent(AppComponent appComponent);

        TransferComponent build();
    }
}