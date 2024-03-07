package com.roo.mine.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.mine.mvp.contract.InviteContract;
import com.roo.mine.mvp.model.InviteModel;

@Module
public abstract class InviteModule {

    @Binds
    abstract InviteContract.Model bindInviteModel(InviteModel model);
}