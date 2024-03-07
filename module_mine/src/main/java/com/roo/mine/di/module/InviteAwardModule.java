package com.roo.mine.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.mine.mvp.contract.InviteAwardContract;
import com.roo.mine.mvp.model.InviteAwardModel;

@Module
public abstract class InviteAwardModule {

    @Binds
    abstract InviteAwardContract.Model bindInviteAwardModel(InviteAwardModel model);
}