package com.github.mysnackbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class MaskLayout extends LinearLayout {

    private OnLayoutChangeListener mOnLayoutChangeListener;

    public MaskLayout(Context context) {
        this(context, null);
    }

    public MaskLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(false);
        LayoutInflater.from(context).inflate(R.layout.view_mask_layout_include, this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && mOnLayoutChangeListener != null) {
            mOnLayoutChangeListener.onLayoutChange(this, l, t, r, b);
        }
    }

    void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
        mOnLayoutChangeListener = onLayoutChangeListener;
    }

    interface OnLayoutChangeListener {
        void onLayoutChange(View view, int left, int top, int right, int bottom);
    }
}