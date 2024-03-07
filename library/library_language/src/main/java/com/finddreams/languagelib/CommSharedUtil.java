package com.finddreams.languagelib;


import android.content.Context;
import android.content.SharedPreferences;


/**
 * CommSharedUtils
 * 通用的SharedPreferences
 */
public class CommSharedUtil {

    private static final String SHARED_PATH = "app_info_str";
    private static CommSharedUtil helper;
    private SharedPreferences sharedPreferences;


    private CommSharedUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
    }

    public static CommSharedUtil getInstance(Context context) {
        if (helper == null) {
            helper = new CommSharedUtil(context);
        }
        return helper;
    }

    public void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

}
