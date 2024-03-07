package com.roo.core.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;


import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;

import com.jess.arms.utils.ArmsUtils;

import java.util.Random;


public class ColorUtils {

    private static final int CHECKED_ATTR = android.R.attr.state_checked;
    private static final int PRESSED_ATTR = android.R.attr.state_pressed;
    private static final int ENABLE_ATTR = android.R.attr.state_enabled;

    /**
     * 修改颜色透明度
     *
     * @param color
     * @param alpha
     * @return
     */
    public static int getAlphaColor(int color, @FloatRange(from = 0f, to = 1.0f) float alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(((int) (alpha * 255)), red, green, blue);
    }

    /**
     * 图片去色,返回灰度图片
     *
     * @param mContext
     * @param bmpOriginal 传入的图片
     * @return 去色后的图片
     */
    public static Bitmap toGrayScale(Context mContext, Bitmap bmpOriginal) {
        int height = bmpOriginal.getHeight();
        int width = bmpOriginal.getWidth();

        int extra = ArmsUtils.dip2px(mContext, 1);//某些机型需要添加一点额外的宽高
        Bitmap bmpGrayscale = Bitmap.createBitmap(width + extra, height + extra, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        new Canvas(bmpGrayscale).drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    /**
     * 通过分析背景色来决定当前文字的匹配颜色，使文字颜色自适应背景颜色
     */
    public static int parseBackgroundColor(int color) {
        return parseBackgroundColor(color, 0, 0);
    }

    public static int parseBackgroundColor(int color, int defultDark, int defaultLight) {
        int counter = 0;
        counter += Color.red(color) >= 128 ? 1 : 0;
        counter += Color.green(color) >= 128 ? 1 : 0;
        counter += Color.blue(color) >= 128 ? 1 : 0;
        return counter >= 2 ? defultDark == 0 ? Color.BLACK : defultDark :
                defaultLight == 0 ? Color.WHITE : defaultLight;
    }

    // #FF55FF => color
    // int color = Color.parseColor("#b64242");

    /**
     * @param color 背景颜色
     * @return 前景色是否深色
     */
    public static boolean isColorDark(int color) {
        float a = (Color.red(color) * 0.299f + Color.green(color) * 0.587f + Color.blue(color) * 0.114f);
        return a > 180;
    }

    public static int getDarkColor(int color) {
        int darkColor;
        int r = Math.max(Color.red(color) - 25, 0);
        int g = Math.max(Color.green(color) - 25, 0);
        int b = Math.max(Color.blue(color) - 25, 0);
        darkColor = Color.rgb(r, g, b);
        return darkColor;
    }

    /**
     * 加深颜色
     *
     * @param color 原色
     * @return 加深后的
     */
    public static int colorDeep(int color) {

        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        float ratio = 0.8F;

        red = (int) (red * ratio);
        green = (int) (green * ratio);
        blue = (int) (blue * ratio);

        return Color.argb(alpha, red, green, blue);
    }

    // color -> #FF55FF
    public static String toRGBHexString(int color) {
        return toRGBHexString(Color.red(color), Color.green(color), Color.blue(color));
    }

    // (r,g,b) -> #FF55FF
    public static String toRGBHexString(int red, int green, int blue) {
        return toARGBHexString(-1, red, green, blue);
    }

    // color -> #FFFF55FF
    public static String toRGBHexString(int alpha, int color) {
        return toARGBHexString(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    // default prefix: "#"
    // (a,r,g,b) -> #FF55FF55
    public static String toARGBHexString(int alpha, int red, int green, int blue) {
        return toARGBHexString("#", alpha, red, green, blue);
    }

    public static String toARGBHexString(String prefix, int alpha, int red, int green, int blue) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        if (alpha != -1) {
            String mAlphaStr = Integer.toHexString(alpha);
            sb.append(mAlphaStr.length() == 1 ? "0" + mAlphaStr : mAlphaStr);
        }
        String mRedStr = Integer.toHexString(red);
        sb.append(mRedStr.length() == 1 ? "0" + mRedStr : mRedStr);
        String mGreenStr = Integer.toHexString(green);
        sb.append(mGreenStr.length() == 1 ? "0" + mGreenStr : mGreenStr);
        String mBlueStr = Integer.toHexString(blue);
        sb.append(mBlueStr.length() == 1 ? "0" + mBlueStr : mBlueStr);
        return sb.toString().toUpperCase();
    }

    /**
     * 颜色选择器
     *
     * @param pressedColor 按下的颜色
     * @param normalColor  正常的颜色
     * @return 颜色选择器
     */
    public static ColorStateList getColorStateList(int pressedColor, int normalColor) {
        //其他状态默认为白色
        return new ColorStateList(
                new int[][]{{android.R.attr.state_enabled, android.R.attr.state_pressed}, {android.R.attr.state_enabled}, {}},
                new int[]{pressedColor, normalColor, Color.WHITE});
    }

    /**
     * 按条件的到随机颜色
     *
     * @param alpha 透明
     * @param lower 下边界
     * @param upper 上边界
     * @return 颜色值
     */

    public static int getRandomColor(int alpha, int lower, int upper) {
        return new RandomColor(alpha, lower, upper).getColor();
    }

    /**
     * @return 获取随机色
     */
    public static int getRandomColor() {
        return new RandomColor(255, 80, 200).getColor();
    }

    public static int getComplimentColor(int color) {
        // get existing colors
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int blue = Color.blue(color);
        int green = Color.green(color);

        // find compliments
        red = (~red) & 0xff;
        blue = (~blue) & 0xff;
        green = (~green) & 0xff;

        return Color.argb(alpha, red, green, blue);
    }

    /**
     * Converts an int RGB color representation into a hexadecimal
     *
     * @param argbColor int RGB color
     * @return hexadecimal color representation
     */
    public static String getHexStringForARGB(int argbColor) {
        String hexString = "#";
        hexString += ARGBToHex(Color.alpha(argbColor));
        hexString += ARGBToHex(Color.red(argbColor));
        hexString += ARGBToHex(Color.green(argbColor));
        hexString += ARGBToHex(Color.blue(argbColor));

        return hexString;
    }

    /**
     * Converts an int R, G, or B value into a hexadecimal
     *
     * @param rgbVal int R, G, or B value
     * @return hexadecimal value
     */
    private static String ARGBToHex(int rgbVal) {
        String hexReference = "0123456789ABCDEF";

        rgbVal = Math.max(0, rgbVal);
        rgbVal = Math.min(rgbVal, 255);
        rgbVal = Math.round(rgbVal);

        return hexReference.charAt((rgbVal - rgbVal % 16) / 16) + "" + hexReference.charAt(rgbVal % 16);
    }

    /**
     * Returns the complimentary (opposite) color.
     * 互补色
     *
     * @return int RGB of compliment color
     */
    public static int getComplimentColor(Paint paint) {
        return getComplimentColor(paint.getColor());
    }

    public static ColorStateList generateThumbColorWithTintColor(final int tintColor) {
        int[][] states = new int[][]{
                {-ENABLE_ATTR, CHECKED_ATTR},
                {-ENABLE_ATTR},
                {PRESSED_ATTR, -CHECKED_ATTR},
                {PRESSED_ATTR, CHECKED_ATTR},
                {CHECKED_ATTR},
                {-CHECKED_ATTR}
        };

        int[] colors = new int[]{
                tintColor - 0xAA000000,
                0xFFBABABA,
                tintColor - 0x99000000,
                tintColor - 0x99000000,
                tintColor | 0xFF000000,
                0xFFEEEEEE
        };
        return new ColorStateList(states, colors);
    }

    public static ColorStateList generateBackColorWithTintColor(final int tintColor) {
        int[][] states = new int[][]{
                {-ENABLE_ATTR, CHECKED_ATTR},
                {-ENABLE_ATTR},
                {CHECKED_ATTR, PRESSED_ATTR},
                {-CHECKED_ATTR, PRESSED_ATTR},
                {CHECKED_ATTR},
                {-CHECKED_ATTR}
        };

        int[] colors = new int[]{
                tintColor - 0xE1000000,
                0x10000000,
                tintColor - 0xD0000000,
                0x20000000,
                tintColor - 0xD0000000,
                0x20000000
        };
        return new ColorStateList(states, colors);
    }

    /**
     * 随机颜色
     */
    public static class RandomColor {
        int alpha;
        int lower;
        int upper;

        public RandomColor(int alpha, int lower, int upper) {
            if (upper <= lower) {
                throw new IllegalArgumentException("must be lower < upper");
            }
            setAlpha(alpha);
            setLower(lower);
            setUpper(upper);
        }

        public int getColor() {
            //随机数是前闭  后开
            int red = getLower() + new Random().nextInt(getUpper() - getLower() + 1);
            int green = getLower() + new Random().nextInt(getUpper() - getLower() + 1);
            int blue = getLower() + new Random().nextInt(getUpper() - getLower() + 1);
            return Color.argb(getAlpha(), red, green, blue);
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
            this.alpha = alpha;
        }

        public int getLower() {
            return lower;
        }

        public void setLower(@IntRange(from = 0, to = 255) int lower) {
            this.lower = lower;
        }

        public int getUpper() {
            return upper;
        }

        public void setUpper(@IntRange(from = 0, to = 255) int upper) {
            this.upper = upper;
        }
    }

    /**
     * 半透明一个color  此方法中的color为头布局的背景颜色
     */
    public static int get2AlphaColor(int color) {
        int r = color >> 16 & 0xff;
        int g = color >> 8 & 0xff;
        int b = color & 0xff;

        r = (int) (r * 0.5f + 0.5f);
        g = (int) (g * 0.5f + 0.5f);
        b = (int) (b * 0.5f + 0.5f);
        return 0x7f << 24 | r << 16 | g << 8 | b;
    }
}