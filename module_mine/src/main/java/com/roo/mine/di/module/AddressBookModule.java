package com.roo.mine.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.roo.mine.mvp.contract.AddressBookContract;
import com.roo.mine.mvp.model.AddressBookModel;
import com.roo.mine.mvp.ui.adapter.AddressBookAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AddressBookModule {

    @Binds
    abstract AddressBookContract.Model bindAddressBookModel(AddressBookModel model);

    @ActivityScope
    @Provides
    static AddressBookAdapter provideAddressBookAdapter() {
        return new AddressBookAdapter();
    }

}