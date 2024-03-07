package com.roo.mine.mvp.ui.utils;

import android.text.TextUtils;

import com.core.domain.mine.AddressBookBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiameng.mmkv.SharedPref;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/13 18:12
 *     desc        : 描述--//AddressManager
 * </pre>
 */

public class AddressBookManager {
    public static final String KEY_ADDRESS = "KEY_ADDRESS";

    private AddressBookManager() {

    }

    public static AddressBookManager getInstance() {
        return Holder.instantce;
    }

    private static class Holder {
        static AddressBookManager instantce = new AddressBookManager();
    }

    public void removeAddress(long id) {
        List<AddressBookBean> addressList = getAddressList();
        Iterator<AddressBookBean> iterator = addressList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
            }
        }
        SharedPref.putString(KEY_ADDRESS, new Gson().toJson(addressList));
    }

    public AddressBookBean getAddressById(long id) {
        List<AddressBookBean> addressList = getAddressList();
        for (AddressBookBean addressBookBean : addressList) {
            if (addressBookBean.getId() == id) {
                return addressBookBean;
            }
        }
        return null;
    }

    public List<AddressBookBean> getAddressList() {
        String json = SharedPref.getString(KEY_ADDRESS);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(json, new TypeToken<List<AddressBookBean>>() {
        }.getType());
    }

    public void addAddress(AddressBookBean addressBook) {
        AddressBookBean addressById = getAddressById(addressBook.getId());
        if (addressById != null) {
            removeAddress(addressBook.getId());
        }
        List<AddressBookBean> addressList = getAddressList();
        addressList.add(0, addressBook);
        SharedPref.putString(KEY_ADDRESS, new Gson().toJson(addressList));
    }

}
