package com.roo.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.contract.BackupWordsContract;
import com.roo.home.mvp.model.BackupWordsModel;
import com.roo.home.mvp.ui.adapter.BackupWordsAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class BackupWordsModule {

    @Binds
    abstract BackupWordsContract.Model bindBackupWordsModel(BackupWordsModel model);


    @ActivityScope
    @Provides
    static BackupWordsAdapter provideBackupWordsAdapter() {
        return new BackupWordsAdapter();
    }

}