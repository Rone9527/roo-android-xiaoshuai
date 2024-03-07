package com.jiameng.lock.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.KeyProperties;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aries.ui.view.title.TitleBarView;
import com.jess.arms.di.component.AppComponent;
import com.jiameng.lock.InputType;
import com.jiameng.lock.R;
import com.jiameng.lock.fingerprint.FingerprintHelper;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.ui.widget.DialogLoading;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.handler.HandlerUtil;
import com.roo.router.Router;


public class FingerprintActivity extends I18nActivityArms implements FingerprintHelper.SimpleAuthenticationCallback {
    public static final int REQUEST_CODE_VERIFY_FINGER = 8952;

    public static final int RETURN_TYPE_UN_SUPPORT = 698;//设备不支持指纹登录
    public static final int RETURN_TYPE_OK = 100;//指纹登录通过
    public static final int RETURN_TYPE_SELECT = 101;//切换登录方式

    private static final String KEY_OPEN_TYPE = "KEY_OPEN_TYPE";
    private FingerprintHelper helper;
    private int openType;

    public static void start(Context context, @InputType int openType) {
        Router.newIntent(context)
                .putInt(KEY_OPEN_TYPE, openType)
                .to(FingerprintActivity.class)
                .requestCode(REQUEST_CODE_VERIFY_FINGER)
                .launch();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        TitleBarView titleBarView = ViewHelper.initTitleBar("", this);
        titleBarView.setVisibility(View.INVISIBLE);

        openType = getIntent().getIntExtra(KEY_OPEN_TYPE, InputType.TYPE_LOGIN);

        TextView tvVerifyStart = findViewById(R.id.tvVerifyStart);
        if (openType == InputType.TYPE_LOGIN) {
            tvVerifyStart.setText("进行身份验证解锁登录");
        } else {
            tvVerifyStart.setText("开启指纹解锁登录");
        }

        TextView verifySelect = findViewById(R.id.tvVerifySelect);
        verifySelect.setVisibility(openType == InputType.TYPE_LOGIN ? View.VISIBLE : View.INVISIBLE);
        verifySelect.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(Constants.KEY_DEFAULT, RETURN_TYPE_SELECT);
            setResult(RESULT_OK, intent);
            finish();
        });

        findViewById(R.id.tvVerifyStart).setOnClickListener(v -> openFingerprintLogin());
        findViewById(R.id.ivVerifyStart).setOnClickListener(v -> openFingerprintLogin());

        helper = FingerprintHelper.getInstance();
        helper.init(getApplicationContext());
        helper.setCallback(this);
        if (helper.checkFingerprintAvailable(this) != -1) {//设备支持指纹登录

            DialogLoading.getInstance().showDialog(this);

            HandlerUtil.runOnUiThreadDelay(this::openFingerprintLogin, 1000);

            HandlerUtil.runOnUiThreadDelay(() -> DialogLoading.getInstance().closeDialog(), 2000);

        } else {
            Intent intent = new Intent();
            intent.putExtra(Constants.KEY_DEFAULT, RETURN_TYPE_UN_SUPPORT);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * 开启指纹登录功能
     */
    private void openFingerprintLogin() {
        LogManage.e("openFingerprintLogin");

        helper.generateKey();
        helper.setPurpose(KeyProperties.PURPOSE_ENCRYPT);
        helper.authenticate();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.stopAuthenticate();
    }

    @Override
    public void onAuthenticationSucceeded(String value) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_DEFAULT, RETURN_TYPE_OK);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onAuthenticationFail() {
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fingerprint;
    }

    @Override
    public void onBackPressed() {
        if (openType == InputType.TYPE_LOGIN) {
            return;
        }
        super.onBackPressed();
    }
}
