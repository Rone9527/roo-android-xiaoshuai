package com.roo.dapp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.roo.dapp.di.module.DappBrowserModule;
import com.roo.dapp.mvp.contract.DappBrowserContract;
import com.roo.dapp.mvp.ui.fragment.DappBrowserFragment;
import com.roo.dapp.mvp.ui.fragment.DappBrowserFragmentNew;

import dagger.BindsInstance;
import dagger.Component;

@FragmentScope
@Component(modules = DappBrowserModule.class, dependencies = AppComponent.class)
public interface DappBrowserComponent {
    void inject(DappBrowserFragment activity);

    void inject(DappBrowserFragmentNew dappBrowserFragmentNew);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DappBrowserComponent.Builder view(DappBrowserContract.View view);

        DappBrowserComponent.Builder appComponent(AppComponent appComponent);

        DappBrowserComponent build();
    }
}