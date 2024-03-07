package com.roo.home.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aries.ui.view.radius.RadiusEditText;
import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.trade.MyAssetsBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lzh.easythread.AsyncCallback;
import com.roo.core.app.annotation.CreateType;
import com.roo.core.app.component.Main;
import com.roo.core.app.component.Qrcode;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.app.constants.PrimaryKey;
import com.roo.core.daoManagers.NewAssetsDaoManager;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ThreadManager;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.HashUtil;
import com.roo.core.utils.utils.TokenManager;
import com.roo.home.R;
import com.roo.home.di.component.DaggerWalletImportComponent;
import com.roo.home.mvp.contract.WalletImportContract;
import com.roo.home.mvp.presenter.WalletImportPresenter;
import com.roo.home.mvp.ui.dialog.ChooseLinkDialog;
import com.roo.router.Router;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.xyzlf.custom.keyboardlib.SimpleKeyboardListener;
import org.simple.eventbus.EventBus;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 导入钱包
 */
public class WalletImportActivity extends BaseActivity<WalletImportPresenter> implements WalletImportContract.View {
    public static final int REQUEST_CODE_CREATE = 6598;

    private TextView tvImportWallet;
    private RadiusEditText etImportWallet;
    private String walletPassword;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(WalletImportActivity.class)
                .requestCode(REQUEST_CODE_CREATE)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

        DaggerWalletImportComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wallet_import;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvImportWallet = findViewById(R.id.tvImportWallet);
        etImportWallet = findViewById(R.id.etImportWallet);
        RxTextView.textChanges(etImportWallet).skipInitialValue()
                .debounce(100, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applySchedulers())
                .map(CharSequence::toString)
                .subscribe(event -> {
                    tvImportWallet.setEnabled(!TextUtils.isEmpty(event));
                });


        TitleBarView mTitleBarView = ViewHelper.initTitleBar(getString(R.string.import_exist_wallet), this);
        mTitleBarView.addRightAction(mTitleBarView.new ImageAction(R.drawable.ic_common_scan,
                v -> {
                    CC.obtainBuilder(Qrcode.NAME)
                            .setContext(this)
                            .setActionName(Qrcode.Action.QrcodeScanActivity)
                            .build().callAsyncCallbackOnMainThread((cc, result) -> {
                        if (result.isSuccess()) {
                            String resultDataItem = result.getDataItem(Qrcode.Result.RESULT);
                            etImportWallet.setText(resultDataItem);
                        } else {
                            ToastUtils.show(getString(R.string.toast_not_support_scan_type));
                        }
                    });
                }));

