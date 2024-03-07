package com.roo.trade.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.roo.trade.di.module.DexModule;
import com.roo.trade.mvp.contract.DexContract;
import com.roo.trade.mvp.ui.fragment.DexFragment;

import dagger.BindsInstance;
import dagger.Component;

;

@FragmentScope
@Component(modules = DexModule.class, dependencies = AppComponent.class)
public interface DexComponent {
    void inject(DexFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DexComponent.Builder view(DexContract.View view);

        DexComponent.Builder appComponent(AppComponent appComponent);

        DexComponent build();
    }
}