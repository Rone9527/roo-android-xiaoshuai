package com.roo.core.utils;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * <pre>
 *     project name: xiaoorange_android
 *     author      : 李琼
 *     create time : 2018/7/27 下午4:38
 *     desc        : 描述--//TouchOutsideHelper 点击非输入框处关闭键盘
 *     Reference   :
 *     modifier               :
 *     modification time      :
 *     modify remarks         :
 *     @version: --//1.0
 * </pre>
 */

public class TouchOutsideHelper {

    /**
     * 清除editText的焦点
     *
     * @param v     焦点所在View
     * @param views 输入框
     */
    public void clearViewFocus(View v, View... views) {
        if (null != v && null != views && views.length > 0) {
            for (View view : views) {
                if (v.getId() == view.getId()) {
                    v.clearFocus();
                    break;
                }
            }
        }
    }

    /**
     * 隐藏键盘
     *
     * @param v     焦点所在View
     * @param views 输入框
     * @return true代表焦点在edit上
     */
    public boolean isFocusEditText(View v, View... views) {
        if (v instanceof EditText) {
            EditText tmpEt = (EditText) v;
            for (View view : views) {
                if (tmpEt.getId() == view.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param views 传入要过滤的View,过滤之后点击将不会有隐藏软键盘的操作
     *              是否触摸在指定view上面,对某个控件过滤
     */
    public boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) {
            return false;
        }
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

}