        RxView.clicks(findViewById(R.id.tvImportWallet)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (SafePassword.isExist()) {
                        //有密码的时候先验证，然后导入钱包
                        checkPassword();
                    } else {
                        //没密码的时候，先创建密码并保存，然后导入钱包
                        inputPassword();
                    }
                });

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    private void importWallet(String mnemonicsOrPK) {
        try {
            if (mnemonicsOrPK.contains(" ")) {//可能是助记词
                if (mnemonicsOrPK.split(" ").length == 12) {//12位助记词
                    //先获取第二个私钥
                    mPresenter.getMnemonics2(mnemonicsOrPK);
                } else {
                    ToastUtils.show(getString(R.string.error_mnemonics));
                    etImportWallet.setText("");
                }

            } else {//可能是私钥
                //先获取第二个私钥
                mPresenter.getPrivateKey2(mnemonicsOrPK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            etImportWallet.setText("");
            ToastUtils.show(R.string.toast_import_error);
        }
    }

    //通过助记词1和助记词2导入钱包
    public void importWalletByMnemonics(String strJson){
        try {
            String tempStr = strJson.replaceAll(" ", "|");
            JSONObject jsonObject = new JSONObject(tempStr);
            String tempMnemonics1 = jsonObject.getString("mnemonics");
            String tempMnemonics2 = jsonObject.getString("mnemonics2");
            String Mnemonics1 = tempMnemonics1.replaceAll("\\|", " ");
            String Mnemonics2 = tempMnemonics2.replaceAll("\\|", " ");

        if (EthereumWalletUtils.getInstance().isWalletExistedByMnemonics(Mnemonics1, this)) {
            etImportWallet.setText("");
            ToastUtils.show(getString(R.string.wallet_exist_already));
        } else {
            UserWallet userWallet = new UserWallet();
            String wallName = EthereumWalletUtils.getInstance().getWalletDefaultName(this);
            userWallet.setRemark(wallName);
            userWallet.setMnemonics(Mnemonics1);
            userWallet.setMnemonics2(Mnemonics2);
            userWallet.setCreateType(CreateType.WORDS);
            userWallet.setBackUpped(true);
            userWallet.setSelected(true);
            PrimaryKey.addIndex(PrimaryKey.WALLET_NAME);

            createDefaultWallet(userWallet, new AsyncCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    EthereumWalletUtils.getInstance().updateWallet(WalletImportActivity.this, userWallet, true);
                    MyAssetsBean myAssetsBean = new MyAssetsBean();
                    myAssetsBean.setWalletRemark(wallName);
                    NewAssetsDaoManager.insert(myAssetsBean);
                    EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.UPLOAD_JPUSH);

                    CC.obtainBuilder(Main.NAME)
                            .setContext(WalletImportActivity.this)
                            .setActionName(Main.Action.MainActivity)
                            .build().call();

                    ToastUtils.show(R.string.import_wallet_success);
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onFailed(Throwable t) {

                }
            });
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //通过私钥1和私钥2导入钱包
    public void importWalletByPK(String jsonStr){
        //第一步，选链
        ArrayList<LinkTokenBean.TokensBean> tokensBeans = new ArrayList<>();
        ChooseLinkDialog.newInstance().setOnClickListener((item) -> {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String pk1 = jsonObject.getString("privateKey");
                String pk2 = jsonObject.getString("privateKey2");
                UserWallet userWallet = new UserWallet();

                String chainCode = "";
                for (LinkTokenBean.TokensBean token : item.getTokens()) {
                    if (token.isMain()) {
                        chainCode = token.getChainCode();
                        LogManage.e("token--->" + token.toString());
                        tokensBeans.add(token);

                        ArrayList<LinkTokenBean.NodesBean> chainNode = TokenManager.getInstance().getChainNode();
                        if (!Kits.Empty.check(chainNode)) {
                            userWallet.setNodes(chainNode);
                        }

                        break;
                    }
                }
                userWallet.setTokenList(tokensBeans);

                //私钥创建钱包
                if (chainCode.equalsIgnoreCase(ChainCode.ETH)
                        || chainCode.equalsIgnoreCase(ChainCode.TRON)
                        || chainCode.equalsIgnoreCase(ChainCode.BSC)
                        || chainCode.equalsIgnoreCase(ChainCode.HECO)
                        || chainCode.equalsIgnoreCase(ChainCode.POLYGON)
                        || chainCode.equalsIgnoreCase(ChainCode.OEC)
                        || chainCode.equalsIgnoreCase(ChainCode.FANTOM)
                ) {
                    String formattedPrivateKey = pk1.startsWith(Constants.PREFIX_16) ?
                            pk1.substring(Constants.PREFIX_16.length()) : pk1;
                    String formattedPrivateKey2 = pk2.startsWith(Constants.PREFIX_16) ?
                            pk2.substring(Constants.PREFIX_16.length()) : pk2;
                    if (EthereumWalletUtils.getInstance().isWalletExistedByPk(formattedPrivateKey, this)) {
                        etImportWallet.setText("");
                        ToastUtils.show(getString(R.string.wallet_exist_already));
                        return;
                    } else {
                        if (formattedPrivateKey.length() != 64) {
                            ToastUtils.show(getString(R.string.error_privateKey));
                            etImportWallet.setText("");
                            return;
                        }
                        ECKeyPair ecKeyPair = EthereumWalletUtils.getInstance().getKeyPair(formattedPrivateKey);
                        ECKeyPair ecKeyPair2 = EthereumWalletUtils.getInstance().getKeyPair(formattedPrivateKey2);
                        WalletFile walletFile = Wallet.createLight(SafePassword.get(), ecKeyPair);
                        WalletFile walletFile2 = Wallet.createLight(SafePassword.get(), ecKeyPair2);

                        String wallName = EthereumWalletUtils.getInstance().getWalletDefaultName(this);
                        UserWallet.ChainWallet chainWallet = new UserWallet.ChainWallet();
                        chainWallet.setWalletFile(walletFile);
                        chainWallet.setWalletFile2(walletFile2);
                        chainWallet.setChainCode(chainCode);
                        userWallet.setCreateType(CreateType.PRIVATE);
                        userWallet.setRemark(wallName);
                        userWallet.setBackUpped(true);

                        userWallet.setPrivateKey(formattedPrivateKey);
                        userWallet.setPrivateKey2(formattedPrivateKey2);
                        userWallet.setSelected(true);
                        PrimaryKey.addIndex(PrimaryKey.WALLET_NAME);

                        userWallet.getChainWallets().add(chainWallet);
                        userWallet.getListMainChainCode().add(chainCode);
                    }
                }
                EthereumWalletUtils.getInstance().updateWallet(this, userWallet, true);
                MyAssetsBean myAssetsBean = new MyAssetsBean();
                myAssetsBean.setWalletRemark(userWallet.getRemark());
                NewAssetsDaoManager.insert(myAssetsBean);
                CC.obtainBuilder(Main.NAME)
                        .setContext(this)
                        .setActionName(Main.Action.MainActivity)
                        .build().call();

                ToastUtils.show(R.string.import_wallet_success);
                setResult(RESULT_OK);
                finish();

            } catch (CipherException | JSONException e) {
                e.printStackTrace();
            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
    }

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
                        importWallet(etImportWallet.getText().toString().trim());
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

        if (SafePassword.isExist()) {
            keyboardDialog.getTipsLayout().setVisibility(View.GONE);
        } else {
            keyboardDialog.getTipsLayout().setVisibility(View.VISIBLE);
        }

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
                tokenList.add(TokenManager.getInstance().getMainTokenBeanByChainCode(chainCode));
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
            if (requestCode == BackupWordsActivity.REQUEST_CODE_CREATE) {
                finish();
            }
        }
    }

    //有安全密码的时候，验证密码，再执行操作
    private void checkPassword() {
        KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(this);
        keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {

            @Override
            public void onPasswordComplete(String text) {
                if (SafePassword.isEquals(text)) {
                    importWallet(etImportWallet.getText().toString().trim());
                    keyboardDialog.dismiss();
                } else {
                    ToastUtils.show(getString(R.string.false_safe_pass));
                    keyboardDialog.clearPassword();
                }
            }
        });
        keyboardDialog.show();
        keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_input));
        if (SafePassword.isExist()) {
            keyboardDialog.getTipsLayout().setVisibility(View.GONE);
        } else {
            keyboardDialog.getTipsLayout().setVisibility(View.VISIBLE);
        }
    }
}