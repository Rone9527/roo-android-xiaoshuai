package com.roo.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.mine.di.module.InviteAwardModule;
import com.roo.mine.mvp.contract.InviteAwardContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.mvp.ui.activity.InviteAwardActivity;;

@ActivityScope
@Component(modules = InviteAwardModule.class, dependencies = AppComponent.class)
public interface InviteAwardComponent {
    void inject(InviteAwardActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InviteAwardComponent.Builder view(InviteAwardContract.View view);

        InviteAwardComponent.Builder appComponent(AppComponent appComponent);

        InviteAwardComponent build();
    }
}