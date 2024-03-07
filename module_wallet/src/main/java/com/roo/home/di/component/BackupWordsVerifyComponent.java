package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.home.di.module.BackupWordsVerifyModule;
import com.roo.home.mvp.contract.BackupWordsVerifyContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.ui.activity.BackupWordsVerifyActivity;;

@ActivityScope
@Component(modules = BackupWordsVerifyModule.class, dependencies = AppComponent.class)
public interface BackupWordsVerifyComponent {
    void inject(BackupWordsVerifyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BackupWordsVerifyComponent.Builder view(BackupWordsVerifyContract.View view);

        BackupWordsVerifyComponent.Builder appComponent(AppComponent appComponent);

        BackupWordsVerifyComponent build();
    }
}