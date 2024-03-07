package com.roo.dapp.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.dapp.di.module.DappBrowserTronModule;
import com.roo.dapp.mvp.contract.DappBrowserTronContract;

import com.jess.arms.di.scope.FragmentScope;
import com.roo.dapp.mvp.ui.fragment.DappBrowserTronFragment;;

@FragmentScope
@Component(modules = DappBrowserTronModule.class, dependencies = AppComponent.class)
public interface DappBrowserTronComponent {
    void inject(DappBrowserTronFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DappBrowserTronComponent.Builder view(DappBrowserTronContract.View view);

        DappBrowserTronComponent.Builder appComponent(AppComponent appComponent);

        DappBrowserTronComponent build();
    }
}