package com.roo.core.utils;


import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.core.content.ContextCompat;

import com.roo.core.app.GlobalContext;

/**
 * Created by DF on 2017/9/6.
 */

public class ResourceUtil {

    /**
     * dp转化成px
     *
     * @param dpValue
     */
    public static int dp2px(int dpValue) {
        return dp2px(dpValue * 1.0f);
    }

    public static int dp2px(float dpValue) {
        final float scale = GlobalContext.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转成dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dp(int pxValue) {
        return px2dp(pxValue * 1.0f);
    }

    public static int px2dp(float pxValue) {
        final float scale = GlobalContext.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px转成sp
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(int pxValue) {
        return px2sp(pxValue * 1.0f);
    }

    public static int px2sp(float pxValue) {
        final float scale = GlobalContext.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int sp2px(int spValue) {
        return sp2px(spValue * 1.0f);
    }

    public static int sp2px(float spValue) {
        final float scale = GlobalContext.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(GlobalContext.getAppContext(), resId);
    }

    public static float getDimension(@DimenRes int resId) {
        return GlobalContext.getAppContext().getResources().getDimension(resId);
    }

}
