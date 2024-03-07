package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.aries.ui.view.radius.RadiusTextView;
import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.component.Main;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.home.R;
import com.roo.home.di.component.DaggerBackupWordsVerifyComponent;
import com.roo.home.mvp.contract.BackupWordsVerifyContract;
import com.roo.home.mvp.model.bean.BackupWord;
import com.roo.home.mvp.presenter.BackupWordsVerifyPresenter;
import com.roo.home.mvp.ui.adapter.BackupWordsVerifyAdapter;
import com.roo.router.Router;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 确认助记词
 */
public class BackupWordsVerifyActivity extends BaseActivity<BackupWordsVerifyPresenter> implements BackupWordsVerifyContract.View {
    public static final int REQUEST_CODE_SUCCESS = 9546;
    @Inject
    BackupWordsVerifyAdapter mAdapter;
    private boolean openMain;

    public static void start(Context context, String remark, boolean openMain) {
        Router.newIntent(context)
                .to(BackupWordsVerifyActivity.class)
                .putString(Constants.WALLET_REMARK, remark)
                .putBoolean(Constants.KEY_DEFAULT, openMain)
                .requestCode(REQUEST_CODE_SUCCESS)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBackupWordsVerifyComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_backup_words_verify;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String remark = getIntent().getStringExtra(Constants.WALLET_REMARK);
        openMain = getIntent().getBooleanExtra(Constants.KEY_DEFAULT, false);
        TitleBarView titleBarView = ViewHelper.initTitleBar(getString(R.string.title_verify_mnemonics), this);
        TextView textMain = titleBarView.getTextView(Gravity.CENTER | Gravity.TOP);
        textMain.getPaint().setFakeBoldText(true);

        UserWallet userWallet = EthereumWalletUtils.getInstance().getWalletByRemark(this, remark);
        String[] split = userWallet.getMnemonics().split(" ");

        ArrayList<BackupWord> dataSet = new ArrayList<>();
        for (int i = 0, splitLength = split.length; i < splitLength; i++) {
            String value = split[i];
            dataSet.add(new BackupWord(value, false, i + 1));
        }
        BackupWord[] random = initRandom(dataSet);
        Collections.shuffle(dataSet);

        TextView tvVerifyText = findViewById(R.id.tvVerifyText);
        tvVerifyText.setText(MessageFormat.format(getString(R.string.tip_verify_mnemonic_a),
                random[0].getIndex(), random[1].getIndex()));
        tvVerifyText.getPaint().setFakeBoldText(true);

        RadiusTextView tvConfirm = findViewById(R.id.tvConfirm);
        RxView.clicks(tvConfirm).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (doVerify(random)) {
                        userWallet.setBackUpped(true);
                        EthereumWalletUtils.getInstance().updateWallet(this, userWallet, false);
                        CC.obtainBuilder(Main.NAME)
                                .setContext(this)
                                .setActionName(Main.Action.MainActivity)
                                .build().call();
                        ToastUtils.show(R.string.verify_true);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        ToastUtils.show(R.string.toast_phrase_error);
                    }
                });

        ViewHelper.initRecyclerView(mAdapter, this, new GridLayoutManager(this, 3));


        mAdapter.setNewData(dataSet);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            BackupWord item = mAdapter.getItem(position);
            if (getSelectedCount() == 2) {
                if (!item.isSelect()) {
                    return;
                }
            }
            item.setSelect(!item.isSelect());
            mAdapter.notifyDataSetChanged();
            tvConfirm.setEnabled(getSelectedCount() == 2);
        });
    }

    private BackupWord[] initRandom(ArrayList<BackupWord> dataSet) {
        ArrayList<Integer> random = new ArrayList<>();
        do {
            int randomInt = Kits.Random.getRandom(dataSet.size());
            if (!random.contains(randomInt)) {
                random.add(randomInt);
            }
        } while (random.size() != 2);
        return new BackupWord[]{dataSet.get(random.get(0)), dataSet.get(random.get(1))};
    }

    private int getSelectedCount() {
        int i = 0;
        for (BackupWord item : mAdapter.getData()) {
            if (item.isSelect()) {
                i = i + 1;
            }
        }
        return i;
    }

    private boolean doVerify(BackupWord[] random) {
        ArrayList<BackupWord> selected = new ArrayList<>();
        for (BackupWord item : mAdapter.getData()) {
            if (item.isSelect()) {
                selected.add(item);
            }
        }
        String randomFirst = random[0].getText();
        String randomSecond = random[1].getText();
        String selectFirst = selected.get(0).getText();
        String selectSecond = selected.get(1).getText();
        return (randomFirst.equals(selectFirst) || randomFirst.equals(selectSecond)) &&
                (randomSecond.equals(selectSecond) || randomSecond.equals(selectFirst));
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

}