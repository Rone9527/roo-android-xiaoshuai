package com.roo.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.di.module.CashierModule;
import com.roo.home.mvp.contract.CashierContract;
import com.roo.home.mvp.ui.activity.CashierActivity;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = CashierModule.class, dependencies = AppComponent.class)
public interface CashierComponent {
    void inject(CashierActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CashierComponent.Builder view(CashierContract.View view);

        CashierComponent.Builder appComponent(AppComponent appComponent);

        CashierComponent build();
    }
}