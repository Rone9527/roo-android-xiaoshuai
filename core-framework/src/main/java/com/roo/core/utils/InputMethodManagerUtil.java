package com.roo.core.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by TZT on 2016/11/8.
 */

public class InputMethodManagerUtil {
    public static void closeInputDialog(Activity a) {
        InputMethodManager mInputKeyBoard = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (a.getCurrentFocus() != null) {
            mInputKeyBoard.hideSoftInputFromWindow(a.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            a.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

    public static void showInputMethod(Activity a) {
        InputMethodManager inputManager = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static void showKeyboard(Activity activity, boolean isShow) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = activity.getCurrentFocus();
        if (null == imm)
            return;
        if (isShow) {
            if (currentFocus != null) {
                imm.showSoftInput(currentFocus, 0);
            } else {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        } else {
            if (currentFocus != null) {
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } else {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        }
    }

    public static void showKeyboard(View currentFocus, boolean isShow) {
        InputMethodManager imm = (InputMethodManager) currentFocus.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null == imm)
            return;
        if (isShow) {
            imm.showSoftInput(currentFocus, 0);
        } else {
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
