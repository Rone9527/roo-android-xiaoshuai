package com.roo.trade.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.trade.di.module.DeFiDetailActivityModule;
import com.roo.trade.mvp.contract.DeFiDetailActivityContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.trade.mvp.ui.activity.DeFiDetailActivityActivity;;

@ActivityScope
@Component(modules = DeFiDetailActivityModule.class, dependencies = AppComponent.class)
public interface DeFiDetailActivityComponent {
    void inject(DeFiDetailActivityActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DeFiDetailActivityComponent.Builder view(DeFiDetailActivityContract.View view);

        DeFiDetailActivityComponent.Builder appComponent(AppComponent appComponent);

        DeFiDetailActivityComponent build();
    }
}