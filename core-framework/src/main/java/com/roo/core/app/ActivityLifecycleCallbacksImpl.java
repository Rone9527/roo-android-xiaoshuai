package com.roo.core.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.roo.core.utils.LogManage;

public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = ActivityLifecycleCallbacksImpl.class.getSimpleName();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogManage.w(TAG + " -  " + activity + " - onActivityCreated");
        GlobalContext.setCurrActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogManage.w(TAG + " -  " + activity + " - onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogManage.w(TAG + " -  " + activity + " - onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogManage.w(TAG + " -  " + activity + " - onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogManage.w(TAG + " -  " + activity + " - onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        LogManage.w(TAG + " -  " + activity + " - onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogManage.w(TAG + " -  " + activity + " - onActivityDestroyed");
    }
}
