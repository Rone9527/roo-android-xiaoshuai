package com.roo.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.di.module.BackupWordsModule;
import com.roo.home.mvp.contract.BackupWordsContract;
import com.roo.home.mvp.ui.activity.BackupWordsActivity;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = BackupWordsModule.class, dependencies = AppComponent.class)
public interface BackupWordsComponent {
    void inject(BackupWordsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BackupWordsComponent.Builder view(BackupWordsContract.View view);

        BackupWordsComponent.Builder appComponent(AppComponent appComponent);

        BackupWordsComponent build();
    }
}