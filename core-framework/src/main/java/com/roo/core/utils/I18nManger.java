package com.roo.core.utils;

import android.text.TextUtils;

import com.roo.core.model.exception.ApiException;

import java.text.MessageFormat;
import java.util.HashMap;

/**
 * <pre>
 *     project name: client-android
 *     author      : 李琼
 *     create time : 2020/7/30 10:35
 *     desc        : 描述--//I18nManger
 * </pre>
 */
public class I18nManger {
    private static HashMap<String, String> language = new HashMap<>();

    public static void initLanguage(HashMap<String, String> result) {
        I18nManger.language = result;
    }

    public static HashMap<String, String> getLanguage() {
        return language;
    }

    public static String getLanguage(Throwable e) {
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            //return getLanguage(apiException.getResultCode(), apiException.getMessage());
        }
        return "";
    }

    public static String getLanguage(String code, String message) {
        if (TextUtils.isEmpty(message)) {
            return getLanguage(code);
        } else {
            if (message.contains(",")) {
                Object[] split = message.split(",");
                return MessageFormat.format(I18nManger.getLanguage(code), split);
            } else {
                return MessageFormat.format(I18nManger.getLanguage(code), message);
            }
        }
    }

    public static String getLanguage(String code) {
        String language = I18nManger.language.get(code);
        return TextUtils.isEmpty(language) ? code : language;
    }
}
