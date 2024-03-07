package com.roo.core.utils;


import com.jiameng.mmkv.SharedPref;
import com.roo.core.R;
import com.roo.core.app.constants.Constants;

/**
 * @author oldnine
 * @since 2018/11/13
 */
public class ColorWalletUtil {

    public static int getMarketColor(float value) {
        return getMarketColor(value, false);
    }

    public static int getMarketColor(boolean isUp) {
        if (getColorType() == 0) {
            return ResourceUtil.getColor(isUp ? R.color.green_color : R.color.red_color);
        } else {
            return ResourceUtil.getColor(isUp ? R.color.red_color : R.color.green_color);
        }
    }

    public static int getMarketColor(float value, boolean zeroFill) {
        if (zeroFill && value == 0) {
            return ResourceUtil.getColor(R.color.white_color_assist_1);
        }
        if (getColorType() == 0) {
            return ResourceUtil.getColor(value > 0 ? R.color.green_color : R.color.red_color);
        } else {
            return ResourceUtil.getColor(value > 0 ? R.color.red_color : R.color.green_color);
        }
    }

    public static int getColorType() {
        return SharedPref.getInt(Constants.COLOR_TYPE, 0);
    }

}
