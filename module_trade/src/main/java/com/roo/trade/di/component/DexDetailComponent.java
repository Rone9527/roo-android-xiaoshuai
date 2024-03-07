package com.roo.trade.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.trade.di.module.DexDetailModule;
import com.roo.trade.mvp.contract.DexDetailContract;
import com.roo.trade.mvp.ui.activity.DexDetailActivity;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = DexDetailModule.class, dependencies = AppComponent.class)
public interface DexDetailComponent {
    void inject(DexDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DexDetailComponent.Builder view(DexDetailContract.View view);

        DexDetailComponent.Builder appComponent(AppComponent appComponent);

        DexDetailComponent build();
    }
}