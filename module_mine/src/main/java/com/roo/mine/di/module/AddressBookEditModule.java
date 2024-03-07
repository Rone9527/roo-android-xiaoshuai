package com.roo.mine.di.module;

import com.roo.mine.mvp.contract.AddressBookEditContract;
import com.roo.mine.mvp.model.AddressBookEditModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AddressBookEditModule {

    @Binds
    abstract AddressBookEditContract.Model bindAddressBookEditModel(AddressBookEditModel model);
}