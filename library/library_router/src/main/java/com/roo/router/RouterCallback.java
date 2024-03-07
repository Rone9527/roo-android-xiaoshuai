package com.roo.router;

import android.content.Context;

/**
 * Created by wanglei on 2016/11/29.
 */

public interface RouterCallback {

    void onBefore(Context from, Class<?> to);

    void onNext(Context from, Class<?> to);

    void onError(Context from, Class<?> to, Throwable throwable);

}
