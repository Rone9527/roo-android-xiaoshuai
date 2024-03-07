package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.component.Main;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.home.R;
import com.roo.home.di.component.DaggerBackupWordsComponent;
import com.roo.home.mvp.contract.BackupWordsContract;
import com.roo.home.mvp.presenter.BackupWordsPresenter;
import com.roo.home.mvp.ui.adapter.BackupWordsAdapter;
import com.roo.home.mvp.ui.dialog.ScreenShotTipsDialog;
import com.roo.router.Router;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 备份助记词
 */
public class BackupWordsActivity extends BaseActivity<BackupWordsPresenter> implements BackupWordsContract.View {

    public static final int REQUEST_CODE_CREATE = 6698;

    @Inject
    BackupWordsAdapter mAdapter;

    public static void start(Context context, String remark, boolean openMain) {
        Router.newIntent(context)
                .to(BackupWordsActivity.class)
                .putString(Constants.WALLET_REMARK, remark)
                .putBoolean(Constants.KEY_DEFAULT, openMain)
                .requestCode(REQUEST_CODE_CREATE)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBackupWordsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_backup_words;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String remark = getIntent().getStringExtra(Constants.WALLET_REMARK);
        boolean openMain = getIntent().getBooleanExtra(Constants.KEY_DEFAULT, false);
        TitleBarView titleBarView = ViewHelper.initTitleBar(getString(R.string.title_back_up_mnemonics), this);
        TextView textMain = titleBarView.getTextView(Gravity.CENTER | Gravity.TOP);
        textMain.getPaint().setFakeBoldText(true);

        titleBarView.getLinearLayout(Gravity.LEFT).removeAllViews();


        UserWallet userWallet = EthereumWalletUtils.getInstance()
                .getWalletByRemark(this, remark);

        RxView.clicks(findViewById(R.id.tvCopy)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    GlobalUtils.copyToClipboard(userWallet.getMnemonics(), this);
                    ToastUtils.show(getString(R.string.mnemonic) + getString(R.string.copy_success));
                });
        RxView.clicks(findViewById(R.id.tvConfirmBackUp)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    BackupWordsVerifyActivity.start(this, userWallet.getRemark(), openMain);
                });
        RxView.clicks(findViewById(R.id.tvConfirmBackUpWait)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (openMain) {
                        CC.obtainBuilder(Main.NAME)
                                .setContext(this)
                                .setActionName(Main.Action.MainActivity)
                                .build().call();
//                        ToastUtils.show(R.string.create_wallet_success);
                    }
                    setResult(RESULT_OK);
                    finish();
                });

        ViewHelper.initRecyclerView(mAdapter, this, new GridLayoutManager(this, 3));
        mAdapter.setNewData(Arrays.asList(userWallet.getMnemonics().split(" ")));

        ScreenShotTipsDialog.newInstance().show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BackupWordsVerifyActivity.REQUEST_CODE_SUCCESS) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void onBackPressed() {
    }
}