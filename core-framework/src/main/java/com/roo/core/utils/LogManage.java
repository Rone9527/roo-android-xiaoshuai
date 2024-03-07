package com.roo.core.utils;

import android.text.TextUtils;

import com.roo.core.app.constants.GlobalConstant;

import timber.log.Timber;

/**
 * Created by TaoHui on 2018/10/6.
 */

public class LogManage {

    public static final boolean isDeBug = GlobalConstant.DEBUG_MODEL;
    private static final String TAG = "com.roo.log";

    //%%%%%%%%%%%%%%%% info %%%%%%%%%%%%%%%%
    public static void i(boolean message) {
        i(null, message ? "true" : "false");
    }

    public static void i(String name, boolean message) {
        i(name, message ? "true" : "false");
    }

    public static void i(int message) {
        i(null, String.valueOf(message));
    }

    public static void i(String name, int message) {
        i(name, String.valueOf(message));
    }

    public static void i(double message) {
        i(null, String.valueOf(message));
    }

    public static void i(String name, double message) {
        i(name, String.valueOf(message));
    }

    public static void i(float message) {
        i(null, String.valueOf(message));
    }

    public static void i(String name, float message) {
        i(name, String.valueOf(message));
    }

    public static void i(String message) {
        i(null, message);
    }

    public static void i(String name, String message) {
        if (isDeBug) {
            if (TextUtils.isEmpty(name)) {
                Timber.tag(TAG).i(message);
            } else {
                Timber.tag(TAG).i(name + "   :   " + message);
            }
        }
    }

    //%%%%%%%%%%%%%%%% info %%%%%%%%%%%%%%%%

    //%%%%%%%%%%%%%%%% debug %%%%%%%%%%%%%%%%
    public static void d(boolean message) {
        d(null, message ? "true" : "false");
    }

    public static void d(String name, boolean message) {
        d(name, message ? "true" : "false");
    }

    public static void d(int message) {
        d(null, String.valueOf(message));
    }

    public static void d(String name, int message) {
        d(name, String.valueOf(message));
    }

    public static void d(double message) {
        d(null, String.valueOf(message));
    }

    public static void d(String name, double message) {
        d(name, String.valueOf(message));
    }

    public static void d(float message) {
        d(null, String.valueOf(message));
    }

    public static void d(String name, float message) {
        d(name, String.valueOf(message));
    }

    public static void d(String message) {
        d(null, message);
    }

    public static void d(String name, String message) {
        if (isDeBug) {
            if (TextUtils.isEmpty(name)) {
                Timber.tag(TAG).d(message);
            } else {
                Timber.tag(TAG).d(name + "   :   " + message);
            }
        }
    }

    //%%%%%%%%%%%%%%%% debug %%%%%%%%%%%%%%%%

    //%%%%%%%%%%%%%%%% verbose %%%%%%%%%%%%%%%%
    public static void v(boolean message) {
        v(null, message ? "true" : "false");
    }

    public static void v(String name, boolean message) {
        v(name, message ? "true" : "false");
    }

    public static void v(int message) {
        v(null, String.valueOf(message));
    }

    public static void v(String name, int message) {
        v(name, String.valueOf(message));
    }

    public static void v(double message) {
        v(null, String.valueOf(message));
    }

    public static void v(String name, double message) {
        v(name, String.valueOf(message));
    }

    public static void v(float message) {
        v(null, String.valueOf(message));
    }

    public static void v(String name, float message) {
        v(name, String.valueOf(message));
    }

    public static void v(String message) {
        v(null, message);
    }

    public static void v(String name, String message) {
        if (isDeBug) {
            if (TextUtils.isEmpty(name)) {
                Timber.tag(TAG).v(message);
            } else {
                Timber.tag(TAG).v(name + "   :   " + message);
            }
        }
    }

    //%%%%%%%%%%%%%%%% verbose %%%%%%%%%%%%%%%%

    //%%%%%%%%%%%%%%%% warn %%%%%%%%%%%%%%%%
    public static void w(boolean message) {
        w(null, message ? "true" : "false");
    }

    public static void w(String name, boolean message) {
        w(name, message ? "true" : "false");
    }

    public static void w(int message) {
        w(null, String.valueOf(message));
    }

    public static void w(String name, int message) {
        w(name, String.valueOf(message));
    }

    public static void w(double message) {
        w(null, String.valueOf(message));
    }

    public static void w(String name, double message) {
        w(name, String.valueOf(message));
    }

    public static void w(float message) {
        w(null, String.valueOf(message));
    }

    public static void w(String name, float message) {
        w(name, String.valueOf(message));
    }

    public static void w(String message) {
        w(null, message);
    }

    public static void w(String name, String message) {
        if (isDeBug) {
            if (TextUtils.isEmpty(name)) {
                Timber.tag(TAG).w(message);
            } else {
                Timber.tag(TAG).w(name + "   :   " + message);
            }
        }
    }

    //%%%%%%%%%%%%%%%% warn %%%%%%%%%%%%%%%%

    //%%%%%%%%%%%%%%%% error %%%%%%%%%%%%%%%%
    public static void e(Throwable ex) {
        e(null, ex);
    }

    public static void e(String name, Throwable ex) {
        if (isDeBug) {
            if (TextUtils.isEmpty(name)) {
                Timber.tag(TAG).e(ex, "出现异常");
            } else {
                Timber.tag(TAG).e(ex, name + "   :   " + "出现异常");
            }
        }
    }

    public static void e(int message) {
        e(String.valueOf(message));
    }

    public static void e(String name, int message) {
        e(name + "   :   " + message);
    }

    public static void e(boolean message) {
        e(message ? "true" : "false");
    }

    public static void e(String name, boolean message) {
        e(name + "   :   " + message);
    }

    public static void e(String message) {
        e(null, message);
    }

    public static void e(String name, String message) {

        if (isDeBug) {
            if (TextUtils.isEmpty(name)) {
                Timber.tag(TAG).e(message);
            } else {
                Timber.tag(TAG).e(name + "   :   " + message);
            }
        }
    }
    //%%%%%%%%%%%%%%%% error %%%%%%%%%%%%%%%%

}
