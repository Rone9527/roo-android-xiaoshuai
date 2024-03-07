package com.roo.mine.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.di.module.LanguageModule;
import com.roo.mine.mvp.contract.LanguageContract;
import com.roo.mine.mvp.ui.activity.LanguageActivity;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = LanguageModule.class, dependencies = AppComponent.class)
public interface LanguageComponent {
    void inject(LanguageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LanguageComponent.Builder view(LanguageContract.View view);

        LanguageComponent.Builder appComponent(AppComponent appComponent);

        LanguageComponent build();
    }
}