package com.roo.trade.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.roo.trade.di.module.FinanceModule;
import com.roo.trade.mvp.contract.FinanceContract;
import com.roo.trade.mvp.ui.fragment.FinanceFragment;

import dagger.BindsInstance;
import dagger.Component;

;

@FragmentScope
@Component(modules = FinanceModule.class, dependencies = AppComponent.class)
public interface FinanceComponent {
    void inject(FinanceFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FinanceComponent.Builder view(FinanceContract.View view);

        FinanceComponent.Builder appComponent(AppComponent appComponent);

        FinanceComponent build();
    }
}