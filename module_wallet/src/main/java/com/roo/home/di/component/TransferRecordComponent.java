package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.home.di.module.TransferRecordModule;
import com.roo.home.mvp.contract.TransferRecordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.ui.activity.TransferRecordActivity;
import com.roo.home.mvp.ui.fragment.TransferRecordFragment;;

@ActivityScope
@Component(modules = TransferRecordModule.class, dependencies = AppComponent.class)
public interface TransferRecordComponent {
    void inject(TransferRecordActivity activity);
    void inject(TransferRecordFragment fragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        TransferRecordComponent.Builder view(TransferRecordContract.View view);

        TransferRecordComponent.Builder appComponent(AppComponent appComponent);

        TransferRecordComponent build();
    }
}