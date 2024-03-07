package com.roo.home.di.module;

import dagger.Binds;
import dagger.Module;

import com.roo.home.mvp.contract.SafetyQuestionnaireContract;
import com.roo.home.mvp.model.SafetyQuestionnaireModel;

@Module
public abstract class SafetyQuestionnaireModule {

    @Binds
    abstract SafetyQuestionnaireContract.Model bindSafetyQuestionnaireModel(SafetyQuestionnaireModel model);
}