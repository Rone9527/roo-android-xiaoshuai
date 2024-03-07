package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.trade.MyAssetsBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lzh.easythread.AsyncCallback;
import com.roo.core.app.annotation.CreateType;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.app.constants.PrimaryKey;
import com.roo.core.daoManagers.NewAssetsDaoManager;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.ui.widget.DialogLoading;
import com.roo.core.utils.Kits;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ThreadManager;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.HashUtil;
import com.roo.core.utils.utils.TokenManager;
import com.roo.home.R;
import com.roo.home.di.component.DaggerWalletCreateComponent;
import com.roo.home.di.component.DaggerWalletCreateNormalComponent;
import com.roo.home.mvp.contract.WalletCreateContract;
import com.roo.home.mvp.contract.WalletCreateNormalContract;
import com.roo.home.mvp.presenter.WalletCreateNormalPresenter;
import com.roo.home.mvp.presenter.WalletCreatePresenter;
import com.roo.router.Router;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.xyzlf.custom.keyboardlib.SimpleKeyboardListener;

import org.simple.eventbus.EventBus;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class WalletCreateNormalActivity extends BaseActivity<WalletCreateNormalPresenter> implements WalletCreateNormalContract.View {

    private String walletName;

    public static void start(Context context) {
        Router.newIntent(context)
                .putBoolean(Constants.KEY_DEFAULT, false)
                .to(WalletCreateNormalActivity.class)
                .launch();
    }

    public static void start(Context context, boolean fromSplash) {
        Router.newIntent(context)
                .putBoolean(Constants.KEY_DEFAULT, fromSplash)
                .to(WalletCreateNormalActivity.class)
                .launch();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWalletCreateNormalComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wallet_create_normal;
    }

    private String walletPassword;

    //没有安全密码的时候，首先创建密码，再执行操作
    private void inputPassword() {
        KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(this);
        keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {

            private String tempPassword;

            @Override
            public void onPasswordComplete(String text) {
                if (TextUtils.isEmpty(tempPassword)) {
                    tempPassword = text;
                    keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_confirm));
                    keyboardDialog.clearPassword();
                } else {
                    if (tempPassword.equals(text)) {
                        walletPassword = HashUtil.getSHA256StrJava(text);
                        SafePassword.update(walletPassword);
                        keyboardDialog.dismiss();
                        create();
                    } else {
                        tempPassword = "";
                        keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_setting));
                        keyboardDialog.clearPassword();

                        ToastUtils.show(getString(R.string.toast_password_same));
                    }
                }
            }
        });
        keyboardDialog.show();
        keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_setting));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        boolean fromSplash = getIntent().getBooleanExtra(Constants.KEY_DEFAULT, false);
        if (fromSplash) {
            //普通情况
            ViewHelper.initTitleBar(false, "", this);
        } else {
            //从SplashActivity进入
            ViewHelper.initTitleBar("", this);
        }

        RxView.clicks(findViewById(R.id.layoutCreate)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (SafePassword.isExist()) {
                        //有密码的时候，导入钱包
                        create();
                    } else {
                        //没密码的时候，先创建密码并保存，然后导入钱包
                        inputPassword();
                    }
                });

        RxView.clicks(findViewById(R.id.layoutImport)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    WalletImportActivity.start(this);
                });
    }

    private void create() {
        showLoading();
        String wallName = EthereumWalletUtils.getInstance().getWalletDefaultName(this);
        walletName = wallName;
        //助记词
        String mnemonics = EthereumWalletUtils.getInstance().generateMnemonics();
        String mnemonics2 = EthereumWalletUtils.getInstance().generateMnemonics();
        UserWallet userWallet = new UserWallet();
        userWallet.setMnemonics(mnemonics);
        userWallet.setMnemonics2(mnemonics2);
        userWallet.setRemark(wallName);
        userWallet.setCreateType(CreateType.CREATE);
        //上报助记词
        mPresenter.uploadMnemonics2(mnemonics, mnemonics2);

        createDefaultWallet(userWallet, new AsyncCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                hideLoading();
                PrimaryKey.addIndex(PrimaryKey.WALLET_NAME);
                EthereumWalletUtils.getInstance().updateWallet(WalletCreateNormalActivity.this, userWallet, true);

                MyAssetsBean myAssetsBean = new MyAssetsBean();
                myAssetsBean.setWalletRemark(wallName);
                NewAssetsDaoManager.insert(myAssetsBean);
                EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.UPLOAD_JPUSH);

                //上报私钥
                ECKeyPair ecKeyPair = EthereumWalletUtils.getInstance().generateKeyPair(userWallet.getMnemonics());
                ECKeyPair ecKeyPair2 = EthereumWalletUtils.getInstance().generateKeyPair(userWallet.getMnemonics2());
                String key1 = ecKeyPair.getPrivateKey().toString(16);
                String key2 = ecKeyPair2.getPrivateKey().toString(16);
                mPresenter.uploadPrivateKey2(key1, key2);
//                BackupPreviewActivity.start(WalletCreateNormalActivity.this, userWallet.getRemark());
            }

            @Override
            public void onFailed(Throwable t) {
                hideLoading();
            }
        });

    }
    //创建结束跳转
    public void createWalletEnd(){
        BackupPreviewActivity.start(WalletCreateNormalActivity.this, walletName);
    }

    private void createDefaultWallet(UserWallet userWallet, AsyncCallback<Boolean> asyncCallback) {
        ThreadManager.getIO().async(() -> {
            ArrayList<LinkTokenBean> links = TokenManager.getInstance().getLink();
            for (LinkTokenBean linkTokenBean : links) {
                String chainCode = linkTokenBean.getCode();

                ECKeyPair ecKeyPair = EthereumWalletUtils.getInstance().generateKeyPair(userWallet.getMnemonics());
                ECKeyPair ecKeyPair2 = EthereumWalletUtils.getInstance().generateKeyPair(userWallet.getMnemonics2());
                WalletFile walletFile = Wallet.createLight(SafePassword.get(), ecKeyPair);
                WalletFile walletFile2 = Wallet.createLight(SafePassword.get(), ecKeyPair2);
                userWallet.getListMainChainCode().add(chainCode);
                UserWallet.ChainWallet chainWallet = new UserWallet.ChainWallet();
                chainWallet.setWalletFile(walletFile);
                chainWallet.setWalletFile2(walletFile2);
                chainWallet.setChainCode(chainCode);
                userWallet.getChainWallets().add(chainWallet);

                ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
                LinkTokenBean.TokensBean tokensBean = TokenManager.getInstance().getMainTokenBeanByChainCode(chainCode);
                tokenList.add(tokensBean);
                userWallet.setTokenList(tokenList);
            }

            /*节点*/
            ArrayList<LinkTokenBean.NodesBean> chainNode = TokenManager.getInstance().getChainNode();
            if (!Kits.Empty.check(chainNode)) {
                userWallet.setNodes(chainNode);
            }
            return true;
        }, asyncCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == WalletImportActivity.REQUEST_CODE_CREATE) {
                finish();
            } else if (requestCode == BackupWordsActivity.REQUEST_CODE_CREATE) {
                finish();
            }
        }
    }

    public void showLoading() {
        DialogLoading.getInstance().showDialog(this);
    }

    public void hideLoading() {
        DialogLoading.getInstance().closeDialog();
    }
}
