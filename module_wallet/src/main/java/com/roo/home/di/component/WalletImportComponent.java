package com.roo.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.di.module.WalletImportModule;
import com.roo.home.mvp.contract.WalletImportContract;
import com.roo.home.mvp.ui.activity.WalletImportActivity;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = WalletImportModule.class, dependencies = AppComponent.class)
public interface WalletImportComponent {
    void inject(WalletImportActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WalletImportComponent.Builder view(WalletImportContract.View view);

        WalletImportComponent.Builder appComponent(AppComponent appComponent);

        WalletImportComponent build();
    }
}