package com.roo.core.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.jess.arms.utils.ArmsUtils;
import com.roo.core.R;
import com.roo.core.app.GlobalContext;

import me.drakeet.support.toast.ToastCompat;

public class ToastUtils {

    private static Handler mMainHandler = new Handler(Looper.getMainLooper());

    private static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private static void doShowToast(final CharSequence text) {
        doShowToast(text, Toast.LENGTH_SHORT);
    }

    private static void doShowToast(CharSequence text, int duration) {
        ToastCompat toastCompat = ToastCompat.makeText(GlobalContext.getAppContext(), text, duration)
                .setBadTokenListener(toast -> {
                    ArmsUtils.snackbarText(text.toString());
                });
        toastCompat.setGravity(Gravity.CENTER, 0, 0);
        View inflate = LayoutInflater.from(GlobalContext.getAppContext()).inflate(R.layout.widget_toast, null);
        TextView textView = inflate.findViewById(R.id.tv_toast_text);
        toastCompat.setView(inflate);
        textView.setText(text);
        toastCompat.setDuration(duration);
        toastCompat.show();
    }

    public static void show(@StringRes int textInt) {
        show(GlobalContext.getAppContext().getString(textInt));
    }

    //public static void showServerCode(ApiException e) {
    //    String text = I18nManger.getLanguage(e);
    //    if (TextUtils.isEmpty(text)) {
    //        return;
    //    }
    //    if (isMainThread()) {
    //        doShowToast(text);
    //    } else {
    //        mMainHandler.post(() -> doShowToast(text));
    //    }
    //}

    public static void show(CharSequence text) {
        if (isMainThread()) {
            doShowToast(text);
        } else {
            mMainHandler.post(() -> doShowToast(text));
        }
    }

    public static void showLong(@StringRes int textInt) {
        showLong(GlobalContext.getAppContext().getString(textInt));
    }

    public static void showLong(CharSequence text) {
        if (isMainThread()) {
            doShowToast(text, Toast.LENGTH_LONG);
        } else {
            mMainHandler.post(() -> doShowToast(text, Toast.LENGTH_LONG));
        }
    }

    public static void showError(@StringRes int textInt) {
        showError(GlobalContext.getAppContext().getString(textInt));
    }

    public static void showError(final CharSequence text) {
        if (isMainThread()) {
            doShowToast(text);
        } else {
            mMainHandler.post(() -> doShowToast(text));
        }
    }

    public static void showLongError(@StringRes int textInt) {
        showLongError(GlobalContext.getAppContext().getString(textInt));
    }

    public static void showLongError(CharSequence text) {
        if (isMainThread()) {
            doShowToast(text, Toast.LENGTH_LONG);
        } else {
            mMainHandler.post(() -> doShowToast(text, Toast.LENGTH_LONG));
        }
    }

    public static void showDebug(CharSequence text) {
        if (LogManage.isDeBug) {
            if (isMainThread()) {
                doShowToast("Debug:" + text, Toast.LENGTH_LONG);
            } else {
                mMainHandler.post(() -> doShowToast(text, Toast.LENGTH_LONG));
            }
        }
    }

}
