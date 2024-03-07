package com.roo.dapp.mvp.utils.connect;

import android.content.Context;
import android.text.TextUtils;

import com.roo.dapp.mvp.utils.connect.SharedPreferenceManager;

import java.util.UUID;

public class UserManager {
    public static String getRandomUUID(Context context) {
        String savedUUID = SharedPreferenceManager.getSavedUUID(context);
        if (TextUtils.isEmpty(savedUUID)) {
            // 创建UUID
            savedUUID = UUID.randomUUID().toString();
            SharedPreferenceManager.setSavedUUID(savedUUID,context);
        }
        return savedUUID;
    }
}
