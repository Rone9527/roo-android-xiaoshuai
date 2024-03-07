package com.roo.core.utils.handler;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by TZT on 2017/6/24.
 */

public class WeakHandler<T extends MessageHandler> extends Handler {

    private WeakReference<T> weakReference;

    public WeakHandler(T messageHandler) {
        this.weakReference = new WeakReference<>(messageHandler);
    }

    @Override
    public void handleMessage(Message msg) {
        if (weakReference != null) {
            T t = this.weakReference.get();
            if (t != null) {
                t.handleMessage(msg);
            }
        }
    }
}
