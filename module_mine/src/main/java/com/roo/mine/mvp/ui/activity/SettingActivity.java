package com.roo.mine.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

import com.core.domain.mine.LegalCurrencyBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.di.component.AppComponent;
import com.jiameng.lock.InputType;
import com.jiameng.lock.activity.FingerprintActivity;
import com.jiameng.lock.fingerprint.FingerprintHelper;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.utils.Kits;
import com.roo.core.utils.PermissionUtil;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.HashUtil;
import com.roo.core.utils.utils.TickerManager;
import com.roo.mine.R;
import com.roo.router.Router;
import com.roo.view.SwitchButton;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.xyzlf.custom.keyboardlib.SimpleKeyboardListener;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;

public class SettingActivity extends I18nActivityArms {

    private TextView tvLanguage;
    private TextView tvLegalCurrency;
    private CompoundButton.OnCheckedChangeListener swVerifySafePwdListener, swVerifyFingerListener, swJPushListener;
    private SwitchButton swVerifyFinger, swVerifySafePwd, swPushManager;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(SettingActivity.class)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar(getString(R.string.setting), this);
        RxView.clicks(findViewById(R.id.layoutSafePwdModify)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {//修改安全密码
                    KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(this);
                    keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {
                        private String tempPassword;
                        private boolean passVerify;

                        @Override
                        public void onPasswordComplete(String text) {
                            if (passVerify) {
                                if (TextUtils.isEmpty(tempPassword)) {
                                    if (SafePassword.isEquals(text)) {
                                        ToastUtils.show(R.string.toast_same_password);
                                        passVerify = false;
                                        keyboardDialog.dismiss();
                                    } else {
                                        tempPassword = text;
                                        keyboardDialog.getTitle().setText(R.string.toast_input_password_again);
                                        keyboardDialog.clearPassword();
                                    }
                                } else {//修改密码逻辑
                                    if (tempPassword.equals(text)) {
                                        tempPassword = HashUtil.getSHA256StrJava(text);
                                        keyboardDialog.dismiss();
                                        if (!Kits.Empty.check(EthereumWalletUtils.getInstance().getWallet(SettingActivity.this))) {
                                            updateWallets(tempPassword);
                                        }
                                        SafePassword.update(tempPassword);
                                        ToastUtils.show(getString(R.string.change_success));
                                    } else {
                                        tempPassword = "";
                                        keyboardDialog.getTitle().setText(R.string.toast_input_password_again);
                                        keyboardDialog.clearPassword();
                                        ToastUtils.show(getText(R.string.toast_password_same));
                                    }
                                    keyboardDialog.dismiss();
                                }
                            } else {//验证是否输入了正确的原密码
                                if (SafePassword.isEquals(text)) {
                                    passVerify = true;
                                    keyboardDialog.getTipsLayout().setVisibility(View.VISIBLE);
                                    keyboardDialog.clearPassword();
                                    keyboardDialog.getTitle().setText(R.string.toast_input_new_password);
                                } else {
                                    keyboardDialog.clearPassword();
                                    ToastUtils.show(getString(R.string.false_safe_pass));
                                }
                            }
                        }
                    });
                    keyboardDialog.show();
                    keyboardDialog.getTipsLayout().setVisibility(View.GONE);
                    keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_input));
                });

        swVerifySafePwd = findViewById(R.id.swVerifySafePwd);
        swVerifySafePwd.setCheckedImmediately(SharedPref.getBoolean(Constants.KEY_OPEN_SAFE_PASSWORD_VERIFY));

        swVerifySafePwdListener = (buttonView, isChecked) -> {
            swVerifySafePwd.setOnCheckedChangeListener(null);
            swVerifySafePwd.setCheckedImmediately(!isChecked);
            swVerifySafePwd.setOnCheckedChangeListener(swVerifySafePwdListener);

            //使用安全密码开启
            KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(this);
            keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {

                @Override
                public void onPasswordComplete(String text) {
                    if (SafePassword.isEquals(text)) {

                        if (SharedPref.getBoolean(Constants.KEY_OPEN_SAFE_PASSWORD_VERIFY)) {
                            SharedPref.remove(Constants.KEY_OPEN_SAFE_PASSWORD_VERIFY);
                        } else {
                            SharedPref.putBoolean(Constants.KEY_OPEN_SAFE_PASSWORD_VERIFY, true);

                            if (SharedPref.getBoolean(Constants.KEY_OPEN_SAFE_FINGER_VERIFY)) {
                                swVerifyFinger.setOnCheckedChangeListener(null);
                                SharedPref.remove(Constants.KEY_OPEN_SAFE_FINGER_VERIFY);
                                swVerifyFinger.setChecked(false);
                                swVerifyFinger.setOnCheckedChangeListener(swVerifyFingerListener);
                            }
                        }
                        swVerifySafePwd.setOnCheckedChangeListener(null);
                        swVerifySafePwd.setCheckedImmediately(SharedPref.getBoolean(Constants.KEY_OPEN_SAFE_PASSWORD_VERIFY));
                        swVerifySafePwd.setOnCheckedChangeListener(swVerifySafePwdListener);
                        keyboardDialog.dismiss();
                    } else {
                        keyboardDialog.clearPassword();
                        ToastUtils.show(getString(R.string.false_safe_pass));
                    }
                }
            });
            keyboardDialog.show();
            keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_input));
            keyboardDialog.getTipsLayout().setVisibility(View.GONE);

        };
        swVerifySafePwd.setOnCheckedChangeListener(swVerifySafePwdListener);

        RxView.clicks(swVerifySafePwd).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                });
        swVerifyFinger = findViewById(R.id.swVerifyFinger);
        swVerifyFinger.setCheckedImmediately(SharedPref.getBoolean(Constants.KEY_OPEN_SAFE_FINGER_VERIFY));
        FingerprintHelper helper = FingerprintHelper.getInstance();
        helper.init(getApplicationContext());
        findViewById(R.id.layoutVerifyFinger).setVisibility(
                helper.checkFingerprintAvailable(this) == -1 ? View.GONE : View.VISIBLE);

        swVerifyFingerListener = (buttonView, isChecked) -> {
            swVerifyFinger.setOnCheckedChangeListener(null);
            swVerifyFinger.setChecked(!isChecked);
            swVerifyFinger.setOnCheckedChangeListener(swVerifyFingerListener);


            //使用指纹或人脸识别开启
            KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(this);
            keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {

                @Override
                public void onPasswordComplete(String text) {
                    if (SafePassword.isEquals(text)) {
                        if (SharedPref.getBoolean(Constants.KEY_OPEN_SAFE_FINGER_VERIFY)) {
                            SharedPref.remove(Constants.KEY_OPEN_SAFE_FINGER_VERIFY);
                            swVerifyFinger.setOnCheckedChangeListener(null);
                            swVerifyFinger.setChecked(false);
                            swVerifyFinger.setOnCheckedChangeListener(swVerifyFingerListener);
                        } else {
                            FingerprintActivity.start(SettingActivity.this, InputType.TYPE_OPEN);
                        }
                        keyboardDialog.dismiss();
                    } else {
                        keyboardDialog.clearPassword();
                        ToastUtils.show(getString(R.string.false_safe_pass));
                    }
                }
            });
            keyboardDialog.show();
            keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_input));
            keyboardDialog.getTipsLayout().setVisibility(View.GONE);
        };
        swVerifyFinger.setOnCheckedChangeListener(swVerifyFingerListener);

        swPushManager = findViewById(R.id.swPushManager);
        swPushManager.setCheckedImmediately(SharedPref.getBoolean(Constants.IS_OPEN_PUSH));

        swJPushListener = (buttonView, isChecked) -> {
            //检测有没有开启通知权限
            if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                swPushManager.setOnCheckedChangeListener(null);
                if (isChecked) {
                    JPushInterface.resumePush(this);
                } else {
                    JPushInterface.stopPush(this);
                }
                SharedPref.putBoolean(Constants.IS_OPEN_PUSH, isChecked);
                swPushManager.setOnCheckedChangeListener(swJPushListener);
            } else {
                if (isChecked) {
                    //去手动开启通知权限
                    PermissionUtil.openNotifyPermissionManual(this);
                    swPushManager.setChecked(!isChecked);
                }
            }
        };
        swPushManager.setOnCheckedChangeListener(swJPushListener);

        tvLegalCurrency = findViewById(R.id.tvLegalCurrency);
        RxView.clicks(findViewById(R.id.layoutLegalCurrency)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {//计价货币
                    LegalCurrencyActivity.start(this);
                });
        tvLanguage = findViewById(R.id.tvLanguage);
        RxView.clicks(findViewById(R.id.layoutLanguage)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {//语言
                    LanguageActivity.start(this);
                });

        LegalCurrencyBean selected = TickerManager.getInstance().getLegal();
        tvLegalCurrency.setText(selected.getSymbol());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (requestCode == FingerprintActivity.REQUEST_CODE_VERIFY_FINGER) {
                swVerifyFinger.setOnCheckedChangeListener(null);
                int result = data.getIntExtra(Constants.KEY_DEFAULT, 0);
                switch (result) {
                    case FingerprintActivity.RETURN_TYPE_UN_SUPPORT://设备不支持指纹登录
                        swVerifyFinger.setChecked(false);
                        break;
                    case FingerprintActivity.RETURN_TYPE_OK://指纹登录通过
                        SharedPref.putBoolean(Constants.KEY_OPEN_SAFE_FINGER_VERIFY, true);
                        swVerifyFinger.setChecked(true);

                        swVerifySafePwd.setOnCheckedChangeListener(null);
                        SharedPref.remove(Constants.KEY_OPEN_SAFE_PASSWORD_VERIFY);
                        swVerifySafePwd.setChecked(false);
                        swVerifySafePwd.setOnCheckedChangeListener(swVerifySafePwdListener);

                        break;
                    default:
                        break;
                }
                swVerifyFinger.setOnCheckedChangeListener(swVerifyFingerListener);
            } else if (requestCode == LegalCurrencyActivity.REQUEST_CODE_CURRENCY) {
                LegalCurrencyBean selected = TickerManager.getInstance().getLegal();
                tvLegalCurrency.setText(selected.getSymbol());
            }
        }
    }

    private void updateWallets(String tempPassword) {
        try {
            for (UserWallet userWallet : EthereumWalletUtils.getInstance().getWallet(this)) {
                for (UserWallet.ChainWallet chainWallet : userWallet.getChainWallets()) {
                    WalletFile walletFile = chainWallet.getWalletFile();
                    ECKeyPair ecKeyPair = EthereumWalletUtils.getInstance().decrypt(SafePassword.get(), walletFile);
                    WalletFile newWalletFile = Wallet.createLight(tempPassword, ecKeyPair);
                    chainWallet.setWalletFile(newWalletFile);
                }
                EthereumWalletUtils.getInstance().updateWallet(this, userWallet, false);
            }
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }
}
