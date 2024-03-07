package com.roo.wallet.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ToastUtils;
import com.roo.router.Router;
import com.roo.wallet.R;
import com.xyzlf.custom.keyboardlib.KeyboardGridAdapter;
import com.xyzlf.custom.keyboardlib.PasswordView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SafeKeyboardVerifyActivity extends I18nActivityArms {
    public static final int REQUEST_CODE_SAFE_KEYBOARD_VERIFY = 1569;
    private PasswordView mPasswordView;
    private KeyboardGridAdapter mKeyboardAdapter;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(SafeKeyboardVerifyActivity.class)
                .requestCode(REQUEST_CODE_SAFE_KEYBOARD_VERIFY)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return R.layout.activity_safe_keyboard_verify;
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mPasswordView = findViewById(R.id.dialog_password);
        mPasswordView.setOnPasswordListener(new PasswordView.OnPasswordListener() {
            @Override
            public void onPasswordChange(String text) {
            }

            @Override
            public void onPasswordComplete(String text) {
                if (SafePassword.isEquals(text)) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    mPasswordView.clearPassword();
                    ToastUtils.show(getString(R.string.false_safe_pass));
                }
            }
        });
        GridView keyboardGrid = findViewById(R.id.dialog_grid);

        List<Integer> dataSet = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataSet.add(i);
        }
        Collections.shuffle(dataSet);
        dataSet.add(9, KeyboardGridAdapter.TYPE_VALUE_BANK);
        dataSet.add(KeyboardGridAdapter.TYPE_VALUE_DELETE);
        mKeyboardAdapter = new KeyboardGridAdapter(dataSet);
        keyboardGrid.setAdapter(mKeyboardAdapter);
        keyboardGrid.setOnItemClickListener((parent, view, position, id) -> {
            if (null == mKeyboardAdapter) {
                return;
            }
            if (position == (mKeyboardAdapter.getCount() - 3)) {
                return;
            }
            if (position == (mKeyboardAdapter.getCount() - 1)) {
                mPasswordView.deletePassword();
            } else {
                int value = mKeyboardAdapter.getItem(position);
                mPasswordView.addPassword(value);
            }
        });
        keyboardGrid.setOnItemLongClickListener((parent, view, position, id) -> {
            if (position == mKeyboardAdapter.getCount() - 1) {
                mPasswordView.clearPassword();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {

    }
}
