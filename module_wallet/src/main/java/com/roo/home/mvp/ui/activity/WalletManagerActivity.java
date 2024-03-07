package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.di.component.AppComponent;
import com.roo.core.app.annotation.CreateType;
import com.roo.core.app.constants.Constants;
import com.roo.core.daoManagers.NewAssetsDaoManager;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.ui.widget.DialogLoading;
import com.roo.core.utils.Kits;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.handler.HandlerUtil;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.home.R;
import com.roo.home.mvp.ui.dialog.ChooseLinkDialog;
import com.roo.home.mvp.ui.dialog.WalletRemoveDialog;
import com.roo.router.Router;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.xyzlf.custom.keyboardlib.SimpleKeyboardListener;

import java.util.concurrent.TimeUnit;

/**
 * 管理钱包--钱包设置
 */
public class WalletManagerActivity extends I18nActivityArms {

    private UserWallet userWallet;
    private TextView tvWalletName;

    public static void start(Context context, String remark) {
        Router.newIntent(context)
                .to(WalletManagerActivity.class)
                .putString(Constants.KEY_DEFAULT, remark)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wallet_manager;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        WalletManagerActivity context = WalletManagerActivity.this;
        ViewHelper.initTitleBar(getString(R.string.my_wallet), this);
        tvWalletName = findViewById(R.id.tvWalletName);

        String remark = getIntent().getStringExtra(Constants.KEY_DEFAULT);
        userWallet = EthereumWalletUtils.getInstance().getWalletByRemark(this, remark);
        if (userWallet.getCreateType() == CreateType.PRIVATE) {
            findViewById(R.id.layoutBackup).setVisibility(View.GONE);
        }
        tvWalletName.setText(userWallet.getRemark());


        RxView.clicks(findViewById(R.id.layoutBackup))
                .throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(this);
            keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {

                @Override
                public void onPasswordComplete(String text) {
                    if (SafePassword.isEquals(text)) {
                        BackupWordsActivity.start(context, userWallet.getRemark(), false);
                        keyboardDialog.dismiss();
                    } else {
                        ToastUtils.show(getString(R.string.false_safe_pass));
                        keyboardDialog.clearPassword();
                    }
                }
            });
            keyboardDialog.show();
            keyboardDialog.getTipsLayout().setVisibility(View.GONE);

            keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_input));
        });
        RxView.clicks(findViewById(R.id.layoutWalletName))
                .throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            WalletNameModifyActivity.start(this, userWallet.getRemark());
        });
        RxView.clicks(findViewById(R.id.layoutExport))
                .throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(this);
            keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {
                @Override
                public void onPasswordComplete(String text) {
                    if (SafePassword.isEquals(text)) {

                        ChooseLinkDialog.newInstance().setOnClickListener((item) -> {
                            String chainCode = item.getCode();
                            if (userWallet.getListMainChainCode().contains(chainCode)) {
                                WalletExportPKActivity.start(context, userWallet.getRemark(), chainCode);
                            } else {
                                ToastUtils.show(getString(R.string.toast_null_chain_wallet));
                            }
                        }).show(getSupportFragmentManager(), getClass().getSimpleName());

                        keyboardDialog.dismiss();
                    } else {
                        ToastUtils.show(getString(R.string.false_safe_pass));
                        keyboardDialog.clearPassword();
                    }
                }
            });
            keyboardDialog.show();
            keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_input));
            keyboardDialog.getTipsLayout().setVisibility(View.GONE);
        });

        RxView.clicks(findViewById(R.id.layoutRemove)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    WalletRemoveDialog.newInstance().setOnClickedListener(() -> {
                        HandlerUtil.runOnUiThreadDelay(() -> {
                            KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(this);
                            keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {

                                @Override
                                public void onPasswordComplete(String text) {
                                    if (SafePassword.isEquals(text)) {
                                        keyboardDialog.dismiss();
                                        DialogLoading.getInstance().showDialog(context);
                                        EthereumWalletUtils.getInstance().deleteWalletByRemark(context, userWallet.getRemark());
                                        NewAssetsDaoManager.delete(userWallet.getRemark());
                                        DialogLoading.getInstance().closeDialog();
                                        ToastUtils.show(R.string.toast_wallet_delete_success);

                                        if (Kits.Empty.check(EthereumWalletUtils.getInstance().getWallet(context))) {
                                            WalletCreateNormalActivity.start(context, true);
                                        }

                                        finish();
                                    } else {
                                        ToastUtils.show(getString(R.string.false_safe_pass));
                                        keyboardDialog.clearPassword();
                                    }
                                }
                            });
                            keyboardDialog.show();
                            keyboardDialog.getTipsLayout().setVisibility(View.GONE);
                            keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_input));
                        }, 200);
                    }).show(getSupportFragmentManager(), getClass().getSimpleName());
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (requestCode == WalletNameModifyActivity.REQUEST_CODE_MODIFY) {
                String newRemark = data.getStringExtra(Constants.KEY_DEFAULT);
                userWallet = EthereumWalletUtils.getInstance()
                        .getWalletByRemark(this, newRemark);
                tvWalletName.setText(userWallet.getRemark());
            }
        }
    }
}