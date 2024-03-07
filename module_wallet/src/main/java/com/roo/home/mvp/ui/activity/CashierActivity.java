package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.gyf.immersionbar.ImmersionBar;
import com.huantansheng.easyphotos.utils.bitmap.BitmapUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.annotation.CreateType;
import com.roo.core.app.component.Qrcode;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.dialog.BackupTipsDialog;
import com.roo.core.ui.dialog.ShareDialog;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.home.R;
import com.roo.home.di.component.DaggerCashierComponent;
import com.roo.home.mvp.contract.CashierContract;
import com.roo.home.mvp.presenter.CashierPresenter;
import com.roo.router.Router;
import com.subgraph.orchid.encoders.Hex;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.xyzlf.custom.keyboardlib.SimpleKeyboardListener;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import wallet.core.jni.Base58;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 收款页面
 */
public class CashierActivity extends BaseActivity<CashierPresenter> implements CashierContract.View {

    private boolean onShare;

    public static void start(Context context, LinkTokenBean.TokensBean tokensBean) {
        Router.newIntent(context)
                .putParcelable(Constants.KEY_DEFAULT, tokensBean)
                .to(CashierActivity.class)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCashierComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_cashier;
    }


    private String getPrivateKeyToAddress(String privateKey) {
        String address = Keys.toChecksumAddress(Keys.getAddress(ECKeyPair.create(Numeric.toBigInt(privateKey))));
        byte[] decode = Hex.decode(address.replace(Constants.PREFIX_16, "41"));
        return Base58.encode(decode);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        LinkTokenBean.TokensBean tokensBean = getIntent().getParcelableExtra(Constants.KEY_DEFAULT);

        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);


        TextView tvCashierTip = findViewById(R.id.tvCashierTip);
        String address = "";
        switch (tokensBean.getChainCode()) {
            case ChainCode.TRON:
                if (userWallet.getPrivateKey2() != null) {
                    address = getPrivateKeyToAddress(userWallet.getPrivateKey2());
                } else {
                    HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
                    address = hdWallet.getAddressForCoin(CoinType.TRON);
                }
                tvCashierTip.setText(MessageFormat.format(getString(R.string.home_cashier_tips), tokensBean.getSymbol(), tokensBean.getChainCode()));
                break;
            case ChainCode.ETH:


                address = Constants.PREFIX_16 + EthereumWalletUtils.getInstance()
                        .getWalletByChainCode(userWallet, tokensBean.getChainCode()).getWalletFile2().getAddress();
                tvCashierTip.setText(MessageFormat.format(getString(R.string.home_cashier_tips), tokensBean.getSymbol(), "Ethereum"));
                break;
            case ChainCode.BSC:

                address = Constants.PREFIX_16 + EthereumWalletUtils.getInstance()
                        .getWalletByChainCode(userWallet, tokensBean.getChainCode()).getWalletFile2().getAddress();
                tvCashierTip.setText(MessageFormat.format(getString(R.string.home_cashier_tips), "BSC"));
                tvCashierTip.setText(MessageFormat.format(getString(R.string.home_cashier_tips), tokensBean.getSymbol(), "BSC"));
                break;
            case ChainCode.HECO:

                address = Constants.PREFIX_16 + EthereumWalletUtils.getInstance()
                        .getWalletByChainCode(userWallet, tokensBean.getChainCode()).getWalletFile2().getAddress();
                tvCashierTip.setText(MessageFormat.format(getString(R.string.home_cashier_tips), tokensBean.getSymbol(), "HECO"));
                break;
            default:
                address = Constants.PREFIX_16 + EthereumWalletUtils.getInstance()
                        .getWalletByChainCode(userWallet, tokensBean.getChainCode()).getWalletFile2().getAddress();
                tvCashierTip.setText(MessageFormat.format(getString(R.string.home_cashier_tips), tokensBean.getSymbol(), tokensBean.getChainCode()));
                break;
        }


        TitleBarView titleBarView = ViewHelper.initTitleBar("收款", this, R.drawable.ic_common_back_white);
        ImmersionBar.with(this).statusBarDarkFont(false).init();
        titleBarView.setTitleMainTextColor(ContextCompat.getColor(this, R.color.white));
        TextView tvAddress = findViewById(R.id.tvAddress);
        tvAddress.setText(address);

        TextView tvAddressShare = findViewById(R.id.tvAddressShare);
        tvAddressShare.setText(address);


        ImageView ivQrcode = findViewById(R.id.ivQrcode);
        ImageView ivQrcodeShare = findViewById(R.id.ivQrcodeShare);
        CC.obtainBuilder(Qrcode.NAME)
                .setContext(this)
                .addParam(Qrcode.Param.Text, address)
                .setActionName(Qrcode.Action.ContentEncode)
                .build().callAsyncCallbackOnMainThread((cc, result) -> {
            if (result.isSuccess()) {
                Bitmap bitmap = result.getDataItem(Qrcode.Result.RESULT);
                ivQrcode.setImageBitmap(bitmap);
                ivQrcodeShare.setImageBitmap(bitmap);
            }
        });

        TextView tvShare = findViewById(R.id.tvShare);
        String finalAddress = address;
        RxView.clicks(tvShare).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (TextUtils.isEmpty(finalAddress)) {
                        return;
                    }
                    View view = findViewById(R.id.layoutShare);
                    ShareDialog.newInstance(BitmapUtils.createBitmapFromView(view))
                            .setOnClickListener(() -> {
                                onShare = true;
                            })
                            .show(getSupportFragmentManager(), getClass().getSimpleName());
                });

        TextView tvCopy = findViewById(R.id.tvCopy);
        String finalAddress1 = address;
        RxView.clicks(tvCopy).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    ToastUtils.show(getString(R.string.copy_success));
                    GlobalUtils.copyToClipboard(finalAddress1, this);
                });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (onShare) {
            ToastUtils.show(getString(R.string.share_success));
            onShare = false;
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    private boolean needToBackUp() {
        UserWallet userWallet = getUserWallet();
        if (userWallet == null
                || userWallet.isBackUpped()
                || userWallet.getCreateType() == CreateType.PRIVATE) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 备份助记词提示
     */
    private void initBackupTipLayout(UserWallet userWallet) {
        if (userWallet == null
                || userWallet.isBackUpped()
                || userWallet.getCreateType() == CreateType.PRIVATE) {
        } else {
            BackupTipsDialog backupTipsDialog = BackupTipsDialog.newInstance();
            backupTipsDialog.setOnClickListener(new BackupTipsDialog.OnClickedListener() {
                @Override
                public void onClick() {
                    KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(CashierActivity.this);
                    keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {

                        @Override
                        public void onPasswordComplete(String text) {
                            if (SafePassword.isEquals(text)) {
                                BackupWordsActivity.start(CashierActivity.this, userWallet.getRemark(), false);
                                keyboardDialog.dismiss();
                                backupTipsDialog.dismiss();
                            } else {
                                ToastUtils.show(getString(R.string.false_safe_pass));
                                keyboardDialog.clearPassword();
                            }
                        }
                    });
                    keyboardDialog.show();
                    keyboardDialog.getTipsLayout().setVisibility(View.GONE);

                    keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_input));
                }

                @Override
                public void onCancel() {
                    finish();
                }
            }).show(getSupportFragmentManager(), getClass().getSimpleName());

        }
    }

    private UserWallet getUserWallet() {
        return EthereumWalletUtils.getInstance().getSelectedWalletOrFirst(getActivity());
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserWallet userWallet = getUserWallet();
        initBackupTipLayout(userWallet);

    }

}