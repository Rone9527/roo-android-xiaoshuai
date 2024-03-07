package com.roo.core.app;

import android.app.Activity;
import android.content.Context;

public class GlobalContext {

    private static Context sContext = null;

    private static Activity mCurrActivity = null;

    public static void setContext(Context context) {
        sContext = context.getApplicationContext();
    }

    public static Context getAppContext() {
        return sContext;
    }

    public static Activity getCurrActivity() {
        return mCurrActivity;
    }

    public static void setCurrActivity(Activity activity) {
        mCurrActivity = activity;
    }
}