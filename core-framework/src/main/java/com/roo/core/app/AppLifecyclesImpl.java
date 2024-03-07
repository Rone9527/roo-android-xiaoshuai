package com.roo.core.app;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.jess.arms.base.delegate.AppLifecycles;

/**
 * ================================================
 * 展示 {@link AppLifecycles} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class AppLifecyclesImpl implements AppLifecycles {

    @Override
    public void attachBaseContext(@NonNull Context base) {
        try {
            MultiDex.install(base);
        } catch (Exception ignore) {
        }
    }

    @Override
    public void onCreate(@NonNull Application application) {
    }

    @Override
    public void onTerminate(@NonNull Application application) {
    }
}
