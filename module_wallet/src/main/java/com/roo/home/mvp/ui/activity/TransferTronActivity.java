package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aries.ui.view.radius.RadiusEditText;
import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.core.domain.dapp.TronTransaction;
import com.core.domain.dapp.TronTransactionTrc10;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.mine.AddressBookBean;
import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.TransactionResult;
import com.core.domain.mine.TransferRecordBean;
import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.ComponentApplication;
import com.roo.core.app.annotation.TransactionStatus;
import com.roo.core.app.component.Mine;
import com.roo.core.app.component.Qrcode;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.dialog.TransferConfirmDialog;
import com.roo.core.ui.widget.DialogLoading;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.BalanceManager;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TickerManager;
import com.roo.core.utils.utils.TokenManager;
import com.roo.home.R;
import com.roo.home.di.component.DaggerTransferTronComponent;
import com.roo.home.mvp.contract.TransferTronContract;
import com.roo.home.mvp.presenter.TransferTronPresenter;
import com.roo.home.mvp.utils.Base58Check;
import com.roo.home.mvp.utils.ByteArray;
import com.roo.home.mvp.utils.HexUtil;
import com.roo.home.mvp.utils.PenddingManager;
import com.roo.home.mvp.utils.WalletCoreUtil;
import com.roo.router.Router;
import com.subgraph.orchid.encoders.Hex;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.xyzlf.custom.keyboardlib.SimpleKeyboardListener;

import org.bitcoinj.core.Sha256Hash;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.Subscriber;
import org.tron.common.crypto.ECKey;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.ResponseBody;
import wallet.core.jni.AnyAddress;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.roo.home.mvp.utils.BalanceUtils.baseToSubunit;

public class TransferTronActivity extends BaseActivity<TransferTronPresenter> implements TransferTronContract.View {

    public static final String FR_RECORD = "FR_RECORD";
    public static final String FR_WALLET = "FR_WALLET";
    private LinkTokenBean.TokensBean tokensBean;
    private UserWallet userWallet;
    private String myAddress;//我的地址
    private RadiusEditText edAddress;
    private RadiusEditText edTransferCount;
    private ImageView ivAddressBook;
    private TextView tvConfirm;
    private TextView tvTransferable;


    public static void start(Context context, LinkTokenBean.TokensBean tokensBean, String fr) {
        start(context, tokensBean, "", fr);
    }

    public static void start(Context context, LinkTokenBean.TokensBean tokensBean, String address, String fr) {
        Router.newIntent(context)
                .to(TransferTronActivity.class)
                .putParcelable(Constants.KEY_DEFAULT, tokensBean)
                .putString(Constants.KEY_ADDRESS, address)
                .putString(Constants.KEY_FR, fr)
                .launch();
    }

    public String getPrivateKeyToAddress(String privateKey) {
        String address = Keys.toChecksumAddress(Keys.getAddress(ECKeyPair.create(Numeric.toBigInt(privateKey))));
        byte[] decode = Hex.decode(address.replace(Constants.PREFIX_16, "41"));
        return Base58Check.bytesToBase58(decode);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTransferTronComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_transfertron; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tokensBean = getIntent().getParcelableExtra(Constants.KEY_DEFAULT);
        userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);
        tvTransferable = findViewById(R.id.tvTransferable);

        initViewById();
        initListener();

