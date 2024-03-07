package com.roo.trade.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.roo.trade.di.module.MarketModule;
import com.roo.trade.mvp.contract.MarketContract;
import com.roo.trade.mvp.ui.fragment.MarketFragment;

import dagger.BindsInstance;
import dagger.Component;

;

@FragmentScope
@Component(modules = MarketModule.class, dependencies = AppComponent.class)
public interface MarketComponent {
    void inject(MarketFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MarketComponent.Builder view(MarketContract.View view);

        MarketComponent.Builder appComponent(AppComponent appComponent);

        MarketComponent build();
    }
}