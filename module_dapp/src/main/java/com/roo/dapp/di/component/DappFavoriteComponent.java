package com.roo.dapp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.dapp.di.module.DappFavoriteModule;
import com.roo.dapp.mvp.contract.DappFavoriteContract;
import com.roo.dapp.mvp.ui.activity.DappFavoriteActivity;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = DappFavoriteModule.class, dependencies = AppComponent.class)
public interface DappFavoriteComponent {
    void inject(DappFavoriteActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DappFavoriteComponent.Builder view(DappFavoriteContract.View view);

        DappFavoriteComponent.Builder appComponent(AppComponent appComponent);

        DappFavoriteComponent build();
    }
}