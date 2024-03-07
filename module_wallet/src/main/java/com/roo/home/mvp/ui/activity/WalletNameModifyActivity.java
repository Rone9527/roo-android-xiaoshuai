package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aries.ui.view.radius.RadiusEditText;
import com.aries.ui.view.radius.RadiusTextView;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.widget.DialogLoading;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.home.R;
import com.roo.home.di.component.DaggerWalletNameModifyComponent;
import com.roo.home.mvp.contract.WalletNameModifyContract;
import com.roo.home.mvp.presenter.WalletNameModifyPresenter;
import com.roo.router.Router;

import java.util.concurrent.TimeUnit;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 修改钱包名称
 */
public class WalletNameModifyActivity extends BaseActivity<WalletNameModifyPresenter> implements WalletNameModifyContract.View {
    public static final int REQUEST_CODE_MODIFY = 5462;
    public static final String WALLET_REMARK = "WALLET_REMARK";
    private String oldRemark;

    public static void start(Context context, String oldRemark) {
        Router.newIntent(context)
                .to(WalletNameModifyActivity.class)
                .putString(WALLET_REMARK, oldRemark)
                .requestCode(REQUEST_CODE_MODIFY)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWalletNameModifyComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wallet_name_modify;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar(getString(R.string.title_change_w_name), this);
        RadiusEditText etWallName = findViewById(R.id.etWallName);
        TextView tvModifyTip = findViewById(R.id.tvModifyTip);
        RadiusTextView tvWalletSave = findViewById(R.id.tvWalletSave);

        oldRemark = getIntent().getStringExtra(WALLET_REMARK);
        etWallName.setText(oldRemark);

        RxTextView.textChanges(etWallName).skipInitialValue()
                .debounce(100, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applySchedulers())
                .map(CharSequence::toString)
                .subscribe(event -> {
                    int length = event.length() * 2 - event.replaceAll("[\u4e00-\u9fa5]", "").length();
                    tvModifyTip.setVisibility(length == 0 || length > 12 ? View.VISIBLE : View.GONE);
                    tvWalletSave.setEnabled(tvModifyTip.getVisibility() == View.GONE);
                });
        RxView.clicks(tvWalletSave).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    String newRemark = etWallName.getText().toString().trim();
                    DialogLoading.getInstance().showDialog(WalletNameModifyActivity.this);
                    if (EthereumWalletUtils.getInstance().setRemarkForWallet(oldRemark, newRemark, this)) {
                        ToastUtils.show(getString(R.string.change_success));
                        DialogLoading.getInstance().closeDialog();
                        Intent data = new Intent();
                        data.putExtra(Constants.KEY_DEFAULT, newRemark);
                        setResult(RESULT_OK, data);
                        finish();
                    } else {
                        DialogLoading.getInstance().closeDialog();
                        ToastUtils.show(R.string.toast_error_wallet_exist);
                    }
                });
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);

    }
}