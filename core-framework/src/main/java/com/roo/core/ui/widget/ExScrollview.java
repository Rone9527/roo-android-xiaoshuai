
package com.roo.core.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * 屏蔽滑动事件
 */
public class ExScrollview extends ScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;
    private boolean forbidScroll;

    public ExScrollview(Context context) {
        this(context, null);
    }

    public ExScrollview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setForbidScroll(boolean forbidScroll) {
        this.forbidScroll = forbidScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (forbidScroll) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = (int) e.getRawX();
                    downY = (int) e.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveY = (int) e.getRawY();
                    if (Math.abs(moveY - downY) > mTouchSlop) {
                        return true;
                    }
            }
        }
        return super.onInterceptTouchEvent(e);
    }
}
