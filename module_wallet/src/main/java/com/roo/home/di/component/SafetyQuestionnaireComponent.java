package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.home.di.module.SafetyQuestionnaireModule;
import com.roo.home.mvp.contract.SafetyQuestionnaireContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.ui.activity.SafetyQuestionnaireActivity;;

@ActivityScope
@Component(modules = SafetyQuestionnaireModule.class, dependencies = AppComponent.class)
public interface SafetyQuestionnaireComponent {
    void inject(SafetyQuestionnaireActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SafetyQuestionnaireComponent.Builder view(SafetyQuestionnaireContract.View view);

        SafetyQuestionnaireComponent.Builder appComponent(AppComponent appComponent);

        SafetyQuestionnaireComponent build();
    }
}