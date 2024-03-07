package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.di.component.AppComponent;
import com.core.domain.manager.ChainCode;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.home.R;
import com.roo.router.Router;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;

import java.util.concurrent.TimeUnit;

/**
 * 导出私钥
 */
public class WalletExportPKActivity extends I18nActivityArms {

    public static void start(Context context, String remark, String chainCode) {
        Router.newIntent(context)
                .to(WalletExportPKActivity.class)
                .putString(Constants.KEY_REMARK, remark)
                .putString(Constants.KEY_CHAIN_CODE, chainCode)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wallet_export;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String chainCode = getIntent().getStringExtra(Constants.KEY_CHAIN_CODE);
        String remark = getIntent().getStringExtra(Constants.KEY_REMARK);
        UserWallet userWallet = EthereumWalletUtils.getInstance().getWalletByRemark(this, remark);

        TextView tvPK = findViewById(R.id.tvPrivateKey);
        ViewHelper.initTitleBar(getString(R.string.title_export_pk), this);

        try {
            switch (chainCode) {
                case ChainCode.ETH:
                case ChainCode.BSC:
                case ChainCode.HECO:
                case ChainCode.OEC:
                case ChainCode.TRON:
                case ChainCode.POLYGON:
                case ChainCode.FANTOM:
                    ECKeyPair ecKeyPair = Wallet.decrypt(SafePassword.get(), EthereumWalletUtils.getInstance().getWalletByChainCode(userWallet, chainCode).getWalletFile());
                    String privateKey = ecKeyPair.getPrivateKey().toString(16);
                    tvPK.setText(privateKey);

                    break;
            }
            RxView.clicks(findViewById(R.id.ivCopy)).throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(o -> {
                        GlobalUtils.copyToClipboard(tvPK.getText().toString(), this);
                        ToastUtils.show(getString(R.string.pk) + getString(R.string.copy_success));
                    });
        } catch (CipherException ignored) {
        }

    }


}
