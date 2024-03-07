package com.github.mysnackbar;

public enum MaskConf {
    /**
     * 黑色
     */
    DARK(R.color.mask_dark),

    /**
     * 浅黑
     */
    DARK_SOFT(R.color.mask_dark_soft),

    /**
     * 深黑
     */
    DARK_DEEP(R.color.mask_dark_deep);

    private int backgroundColor;

    MaskConf(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
