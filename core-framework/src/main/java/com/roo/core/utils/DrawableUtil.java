package com.roo.core.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.DrawableRes;

import com.roo.core.app.GlobalContext;


/**
 * Created by TZT on 2016/11/24.
 */

public class DrawableUtil {
    /**
     * 圆角
     *
     * @param dpSize
     * @param bgColor
     * @return
     */
    public static Drawable getRoundDrawable(int dpSize, int bgColor) {
        return getRoundPxDrawable(ResourceUtil.dp2px(dpSize), bgColor);
    }

    public static Drawable getRoundDrawable(float dpSize, int bgColor) {
        return getRoundPxDrawable(ResourceUtil.dp2px(dpSize), bgColor);
    }

    public static Drawable getRoundDrawable(float dpSize, int[] bgColor) {
        return getRoundPxDrawable(ResourceUtil.dp2px(dpSize), bgColor);
    }


    public static Drawable getRoundPxDrawable(int pxSize, int... bgColor) {
        final int len = bgColor.length;
        final int[] colors;
        if (len == 1) {
            colors = new int[2];
            colors[1] = bgColor[0];
        } else {
            colors = new int[len];
        }

        for (int i = 0; i < len; i++) {
            colors[i] = bgColor[i];
        }
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, colors);
        drawable.setCornerRadius(pxSize);
        return drawable;
    }
    /**
     --------------------------------------------选择器-----------------------------------------
     */
    /**
     * 点击
     *
     * @param pressed
     * @param normal
     * @return
     */
    public static Drawable getSelectorClickDrawable(Drawable pressed, Drawable normal) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, normal);
        return stateListDrawable;
    }

    public static Drawable getSelectorClickDrawable(int pressed, int normal) {
        Drawable drawableEnabled = getResId(pressed);
        Drawable drawablenormal = getResId(normal);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawableEnabled);
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, drawablenormal);
        return stateListDrawable;
    }

    /**
     * 选中
     *
     * @param checked
     * @param normal
     * @return
     */
    public static Drawable getSelectorCheckDrawable(Drawable checked, Drawable normal) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checked);
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, normal);
        return stateListDrawable;
    }

    public static Drawable getSelectorCheckDrawableByColor(int checked, int normal) {
        return getSelectorCheckDrawable(new ColorDrawable(checked), new ColorDrawable(normal));
    }

    public static Drawable getSelectorCheckDrawable(int checked, int normal) {
        Drawable drawableEnabled = getResId(checked);
        Drawable drawablenormal = getResId(normal);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, drawableEnabled);
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, drawablenormal);
        return stateListDrawable;
    }

    /**
     * 可用
     *
     * @param enabled
     * @param normal
     * @return
     */
    public static Drawable getSelectorEnableDrawable(Drawable enabled, Drawable normal) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, enabled);
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, normal);
        return stateListDrawable;
    }

    public static Drawable getSelectorEnableDrawable(@DrawableRes int enabled, @DrawableRes int normal) {

        Drawable drawableEnabled = getResId(enabled);
        Drawable drawablenormal = getResId(normal);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, drawableEnabled);
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, drawablenormal);
        return stateListDrawable;
    }

    public static Drawable getResId(@DrawableRes int id) {
        return GlobalContext.getAppContext().getResources().getDrawable(id);
    }

    /**
     * -------------------------------------------------------------------------------------------------
     */

}
