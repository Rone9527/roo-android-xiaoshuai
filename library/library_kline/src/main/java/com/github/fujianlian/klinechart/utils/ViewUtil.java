package com.github.fujianlian.klinechart.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.github.fujianlian.klinechart.R;
import com.jiameng.mmkv.SharedPref;

/**
 * Created by tian on 2016/4/11.
 */
public class ViewUtil {
    public static final String COLOR_TYPE = "COLOR_TYPE";
    private static int decreaseColor;
    private static int increaseColor;

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int getColorType(Context context) {
        return SharedPref.getInt(ViewUtil.COLOR_TYPE, 0);
    }

    public static void initColor(Context context) {
        if (getColorType(context) == 0) {
            decreaseColor = ContextCompat.getColor(context, R.color.chart_red);
            increaseColor = ContextCompat.getColor(context, R.color.chart_green);
        } else {
            decreaseColor = ContextCompat.getColor(context, R.color.chart_green);
            increaseColor = ContextCompat.getColor(context, R.color.chart_red);
        }
    }

    public static int getDecreaseColor(Context context) {
        if (decreaseColor == 0) {
            initColor(context);
        }
        return decreaseColor;
    }

    public static int getIncreaseColor(Context context) {
        if (increaseColor == 0) {
            initColor(context);
        }
        return increaseColor;
    }


}
