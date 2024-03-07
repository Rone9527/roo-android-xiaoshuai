package com.roo.mine.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.di.module.LegalCurrencyModule;
import com.roo.mine.mvp.contract.LegalCurrencyContract;
import com.roo.mine.mvp.ui.activity.LegalCurrencyActivity;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = LegalCurrencyModule.class, dependencies = AppComponent.class)
public interface LegalCurrencyComponent {
    void inject(LegalCurrencyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LegalCurrencyComponent.Builder view(LegalCurrencyContract.View view);

        LegalCurrencyComponent.Builder appComponent(AppComponent appComponent);

        LegalCurrencyComponent build();
    }
}