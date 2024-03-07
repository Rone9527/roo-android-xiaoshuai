package com.roo.mine.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.di.module.AddressBookModule;
import com.roo.mine.mvp.contract.AddressBookContract;
import com.roo.mine.mvp.ui.activity.AddressBookActivity;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = AddressBookModule.class, dependencies = AppComponent.class)
public interface AddressBookComponent {
    void inject(AddressBookActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddressBookComponent.Builder view(AddressBookContract.View view);

        AddressBookComponent.Builder appComponent(AppComponent appComponent);

        AddressBookComponent build();
    }
}