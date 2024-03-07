package com.roo.trade.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.trade.mvp.contract.DefiSearchContract;
import com.roo.trade.mvp.ui.activity.DefiSearchActivity;
import com.roo.trade.di.module.DefiSearchModule;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = DefiSearchModule.class, dependencies = AppComponent.class)
public interface DefiSearchComponent {
    void inject(DefiSearchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DefiSearchContract.View view);

        Builder appComponent(AppComponent appComponent);

        DefiSearchComponent build();
    }
}