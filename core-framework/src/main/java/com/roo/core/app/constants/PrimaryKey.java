package com.roo.core.app.constants;

import androidx.annotation.StringDef;

import com.jiameng.mmkv.SharedPref;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/12 09:38
 *     desc        : 描述--//PrimaryKey 主键自增长管理类
 * </pre>
 */

public class PrimaryKey {

    public static final String ADDRESS = "ADDRESS";
    public static final String WALLET_NAME = "WALLET_NAME";

    @StringDef({
            ADDRESS,
            WALLET_NAME,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyType {
    }


    public static int getIndexCurrent(@KeyType String key) {
        return SharedPref.getInt(key);
    }


    public static int getIndexNext(@KeyType String key) {
        return SharedPref.getInt(key) + 1;
    }

    public static void addIndex(@KeyType String key) {
        int current = SharedPref.getInt(key);
        SharedPref.putInt(key, current + 1);
    }


    public static void resetIndex(@KeyType String key) {
        SharedPref.remove(key);
    }

    public static void subIndex(@KeyType String key) {
        int current = SharedPref.getInt(key);
        SharedPref.putInt(key, current - 1);
    }
}
