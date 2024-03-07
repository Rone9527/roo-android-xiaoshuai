package com.jiameng.verifybutton;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;


/**
 * author: zheng
 * created on: 2017/3/2 17:58
 * description:获取验证码按钮
 * version: 1.0
 */

public class VerifyCodeButton extends AppCompatTextView {
    public static final String SP_NAME = "config";
    private static final long ONE_MINUTE = 60_000;
    private long lastGetVerifiCodeTime;
    private CustomCountDownTimer timer;
    private int textColorTimer;//字体颜色->倒计时
    private int textColorSend;//字体颜色->重新获取

    public VerifyCodeButton(Context context) {
        this(context, null);
    }

    public VerifyCodeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        textColorTimer = ContextCompat.getColor(getContext(), R.color.color_verify_resend);
        textColorSend = ContextCompat.getColor(getContext(), R.color.color_verify_send);
    }

    public int getColorAccent(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 当调用此方法时,该控件会初始化状态
     */
    public void setKeyAndInitState(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("invalid key");
        }

        //从本地获取上次获取验证码时间
        lastGetVerifiCodeTime = getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getLong(key, 0L);
        //针对还没有倒计时完成就退出了该页面的情况
        long duration = SystemClock.elapsedRealtime() - lastGetVerifiCodeTime;
        long surplus = ONE_MINUTE - duration;
        if (lastGetVerifiCodeTime != 0 && surplus > 0 && surplus < ONE_MINUTE) {
            setEnabled(false);
            createTimer(surplus);
        } else {
            lastGetVerifiCodeTime = 0;
        }
    }

    public void removeKeyAndInitState(String key) {
        getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().remove(key).apply();
    }

    /**
     * 当输入条件正确时,调用该方法,记录最后一次获取验证码的时间
     *
     * @param key 用于区分获取验证码的情况的key
     */
    public void recordLastGetVerifiCodeTime(String key) {
        setEnabled(false);
        lastGetVerifiCodeTime = SystemClock.elapsedRealtime();
        getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().putLong(key, lastGetVerifiCodeTime).apply();
        createTimer(ONE_MINUTE);
    }

    /**
     * 创建 一个计时器
     *
     * @param countdownTime 倒数总毫秒数
     */
    private void createTimer(long countdownTime) {
        timer = new CustomCountDownTimer(countdownTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setText(TextUtils.concat(String.valueOf(millisUntilFinished / 1000), "s"));
                setTextColor(textColorTimer);
            }

            @Override
            public void onFinish() {
                setEnabled(true);
                setText(R.string.reacquire);
                setTextColor(textColorSend);
                lastGetVerifiCodeTime = 0;
                timer = null;
            }
        };
        timer.start();
    }

    public void setTextColorTimer(@ColorInt int colorRes) {
        this.textColorTimer = colorRes;
    }

    public void setTextColorSend(@ColorInt int colorRes) {
        this.textColorSend = colorRes;
    }

    /**
     * 销毁计时控件
     */
    public void onDestroy() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }
}
