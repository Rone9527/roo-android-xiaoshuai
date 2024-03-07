package com.roo.core.utils.handler;

import android.os.Handler;
import android.os.Looper;

public class HandlerUtil {
    public static final Handler HANDLER = new Handler(Looper.getMainLooper());
    public static final Handler HANDLER_IO = new Handler();

    public static void runOnThread(Runnable runnable) {
        HANDLER_IO.post(runnable);
    }

    public static void runOnThreadDelay(Runnable runnable, long delayMillis) {
        HANDLER_IO.postDelayed(runnable, delayMillis);
    }

    public static void runOnUiThread(Runnable runnable) {
        HANDLER.post(runnable);
    }

    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis) {
        HANDLER.postDelayed(runnable, delayMillis);
    }

    public static void removeRunable(Runnable runnable) {
        HANDLER.removeCallbacks(runnable);
        HANDLER_IO.removeCallbacks(runnable);
    }

    /**
     * Remove any pending posts of callbacks and sent messages whose
     * obj is token. If token is null, all callbacks and messages will be removed.
     */
    public static void removeCallbacksAndMessages() {
        HANDLER.removeCallbacksAndMessages(null);
        HANDLER_IO.removeCallbacksAndMessages(null);
    }

}
