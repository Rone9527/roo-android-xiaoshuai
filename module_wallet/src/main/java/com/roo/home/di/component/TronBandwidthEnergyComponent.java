package com.roo.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.roo.home.di.module.TronBandwidthEnergyModule;
import com.roo.home.mvp.contract.TronBandwidthEnergyContract;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.home.mvp.ui.activity.TronBandwidthEnergyActivity;;

@ActivityScope
@Component(modules = TronBandwidthEnergyModule.class, dependencies = AppComponent.class)
public interface TronBandwidthEnergyComponent {
    void inject(TronBandwidthEnergyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TronBandwidthEnergyComponent.Builder view(TronBandwidthEnergyContract.View view);

        TronBandwidthEnergyComponent.Builder appComponent(AppComponent appComponent);

        TronBandwidthEnergyComponent build();
    }
}