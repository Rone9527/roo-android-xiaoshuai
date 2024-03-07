package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.di.module.WalletCreateNormalModule;
import com.roo.home.mvp.contract.WalletCreateNormalContract;
import com.roo.home.mvp.ui.activity.WalletCreateNormalActivity;;

@ActivityScope
@Component(modules = WalletCreateNormalModule.class, dependencies = AppComponent.class)
public interface WalletCreateNormalComponent {
    void inject(WalletCreateNormalActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WalletCreateNormalComponent.Builder view(WalletCreateNormalContract.View view);

        WalletCreateNormalComponent.Builder appComponent(AppComponent appComponent);

        WalletCreateNormalComponent build();
    }
}