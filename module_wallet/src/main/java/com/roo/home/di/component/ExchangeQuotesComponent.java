package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.home.di.module.ExchangeQuotesModule;
import com.roo.home.mvp.contract.ExchangeQuotesContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.ui.activity.ExchangeQuotesActivity;;

@ActivityScope
@Component(modules = ExchangeQuotesModule.class, dependencies = AppComponent.class)
public interface ExchangeQuotesComponent {
    void inject(ExchangeQuotesActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExchangeQuotesComponent.Builder view(ExchangeQuotesContract.View view);

        ExchangeQuotesComponent.Builder appComponent(AppComponent appComponent);

        ExchangeQuotesComponent build();
    }
}