package com.roo.core.utils;

import com.jiameng.mmkv.SharedPref;
import com.roo.core.utils.utils.HashUtil;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/15 19:53
 *     desc        : 描述--//SafePassword
 * </pre>
 */

public class SafePassword {

    private static final String WALLET_PASSWORD = "WALLET_PASSWORD";

    public static boolean isEquals(String text) {
        return HashUtil.getSHA256StrJava(text).equals(SharedPref.getString(WALLET_PASSWORD));
    }

    public static boolean isExist() {
        return SharedPref.contains(WALLET_PASSWORD);
    }

    public static void update(String sha256) {
        SharedPref.putString(WALLET_PASSWORD, sha256);
    }

    public static String get() {
        return SharedPref.getString(WALLET_PASSWORD);
    }
}
