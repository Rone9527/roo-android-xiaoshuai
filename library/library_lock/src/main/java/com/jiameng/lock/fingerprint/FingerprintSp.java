package com.jiameng.lock.fingerprint;

import com.jiameng.mmkv.SharedPref;

public class FingerprintSp {

    public static final String DATA_KEY_NAME = "DATA_KEY_NAME";
    public static final String IV_KEY_NAME = "IV_KEY_NAME";

    String getData(String keyName) {
        return SharedPref.getString(keyName, "");
    }

    void storeData(String key, String data) {
        SharedPref.putString(key, data);
    }
}
