package com.roo.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.mine.di.module.NoticeDetailModule;
import com.roo.mine.mvp.contract.NoticeDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.mvp.ui.activity.NoticeDetailActivity;;

@ActivityScope
@Component(modules = NoticeDetailModule.class, dependencies = AppComponent.class)
public interface NoticeDetailComponent {
    void inject(NoticeDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NoticeDetailComponent.Builder view(NoticeDetailContract.View view);

        NoticeDetailComponent.Builder appComponent(AppComponent appComponent);

        NoticeDetailComponent build();
    }
}