package com.roo.dapp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.roo.dapp.di.module.DappModule;
import com.roo.dapp.mvp.ui.fragment.DappFragment;

import dagger.Component;

@FragmentScope
@Component(modules = DappModule.class, dependencies = AppComponent.class)
public interface DappComponent {
    void inject(DappFragment fragment);
}