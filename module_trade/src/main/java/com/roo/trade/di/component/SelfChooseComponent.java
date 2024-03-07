package com.roo.trade.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.roo.trade.di.module.SelfChooseModule;
import com.roo.trade.mvp.contract.SelfChooseContract;
import com.roo.trade.mvp.ui.fragment.DeFiSelfChooseFragment;

import dagger.BindsInstance;
import dagger.Component;

;

@FragmentScope
@Component(modules = SelfChooseModule.class, dependencies = AppComponent.class)
public interface SelfChooseComponent {
    void inject(DeFiSelfChooseFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SelfChooseComponent.Builder view(SelfChooseContract.View view);

        SelfChooseComponent.Builder appComponent(AppComponent appComponent);

        SelfChooseComponent build();
    }
}