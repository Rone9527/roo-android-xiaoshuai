package com.roo.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.di.module.TransferDetailModule;
import com.roo.home.mvp.contract.TransferDetailContract;
import com.roo.home.mvp.ui.activity.TransferDetailActivity;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = TransferDetailModule.class, dependencies = AppComponent.class)
public interface TransferDetailComponent {
    void inject(TransferDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TransferDetailComponent.Builder view(TransferDetailContract.View view);

        TransferDetailComponent.Builder appComponent(AppComponent appComponent);

        TransferDetailComponent build();
    }
}