package com.roo.trade.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.roo.trade.di.module.TradeModule;
import com.roo.trade.mvp.ui.fragment.QuotesFragment;

import dagger.Component;

@FragmentScope
@Component(modules = TradeModule.class, dependencies = AppComponent.class)
public interface TradeComponent {
    void inject(QuotesFragment fragment);
}