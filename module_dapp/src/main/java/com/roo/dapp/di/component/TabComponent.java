package com.roo.dapp.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.dapp.di.module.TabModule;
import com.roo.dapp.mvp.contract.TabContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.dapp.mvp.ui.fragment.TabFragment;;

@ActivityScope
@Component(modules = TabModule.class, dependencies = AppComponent.class)
public interface TabComponent {
    void inject(TabFragment tabFragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TabComponent.Builder view(TabContract.View view);

        TabComponent.Builder appComponent(AppComponent appComponent);

        TabComponent build();
    }
}