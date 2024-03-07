package com.roo.core.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;


/**
 * Created by Mango
 * on 2017/4/11.
 */

public class DialogLoading {
    private static Handler mMainHandler = new Handler(Looper.getMainLooper());
    public static Dialog dialog;

    private static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private DialogLoading() {

    }

    public static DialogLoading getInstance() {
        return DialogHolder.instantce;
    }

    public void showDialog(Context context) {
        if (isMainThread()) {
            showDialog(context, true);
        } else {
            mMainHandler.post(() -> showDialog(context, true));
        }

    }

    public void showDialog(Context context, boolean cancelable) {
        try {
            if (dialog == null) {
                dialog = ProgressFactory.newLoading(context);
            } else {
                closeDialog();
                dialog = ProgressFactory.newLoading(context);
            }
            dialog.setCancelable(cancelable);
            if (!dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDialog() {
        try {

            if (isMainThread()) {
                if (dialog != null) {
                    ProgressFactory.closeDialog(dialog);
                    dialog = null;
                }
            } else {
                mMainHandler.post(() -> {
                    if (dialog != null) {
                        ProgressFactory.closeDialog(dialog);
                        dialog = null;
                    }
                });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class DialogHolder {
        static DialogLoading instantce = new DialogLoading();
    }
}
