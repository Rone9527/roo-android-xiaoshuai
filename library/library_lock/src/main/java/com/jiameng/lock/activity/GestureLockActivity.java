package com.jiameng.lock.activity;

import android.content.Context;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.jiameng.lock.InputType;
import com.jiameng.lock.R;
import com.jiameng.lock.gesture.GxPainter;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.utils.ViewHelper;
import com.roo.router.Router;
import com.wangnan.library.GestureLockView;
import com.wangnan.library.listener.OnGestureLockListener;


/**
 * 手势解锁
 */
public class GestureLockActivity extends I18nActivityArms implements OnGestureLockListener {

    private static final String KEY_OPEN_TYPE = "KEY_OPEN_TYPE";

    public static void start(Context context, int requestCode, @InputType int openType) {
        Router.newIntent(context)
                .putInt(KEY_OPEN_TYPE, openType)
                .requestCode(requestCode)
                .to(GestureLockActivity.class)
                .launch();
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar("", this);
        int openType = getIntent().getIntExtra(KEY_OPEN_TYPE, InputType.TYPE_LOGIN);

        GestureLockView mGestureLockView = findViewById(R.id.gesture_lock_view);
        mGestureLockView.setPainter(new GxPainter());
        mGestureLockView.setGestureLockListener(this);
        //mGestureLockView.showErrorStatus(1000);
        //mGestureLockView.clearView();
    }


    /**
     * 解锁完成
     */
    @Override
    public void onComplete(String result) {
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting_gesture;
    }


    /**
     * 解锁开始
     */
    @Override
    public void onStarted() {
    }

    /**
     * 解锁密码改变
     */
    @Override
    public void onProgress(String progress) {
    }

}


