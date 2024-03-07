package com.roo.mine.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.mine.mvp.contract.NoticeDetailContract;
import com.roo.mine.mvp.model.NoticeDetailModel;

@Module
public abstract class NoticeDetailModule {

    @Binds
    abstract NoticeDetailContract.Model bindNoticeDetailModel(NoticeDetailModel model);
}