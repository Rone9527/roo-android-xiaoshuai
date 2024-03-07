package com.roo.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.di.module.WalletModule;
import com.roo.home.mvp.ui.fragment.WalletFragment;

import dagger.Component;

@ActivityScope
@Component(modules = WalletModule.class, dependencies = AppComponent.class)
public interface WalletComponent {
    void inject(WalletFragment fragment);
}