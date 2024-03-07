package com.roo.mine.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.mvp.contract.NoticeContract;
import com.roo.mine.mvp.model.NoticeModel;
import com.roo.mine.mvp.ui.adapter.NoticeAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class NoticeModule {

    @Binds
    abstract NoticeContract.Model bindNoticeModel(NoticeModel model);

    @ActivityScope
    @Provides
    static NoticeAdapter provideNoticeAdapter() {
        return new NoticeAdapter();
    }

}