package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.home.di.module.WalletNameModifyModule;
import com.roo.home.mvp.contract.WalletNameModifyContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.ui.activity.WalletNameModifyActivity;;

@ActivityScope
@Component(modules = WalletNameModifyModule.class, dependencies = AppComponent.class)
public interface WalletNameModifyComponent {
    void inject(WalletNameModifyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WalletNameModifyComponent.Builder view(WalletNameModifyContract.View view);

        WalletNameModifyComponent.Builder appComponent(AppComponent appComponent);

        WalletNameModifyComponent build();
    }
}