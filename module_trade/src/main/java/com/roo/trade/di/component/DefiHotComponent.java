package com.roo.trade.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.roo.trade.di.module.DefiHotModule;
import com.roo.trade.mvp.contract.DefiHotContract;
import com.roo.trade.mvp.ui.fragment.DeFiOtherFragment;

import dagger.BindsInstance;
import dagger.Component;

;

@FragmentScope
@Component(modules = DefiHotModule.class, dependencies = AppComponent.class)
public interface DefiHotComponent {
    void inject(DeFiOtherFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DefiHotComponent.Builder view(DefiHotContract.View view);

        DefiHotComponent.Builder appComponent(AppComponent appComponent);

        DefiHotComponent build();
    }
}