package com.roo.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.mine.di.module.InviteModule;
import com.roo.mine.mvp.contract.InviteContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.mvp.ui.activity.InviteActivity;;

@ActivityScope
@Component(modules = InviteModule.class, dependencies = AppComponent.class)
public interface InviteComponent {
    void inject(InviteActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InviteComponent.Builder view(InviteContract.View view);

        InviteComponent.Builder appComponent(AppComponent appComponent);

        InviteComponent build();
    }
}