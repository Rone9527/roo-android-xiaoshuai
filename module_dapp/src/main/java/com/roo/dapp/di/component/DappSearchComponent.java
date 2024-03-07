package com.roo.dapp.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.dapp.di.module.DappSearchModule;
import com.roo.dapp.mvp.contract.DappSearchContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.dapp.mvp.ui.activity.DappSearchActivity;;

@ActivityScope
@Component(modules = DappSearchModule.class, dependencies = AppComponent.class)
public interface DappSearchComponent {
    void inject(DappSearchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DappSearchComponent.Builder view(DappSearchContract.View view);

        DappSearchComponent.Builder appComponent(AppComponent appComponent);

        DappSearchComponent build();
    }
}