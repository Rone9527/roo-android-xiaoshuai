package com.roo.mine.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.di.module.AddressBookEditModule;
import com.roo.mine.mvp.contract.AddressBookEditContract;
import com.roo.mine.mvp.ui.activity.AddressBookEditActivity;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = AddressBookEditModule.class, dependencies = AppComponent.class)
public interface AddressBookEditComponent {
    void inject(AddressBookEditActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddressBookEditComponent.Builder view(AddressBookEditContract.View view);

        AddressBookEditComponent.Builder appComponent(AppComponent appComponent);

        AddressBookEditComponent build();
    }
}