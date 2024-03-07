package com.roo.core.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.roo.core.utils.LogManage;

public class ScaledEditText extends AppCompatEditText {
    /**
     *
     */
    private Callback callback;

    public ScaledEditText(Context context) {
        this(context, null);
    }

    public ScaledEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaledEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public int getScale() {
        if (callback == null) {
            return 2;
        }
        return callback.decimal();
    }

    private int getScaleInput(float input) {
        int x = String.valueOf(input).indexOf('.') + 1;   //小数点的位置
        int y = String.valueOf(input).length() - x;  //小数的位数
        LogManage.e("[" + input + "]有" + y + "位小数");
        return y;
    }

    public interface Callback {
        int decimal();
    }

}


