package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.di.module.WalletCreateModule;
import com.roo.home.mvp.contract.WalletCreateContract;
import com.roo.home.mvp.ui.activity.WalletCreateActivity;;

@ActivityScope
@Component(modules = WalletCreateModule.class, dependencies = AppComponent.class)
public interface WalletCreateComponent {
    void inject(WalletCreateActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WalletCreateComponent.Builder view(WalletCreateContract.View view);

        WalletCreateComponent.Builder appComponent(AppComponent appComponent);

        WalletCreateComponent build();
    }
}