        if (userWallet.getPrivateKey2() != null) {
            myAddress = getPrivateKeyToAddress(userWallet.getPrivateKey2());
        } else {
            HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
            myAddress = hdWallet.getAddressForCoin(CoinType.TRON);
        }


    }


    private void initViewById() {
        edTransferCount = findViewById(R.id.edTransferCount);
        edAddress = findViewById(R.id.edAddress);
        ivAddressBook = findViewById(R.id.iv_chosen_address);
        tvConfirm = findViewById(R.id.tvConfirm);
        String address = getIntent().getStringExtra(Constants.KEY_ADDRESS);
        edAddress.setText(address);
        setTransferable();
    }

    private void initListener() {
        TitleBarView mTitleBarView = ViewHelper.initTitleBar(MessageFormat.format(getString(R.string.tip_transfer),
                tokensBean.getSymbol()), this);
        mTitleBarView.addRightAction(mTitleBarView.new ImageAction(R.drawable.ic_common_scan,
                v -> {
                    CC.obtainBuilder(Qrcode.NAME)
                            .setContext(this)
                            .setActionName(Qrcode.Action.QrcodeScanActivity)
                            .build().callAsyncCallbackOnMainThread((cc, result) -> {
                        if (result.isSuccess()) {
                            String resultDataItem = result.getDataItem(Qrcode.Result.RESULT);
                            edAddress.setText(resultDataItem);
                        } else {
                            ToastUtils.show(getString(R.string.toast_not_support_scan_type));
                        }
                    });
                }));
        RxView.clicks(ivAddressBook).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    CC.obtainBuilder(Mine.NAME)
                            .setContext(this)
                            .addParam(Mine.Param.chainCode, ChainCode.TRON)
                            .setActionName(Mine.Action.AddressBookActivity)
                            .build().callAsyncCallbackOnMainThread((cc, result) -> {
                        if (result.isSuccess()) {
                            AddressBookBean item = result.getDataItem(Mine.Result.RESULT);
                            edAddress.setText(item.getAddress());
                        }
                    });
                });

        InitialValueObservable<CharSequence> observableAddress = RxTextView.textChanges(edAddress);
        InitialValueObservable<CharSequence> observableTransferCount = RxTextView.textChanges(edTransferCount);

        Observable.combineLatest(observableAddress, observableTransferCount, (s1, s2) -> {
            boolean condition = !(Kits.Empty.check(s1) || Kits.Empty.check(s2));

            return condition;
        }).subscribe(new ErrorHandleSubscriber<Boolean>(ComponentApplication.getRxErrorHandler()) {
            @Override
            public void onNext(@NotNull Boolean verify) {
                tvConfirm.setEnabled(verify);
            }
        });

        RxView.clicks(tvConfirm).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    String toAddress = Kits.Text.getText(edAddress);
                    String transferCount = Kits.Text.getText(edTransferCount);

                    if (!AnyAddress.isValid(toAddress, CoinType.TRON)) {
                        ToastUtils.show(getString(R.string.error_address));
                        return;
                    }

                    if (Objects.equals(toAddress, myAddress)) {
                        ToastUtils.show(R.string.send_same_address);
                        return;
                    }
                    BigDecimal bigDecimal = BalanceManager.getInstance().get(userWallet, tokensBean);
                    LogManage.e("bigDecimal" + bigDecimal);


                    //判断余额是否足够
                    BigDecimal gasFee;
                    gasFee = BigDecimal.ZERO;
                    if (isBalanceEnough(gasFee)) {
                        TransferConfirmDialog.newInstance
                                (myAddress, toAddress, transferCount, tokensBean, gasFee)
                                .setOnEnsureClickedListener(() -> {
                                    KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(this);
                                    keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {
                                        @Override
                                        public void onPasswordComplete(String passWord) {
                                            if (SafePassword.isEquals(passWord)) {
                                                showLoading();
                                                transTron();
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
                                }).show(getSupportFragmentManager(), getClass().getSimpleName());
                    }

                });

    }

    private void transTron() {
        String toAddress = Kits.Text.getText(edAddress);
        String amount = Kits.Text.getText(edTransferCount);
        BigInteger use = baseToSubunit(amount, 6);
        if (!tokensBean.isMain()) {//如果不是主网币
            if (tokensBean.getContractId().startsWith("T")) {//trc20 转账
                LogManage.e("token---->" + tokensBean.getContractId());


                String s1 = new WalletCoreUtil().stringTo64b(ByteArray.toHexString(Base58Check.base58ToBytes(toAddress)));

                String s2 = new WalletCoreUtil().stringTo64b(use.toString(16));

                String s = ByteArray.toHexString(Base58Check.base58ToBytes(toAddress));
                LogManage.e("s----->" + s);

                LogManage.e("token---->" + s1 + s2);
                Map map = new HashMap();
                map.put("parameter", s1 + s2);
                map.put("owner_address", ByteArray.toHexString(Base58Check.base58ToBytes(myAddress)));
                map.put("contract_address", ByteArray.toHexString(Base58Check.base58ToBytes(tokensBean.getContractId())));
                map.put("function_selector", "transfer(address,uint256)");
                map.put("fee_limit", 100000000);

                mPresenter.triggerSmartContract(new Gson().toJson(map));
            } else {//trc10 转账
                LogManage.e("tokensBean.getContractId()--->" + tokensBean.getContractId());
                Map map = new HashMap();
                map.put("owner_address", ByteArray.toHexString(Base58Check.base58ToBytes(myAddress)));
                map.put("to_address", ByteArray.toHexString(Base58Check.base58ToBytes(toAddress)));
                map.put("asset_name", ByteArray.toHexString(tokensBean.getContractId().getBytes()));
                map.put("amount", use);
                LogManage.e("createTransactionTrc10---" + new Gson().toJson(map));


                mPresenter.createTransactionTrc10(new Gson().toJson(map));
            }

        } else {
            Objects.requireNonNull(mPresenter).getNowBlock();
        }

    }

    private boolean isBalanceEnough(BigDecimal fee) {
        boolean isEnough = true;
        String amountString = Kits.Text.getText(edTransferCount);
        BigDecimal amount = new BigDecimal(amountString);

        if (tokensBean.isMain()) {
            if (BalanceManager.getInstance().get(userWallet, tokensBean).compareTo(amount.add(fee)) < 0) {
                ToastUtils.show(getString(R.string.toast_balance_not_enough));
                isEnough = false;
            }
        } else {
            String mainSymbol = TokenManager.getInstance().getMainSymbolByChainCode(tokensBean.getChainCode());
            //balanceMain：token的主链上的币
            BigDecimal balanceMain = BalanceManager.getInstance()
                    .get(userWallet, tokensBean.getChainCode(), "");
            if (balanceMain.compareTo(fee) < 0) {//主币余额不足
                ToastUtils.show(tokensBean.getChainCode() + getString(R.string.toast_fee_not_enough));
                isEnough = false;
            } else if (BalanceManager.getInstance().get(userWallet, tokensBean).compareTo(amount) < 0) {//代币余额小于转账数量
                ToastUtils.show(tokensBean.getSymbol() + getString(R.string.toast_token_not_enough));
                isEnough = false;
            }
        }

        return isEnough;
    }

    private void setTransferable() {
        BigDecimal bigDecimal = BalanceManager.getInstance().get(userWallet, tokensBean);
        tvTransferable.setText(MessageFormat.format("{0}{1}",
                TickerManager.getInstance().getDecimalSymbolCount(tokensBean.getSymbol(), bigDecimal)
                        .toPlainString(),
                tokensBean.getSymbol()));
    }

    @Subscriber(tag = EventBusTag.LOADED_BALANCE)
    public void onLoadedBalance(String event) {

        ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
        for (LinkTokenBean.TokensBean bean : tokenList) {
            if (tokensBean.getChainCode().equalsIgnoreCase(bean.getChainCode()) &&
                    tokensBean.getSymbol().equalsIgnoreCase(bean.getSymbol())) {
                this.tokensBean = bean;
                break;
            }
        }
        setTransferable();
    }

    @Override
    public void showLoading() {
        DialogLoading.getInstance().showDialog(this);
    }

    @Override
    public void hideLoading() {
        DialogLoading.getInstance().closeDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != tvTransferable) {
            setTransferable();
        }
    }

    @Override
    public void getNowBlockDataSet(BlockHeader t) {
            String toAddress = Kits.Text.getText(edAddress);
        String amount = Kits.Text.getText(edTransferCount);
        long number = t.block_header.raw_data.number;
        String witnessAddress = t.block_header.raw_data.witness_address;
        String txTrieRoot = t.block_header.raw_data.txTrieRoot;
        String parentHash = t.block_header.raw_data.parentHash;
        long timestamp = t.block_header.raw_data.timestamp;
        int version = t.block_header.raw_data.version;
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);
        if (userWallet != null) {
            String privateKeyHex;
            byte[] privateKey;
            if (userWallet.getMnemonics2() != null) {
                HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
                privateKey = hdWallet.getKeyForCoin(CoinType.TRON).data();
                privateKeyHex = HexUtil.byte2Hex(privateKey, false, false);
            } else {
                privateKeyHex = userWallet.getPrivateKey2();
            }

            BigInteger use = baseToSubunit(amount, 6);
            String token = "";
            LogManage.e(
                    "\nnumber:" + number
                            + "\nwitnessAddress:" + witnessAddress
                            + "\ntxTrieRoot:" + txTrieRoot
                            + "\nparentHash:" + parentHash
                            + "\ntimestamp:" + timestamp
                            + "\nversion:" + version
                            + "\nfrom_adress:" + myAddress
                            + "\nto_adress:" + toAddress
                            + "\namount:" + use
                            + "\nprivateKey:" + privateKeyHex
                            + "\ndecimals:" + 6
                            + "\ngasLimit:" + 6000);
            LogManage.e("tokensBean.isMain()----?" + tokensBean.isMain());

            if (tokensBean.isMain()){
                String hexStrin = new WalletCoreUtil().getSignTransferTrc20Contract(myAddress,
                        "",
                        toAddress,
                        use,
                        timestamp,
                        txTrieRoot,
                        parentHash,
                        number,
                        witnessAddress,
                        version,
                        privateKeyHex,
                        "", 0L);
                LogManage.e("hexStrin--->" + hexStrin);
                mPresenter.broadcastTransaction(hexStrin);
            }
        }
    }

    @Override
    public void broadcastTransactionSuccess(TransactionResult t) {
        LogManage.e("t---->" + t.toString());
        if (t.result) {
            ToastUtils.show("广播成功");
            String fr = getIntent().getStringExtra(Constants.KEY_FR);
            if (fr.equals(TransferTronActivity.FR_WALLET)) {
                TransferRecordActivity.start(this, tokensBean);
            }
            String amount = Kits.Text.getText(edTransferCount);
            String toAddress = Kits.Text.getText(edAddress);
            generateTrans(toAddress, amount, t.txid);
        }
    }

    private static String signTransaction2Byte(byte[] transactionBytes, byte[] privateKey) throws InvalidProtocolBufferException {

        ECKey ecKey = ECKey.fromPrivate(privateKey);
        byte[] hash = Sha256Hash.hash(transactionBytes);
        ECKey.ECDSASignature sign1 = ecKey.sign(hash);
        String s = sign1.toHex();
        return s;
    }

    /**
     * 获取trc20 Transaction
     *
     * @param t
     */
    @Override
    public void getTransactionSuccess(ResponseBody t) {
        String privateKeyHex;
        byte[] privateKey;
        if (userWallet.getMnemonics2() != null) {
            HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
            privateKey = hdWallet.getKeyForCoin(CoinType.TRON).data();
            privateKeyHex = HexUtil.byte2Hex(privateKey, false, false);
        } else {
            privateKeyHex = userWallet.getPrivateKey2();
        }
        try {
            String string = t.string();
            LogManage.e("Transaction--->" + string);
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.has("transaction")) {  //transaction
                JSONObject transaction = jsonObject.getJSONObject("transaction");
                TronTransaction data = new Gson().fromJson(transaction.toString(), TronTransaction.class);
                LogManage.e("data.raw_data_hex--->" + data.raw_data_hex);
                byte[] rawData = Numeric.hexStringToByteArray(data.raw_data_hex);
                String bytes = signTransaction2Byte(rawData, Numeric.hexStringToByteArray(privateKeyHex));
                ArrayList<String> signature = new ArrayList<>();
                signature.add(bytes);
                Map map = new HashMap();
                map.put("txID", data.txID);
                map.put("raw_data", data.raw_data);
                map.put("raw_data_hex", data.raw_data_hex);
                map.put("signature", signature);
                String hexStrin = new Gson().toJson(map);
                LogManage.e("hexStrin--->" + hexStrin);
                mPresenter.broadcastTransaction(hexStrin);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTransactionTrc10Success(ResponseBody t) {
        hideLoading();
        String privateKeyHex;
        byte[] privateKey;
        if (userWallet.getMnemonics2() != null) {
            HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
            privateKey = hdWallet.getKeyForCoin(CoinType.TRON).data();
            privateKeyHex = HexUtil.byte2Hex(privateKey, false, false);
        } else {
            privateKeyHex = userWallet.getPrivateKey2();
        }
        try {
            String string = t.string();
            TronTransactionTrc10 data = new Gson().fromJson(string, TronTransactionTrc10.class);

            byte[] rawData = Numeric.hexStringToByteArray(data.raw_data_hex);
            String bytes = signTransaction2Byte(rawData, Numeric.hexStringToByteArray(privateKeyHex));
            ArrayList<String> signature = new ArrayList<>();
            signature.add(bytes);

            data.signature = signature;
            String hexStrin = new Gson().toJson(data);
            mPresenter.broadcastTransaction(hexStrin);

        } catch (IOException e) {
            e.printStackTrace();
            hideLoading();
        }
    }

    private void generateTrans(String toAddress, String amount, String transactionHash) {

        TransferRecordBean.DataBean localPendding = new TransferRecordBean.DataBean();
        localPendding.setTxId(transactionHash);
        localPendding.setToken(tokensBean.getSymbol());//转的币

        localPendding.setFromAddr(myAddress);
        localPendding.setToAddr(toAddress);

        localPendding.setAmount(new BigDecimal(amount));

//        localPendding.setGasLimit(new BigDecimal(gasLimit.toString()));
//        localPendding.setGasPrice(new BigDecimal("0"));

        localPendding.setGasUsed(BigDecimal.ZERO);
        localPendding.setConvertGasUsed(BigDecimal.ZERO);

        String gasFeeUnit = TokenManager.getInstance().getMainSymbolByChainCode(tokensBean.getChainCode());
        localPendding.setFeeToken(gasFeeUnit);//主链币

        localPendding.setTimeStamp(System.currentTimeMillis() / 1000);
        localPendding.setStatusType(TransactionStatus.PENDING);
        localPendding.setFromSelf(true);
        PenddingManager.getInstance().addPendding(localPendding, tokensBean);

        String fr = getIntent().getStringExtra(Constants.KEY_FR);
        if (fr.equals(TransferTronActivity.FR_WALLET)) {
            TransferRecordActivity.start(this, tokensBean);
        }
        finish();
    }


}