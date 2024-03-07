package com.roo.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.mine.di.module.NoticeModule;
import com.roo.mine.mvp.contract.NoticeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.mvp.ui.activity.NoticeActivity;;

@ActivityScope
@Component(modules = NoticeModule.class, dependencies = AppComponent.class)
public interface NoticeComponent {
    void inject(NoticeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NoticeComponent.Builder view(NoticeContract.View view);

        NoticeComponent.Builder appComponent(AppComponent appComponent);

        NoticeComponent build();
    }
}