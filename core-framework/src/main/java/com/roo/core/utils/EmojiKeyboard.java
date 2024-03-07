package com.roo.core.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jess.arms.utils.DeviceUtils;

import java.util.ArrayList;

/**
 * 作者： chenZY
 * 时间： 2017/8/26 18:12
 * 描述：
 */
@SuppressLint("ClickableViewAccessibility")
public class EmojiKeyboard {
    private static final String EMOJI_KEYBOARD = "EmojiKeyboard";
    private static final String KEY_SOFT_KEYBOARD_HEIGHT = "SoftKeyboardHeight";
    private static final int SOFT_KEYBOARD_HEIGHT_DEFAULT = 654;
    private Activity mActivity;
    //文本输入框
    private EditText mEditText;
    //表情面板
    private View mPanelView;
    //内容View,即除了表情布局和输入框布局以外的布局 =>用于固定输入框一行的高度以防止跳闪
    private View mContentView;
    private InputMethodManager inputMethodManager;
    private SharedPreferences sharedPreferences;
    private Handler mHandler;
    private OnPanelVisibilityChangeListener listener;

    public EmojiKeyboard(Activity activity, EditText editText, View panelView,
                         ArrayList<View> panelSwitchViews, View contentView, boolean onTouchHide) {
        mActivity = activity;
        mEditText = editText;
        mPanelView = panelView;
        mContentView = contentView;
        mEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && mPanelView.isShown()) {
                lockContentViewHeight();
                hidePanel(true);
                unlockContentViewHeight();
            }
            return false;
        });
        //触摸ContentView关闭输入框
        if (onTouchHide) {
            mContentView.setOnTouchListener((view, motionEvent) -> {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (mPanelView.isShown()) {
                        hidePanel(false);
                    } else if (isSoftKeyboardShown()) {
                        hideSoftKeyboard();
                    }
                }
                return false;
            });
        }
        //用于弹出表情面板的View
        for (View switchView : panelSwitchViews) {
            switchView.setOnClickListener(v -> {
                if (mPanelView.isShown()) {
                    lockContentViewHeight();
                    if (listener != null) {
                        listener.onPanelShow(v);
                    }
                    unlockContentViewHeight();
                } else {
                    if (isSoftKeyboardShown()) {
                        lockContentViewHeight();
                        showPanel(switchView);
                        unlockContentViewHeight();
                    } else {
                        showPanel(switchView);
                    }
                }
            });
        }
        inputMethodManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        sharedPreferences = mActivity.getSharedPreferences(EMOJI_KEYBOARD, Context.MODE_PRIVATE);
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mHandler = new Handler();
        init();
    }

    /**
     * 如果之前没有保存过键盘高度值
     * 则在进入Activity时自动打开键盘，并把高度值保存下来
     */
    private void init() {
        if (!sharedPreferences.contains(KEY_SOFT_KEYBOARD_HEIGHT)) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showSoftKeyboard(true);
                }
            }, 200);
        }
    }

    /**
     * 当点击返回键时需要先隐藏表情面板
     */
    public boolean interceptBackPress() {
        if (mPanelView.isShown()) {
            hidePanel(false);
            return true;
        }
        return false;
    }

    /**
     * 锁定内容View以防止跳闪
     */
    private void lockContentViewHeight() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        layoutParams.height = mContentView.getHeight();
        layoutParams.weight = 0;
    }

    /**
     * 释放锁定的内容View
     */
    private void unlockContentViewHeight() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) mContentView.getLayoutParams()).weight = 1;
            }
        }, 200);
    }

    /**
     * 获取键盘的高度
     */
    private int getSoftKeyboardHeight() {
        Rect rect = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //屏幕当前可见高度，不包括状态栏
        int displayHeight = rect.bottom - rect.top;
        //屏幕可用高度
        int availableHeight = Kits.Dimens.getScreenHeightAboveVirtualKeyboard(mActivity);
        //用于计算键盘高度
        int softInputHeight = availableHeight - displayHeight - DeviceUtils.getStatusBarHeight(mActivity);
        Log.e("TAG-di", displayHeight + "");
        Log.e("TAG-av", availableHeight + "");
        Log.e("TAG-so", softInputHeight + "");
        if (softInputHeight != 0) {
            // 因为考虑到用户可能会主动调整键盘高度，所以只能是每次获取到键盘高度时都将其存储起来
            sharedPreferences.edit().putInt(KEY_SOFT_KEYBOARD_HEIGHT, softInputHeight).apply();
        }
        return softInputHeight;
    }

    /**
     * 获取本地存储的键盘高度值或者是返回默认值
     */
    private int getSoftKeyboardHeightLocalValue() {
        return sharedPreferences.getInt(KEY_SOFT_KEYBOARD_HEIGHT, SOFT_KEYBOARD_HEIGHT_DEFAULT);
    }

    /**
     * 判断是否显示了键盘
     */
    private boolean isSoftKeyboardShown() {
        return getSoftKeyboardHeight() != 0;
    }

    /**
     * 令编辑框获取焦点并显示键盘
     */
    private void showSoftKeyboard(boolean saveSoftKeyboardHeight) {
        mEditText.requestFocus();
        inputMethodManager.showSoftInput(mEditText, 0);
        if (saveSoftKeyboardHeight) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSoftKeyboardHeight();
                }
            }, 200);
        }
    }

    /**
     * 隐藏键盘
     */
    private void hideSoftKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 显示表情面板
     *
     * @param panelSwitchView
     */
    private void showPanel(View panelSwitchView) {
        int softKeyboardHeight = getSoftKeyboardHeight();
        if (softKeyboardHeight == 0) {
            softKeyboardHeight = getSoftKeyboardHeightLocalValue();
        } else {
            hideSoftKeyboard();
        }
        mPanelView.getLayoutParams().height = softKeyboardHeight;
        mPanelView.setVisibility(View.VISIBLE);
        if (listener != null) {
            listener.onPanelShow(panelSwitchView);
        }
    }

    /**
     * 隐藏表情面板，同时指定是否随后开启键盘
     */
    public void hidePanel(boolean showSoftKeyboard) {
        if (mPanelView.isShown()) {
            mPanelView.setVisibility(View.GONE);
            if (showSoftKeyboard) {
                showSoftKeyboard(false);
            }
            if (listener != null) {
                listener.onPanelHide();
            }
        }
    }

    public void setPanelVisibilityChangeListener(OnPanelVisibilityChangeListener listener) {
        this.listener = listener;
    }

    public interface OnPanelVisibilityChangeListener {

        void onPanelShow(View panelSwitchView);

        void onPanelHide();
    }

}
