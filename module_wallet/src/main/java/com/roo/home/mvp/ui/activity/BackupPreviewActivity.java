package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.billy.cc.core.component.CC;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.di.component.AppComponent;
import com.roo.core.app.component.Main;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.home.R;
import com.roo.router.Router;

import java.util.concurrent.TimeUnit;

/**
 * 备份助记词
 */
public class BackupPreviewActivity extends I18nActivityArms {

    public static void start(Context context, String remark) {
        Router.newIntent(context)
                .to(BackupPreviewActivity.class)
                .putString(Constants.WALLET_REMARK, remark)
                .requestCode(BackupWordsActivity.REQUEST_CODE_CREATE)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_backup_words_preview;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String remark = getIntent().getStringExtra(Constants.WALLET_REMARK);

        RxView.clicks(findViewById(R.id.tvConfirmBackUpWait)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    CC.obtainBuilder(Main.NAME)
                            .setContext(this)
                            .setActionName(Main.Action.MainActivity)
                            .build().call();
                    setResult(RESULT_OK);
                    finish();
                });

        RxView.clicks(findViewById(R.id.tvConfirmBackUp)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    BackupWordsActivity.start(this, remark, true);
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (requestCode == BackupWordsActivity.REQUEST_CODE_CREATE) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
    }

}