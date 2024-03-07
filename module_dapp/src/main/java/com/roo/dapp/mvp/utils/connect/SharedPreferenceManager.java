package com.roo.dapp.mvp.utils.connect;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferenceManager {

    private static final String SAVED_UUID = "savedUuid";
    private static final String WALLET_CONNECT_PEER_META = "walletConnectPeerMeta";

    public static String getSavedUUID(Context context) {
        SharedPreferences configSP = SharedPreferenceFactory.getConfigSP(context);
        return get(configSP, SAVED_UUID, "");
    }

    public static void setSavedUUID(String savedUUID,Context context) {
        SharedPreferences configSP = SharedPreferenceFactory.getConfigSP(context);
        SharedPreferences.Editor editor = configSP.edit();
        put(SAVED_UUID, savedUUID, editor);
    }

    public static String getWalletConnectPeerMeta(Context context) {
        SharedPreferences configSP = SharedPreferenceFactory.getConfigSP(context);
        return get(configSP, WALLET_CONNECT_PEER_META, "");
    }

    public static void setWalletConnectPeerMeta(String peerMeta,Context context) {
        SharedPreferences configSP = SharedPreferenceFactory.getConfigSP(context);
        SharedPreferences.Editor editor = configSP.edit();
        put(WALLET_CONNECT_PEER_META, peerMeta, editor);
    }




    private static void put(String key, String value, SharedPreferences.Editor editor) {
        editor.putString(key, value);
        editor.apply();
    }

    private static void put(String key, int value, SharedPreferences.Editor editor) {
        editor.putInt(key, value);
        editor.apply();
    }

    private static void put(String key, boolean value, SharedPreferences.Editor editor) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    private static void put(String key, long value, SharedPreferences.Editor editor) {
        editor.putLong(key, value);
        editor.apply();
    }

    private static String get(SharedPreferences preferences, String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    private static int get(SharedPreferences preferences, String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    private static boolean get(SharedPreferences preferences, String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    private static long get(SharedPreferences preferences, String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }
}
