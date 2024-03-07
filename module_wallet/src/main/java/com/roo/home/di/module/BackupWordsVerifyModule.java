package com.roo.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.contract.BackupWordsVerifyContract;
import com.roo.home.mvp.model.BackupWordsVerifyModel;
import com.roo.home.mvp.ui.adapter.BackupWordsVerifyAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class BackupWordsVerifyModule {

    @Binds
    abstract BackupWordsVerifyContract.Model bindBackupWordsVerifyModel(BackupWordsVerifyModel model);

    @ActivityScope
    @Provides
    static BackupWordsVerifyAdapter provideBackupWordsVerifyAdapter() {
        return new BackupWordsVerifyAdapter();
    }

}