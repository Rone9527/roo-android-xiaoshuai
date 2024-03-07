package com.roo.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.contract.TransferRecordContract;
import com.roo.home.mvp.model.TransferRecordModel;
import com.roo.home.mvp.ui.adapter.TransferRecordAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class TransferRecordModule {

    @Binds
    abstract TransferRecordContract.Model bindTransferRecordModel(TransferRecordModel model);

    @ActivityScope
    @Provides
    static TransferRecordAdapter provideTransferRecordAdapter() {
        return new TransferRecordAdapter();
    }

}