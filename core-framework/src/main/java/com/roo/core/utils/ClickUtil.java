package com.roo.core.utils;

/**
 * Author  JTXC
 * Create Time  2020/5/19 13:01
 */
public class ClickUtil {

    // 两次点击按钮之间的点击间隔不能少于 600 毫秒
    private static final int MIN_CLICK_DELAY_TIME = 600;
    private static long lastClickTime = 0;

    public static boolean clickInFronzen() {
        return !clickAble(MIN_CLICK_DELAY_TIME);
    }

    public static boolean clickInFronzen(int clickDelayTime) {
        return !clickAble(clickDelayTime);
    }

    public static boolean clickAble(int clickDelayTime) {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= clickDelayTime) {
            lastClickTime = curClickTime;
            flag = true;
        }
        return flag;
    }
}
