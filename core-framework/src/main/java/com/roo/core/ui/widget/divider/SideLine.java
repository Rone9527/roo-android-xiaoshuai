package com.roo.core.ui.widget.divider;


import androidx.annotation.ColorInt;

public class SideLine {

    public boolean isHave;
    /**
     * A single color value in the form 0xAARRGGBB.
     **/
    public int color;
    /**
     * 单位dp
     */
    public float widthDp;
    /**
     * startPaddingDp,分割线起始的padding，水平方向左为start，垂直方向上为start
     * endPaddingDp,分割线尾部的padding，水平方向右为end，垂直方向下为end
     */
    public float startPaddingDp;
    public float endPaddingDp;

    public SideLine(boolean isHave, @ColorInt int color, float widthDp, float startPaddingDp, float endPaddingDp) {
        this.isHave = isHave;
        this.color = color;
        this.widthDp = widthDp;
        this.startPaddingDp = startPaddingDp;
        this.endPaddingDp = endPaddingDp;
    }

    public float getStartPaddingDp() {
        return startPaddingDp;
    }


    public float getEndPaddingDp() {
        return endPaddingDp;
    }

    public boolean isHave() {
        return isHave;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getWidthDp() {
        return widthDp;
    }

}
