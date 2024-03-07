package com.roo.mine.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.roo.mine.di.module.MineModule;
import com.roo.mine.mvp.ui.fragment.MineFragment;

import dagger.Component;

@FragmentScope
@Component(modules = MineModule.class, dependencies = AppComponent.class)
public interface MineComponent {
    void inject(MineFragment fragment);
}