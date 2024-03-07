package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.aries.ui.view.radius.RadiusEditText;
import com.aries.ui.view.radius.RadiusRelativeLayout;
import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.core.domain.dapp.EthereumNetworkBase;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.mine.AddressBookBean;
import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.GasBean;
import com.core.domain.mine.TransactionResult;
import com.core.domain.mine.TransferRecordBean;
import com.core.domain.trade.SignatureFromKey;
import com.core.domain.trade.TransferPointBean;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.ComponentApplication;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.annotation.TransactionStatus;
import com.roo.core.app.api.ApiService;
import com.roo.core.app.component.Mine;
import com.roo.core.app.component.Qrcode;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.model.UserWallet;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.ui.dialog.TransferConfirmDialog;
import com.roo.core.ui.widget.DialogLoading;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RetrofitFactory;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ThreadManager;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.BalanceManager;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TickerManager;
import com.roo.core.utils.utils.TokenManager;
import com.roo.core.utils.utils.Web3jUtil;
import com.roo.home.R;
import com.roo.home.di.component.DaggerTransferComponent;
import com.roo.home.mvp.contract.TransferContract;
import com.roo.home.mvp.presenter.TransferPresenter;
import com.roo.home.mvp.utils.AddressConver;
import com.roo.home.mvp.utils.Base58Check;
import com.roo.home.mvp.utils.ByteArray;
import com.roo.home.mvp.utils.CoinMyUtils;
import com.roo.home.mvp.utils.HexUtil;
import com.roo.home.mvp.utils.PenddingManager;
import com.roo.home.mvp.utils.WalletCoreUtil;
import com.roo.router.Router;
import com.subgraph.orchid.encoders.Hex;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.xyzlf.custom.keyboardlib.SimpleKeyboardListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.Subscriber;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.Sign;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import wallet.core.jni.AnyAddress;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.roo.home.mvp.utils.BalanceUtils.baseToSubunit;

/**
 * 以太链的转账
 * 修改记录
 * 2020/8/12-王天运
 * 问题：ETH代币转账时，交易hash返回为null
 * 定位：由于针对伦敦节点升级，导致的代币转账必须经过EIP1555签名处理，否则发送交易会抛出异常导致无法正常发起交易
 * 处理：已经经过EIP1555签名处理，然后发送交易并返回hash
 * tip：目前测试通过了ETH及平行链BSC、HECO代币的转账，密切关注ETH以及各链的升级情况
 */
public class TransferETHActivity extends BaseActivity<TransferPresenter> implements TransferContract.View {
    public static final String FR_RECORD = "FR_RECORD";
    public static final String FR_WALLET = "FR_WALLET";

    private RadiusRelativeLayout layoutSpeedNormal;
    private RadiusRelativeLayout layoutSpeedFast;
    private RadiusRelativeLayout layoutSpeedBest;
    private LinearLayout layoutGasSetting;
    private ImageView ivSpeedBest;
    private ImageView ivSpeedFast;
    private LinearLayout layoutGas;
    private ImageView ivSpeedNormal;
    private RadiusEditText edGasPrice, edGasLimit;
    private TextView tvTransferable;
    private RadiusEditText edTransferCount;
    private RadiusEditText edAddress;
    private TextView tvSpeedBestValue, tvSpeedFastValue, tvSpeedNormalValue;
    private LinkTokenBean.TokensBean tokensBean;
    private UserWallet userWallet;
    private UserWallet.ChainWallet chainWallet;
    private Web3j web3j;
    private BigDecimal gasPrice;
    private BigInteger gasLimit;
    private String myAddress;//我的地址
    private GasBean gasBean;
    private TextView tvGasFee;
    private String unit;//代币精度
    private String privateKey;//私钥1

    public static void start(Context context, LinkTokenBean.TokensBean tokensBean, String fr) {
        start(context, tokensBean, "", fr);
    }

    public static void start(Context context, LinkTokenBean.TokensBean tokensBean, String address, String fr) {
        Router.newIntent(context)
                .to(TransferETHActivity.class)
                .putParcelable(Constants.KEY_DEFAULT, tokensBean)
                .putString(Constants.KEY_ADDRESS, address)
                .putString(Constants.KEY_FR, fr)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTransferComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_transfer;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tokensBean = getIntent().getParcelableExtra(Constants.KEY_DEFAULT);
        userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);
        String chainCode = tokensBean.getChainCode();
        switch (chainCode) {
            case ChainCode.TRON:
                if (userWallet.getPrivateKey2() != null) {
                    myAddress = getPrivateKeyToAddress(userWallet.getPrivateKey2());
                } else {
                    HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
                    myAddress = hdWallet.getAddressForCoin(CoinType.TRON);
                }

                break;
            default:
                chainWallet = EthereumWalletUtils.getInstance().getWalletByChainCode(userWallet, tokensBean.getChainCode());
                myAddress = Constants.PREFIX_16 + chainWallet.getWalletFile2().getAddress();
                break;
        }

        web3j = Web3jUtil.getInstance().getBlockConnect(tokensBean.getChainCode());

        edAddress = findViewById(R.id.edAddress);
        layoutGasSetting = findViewById(R.id.layoutGasSetting);
        layoutGas = findViewById(R.id.ll_gas);
        if (tokensBean.getChainCode().equals(ChainCode.TRON)) {
            layoutGas.setVisibility(View.GONE);
        }
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

        String address = getIntent().getStringExtra(Constants.KEY_ADDRESS);
        edAddress.setText(address);
        TextView tvConfirm = findViewById(R.id.tvConfirm);
        edGasPrice = findViewById(R.id.edGasPrice);
        edAddress.setHint(MessageFormat.format(getString(R.string.hint_input_x_address), tokensBean.getSymbol()));
        RxView.clicks(findViewById(R.id.ivAddressBook)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    CC.obtainBuilder(Mine.NAME)
                            .setContext(this)
                            .addParam(Mine.Param.chainCode, chainWallet.getChainCode())
                            .setActionName(Mine.Action.AddressBookActivity)
                            .build().callAsyncCallbackOnMainThread((cc, result) -> {
                        if (result.isSuccess()) {
                            AddressBookBean item = result.getDataItem(Mine.Result.RESULT);
                            edAddress.setText(item.getAddress());
                        }
                    });
                });

        tvTransferable = findViewById(R.id.tvTransferable);
        edTransferCount = findViewById(R.id.edTransferCount);
        tvGasFee = findViewById(R.id.tvGasFee);

        setTransferable();

        layoutSpeedBest = findViewById(R.id.layoutSpeedBest);
        RxView.clicks(layoutSpeedBest).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    resetSelect(layoutSpeedBest, ivSpeedBest);
                });
        tvSpeedBestValue = findViewById(R.id.tvSpeedBestValue);
        ivSpeedBest = findViewById(R.id.ivSpeedBest);

        layoutSpeedFast = findViewById(R.id.layoutSpeedFast);
        RxView.clicks(layoutSpeedFast).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    resetSelect(layoutSpeedFast, ivSpeedFast);
                });
        tvSpeedFastValue = findViewById(R.id.tvSpeedFastValue);
        ivSpeedFast = findViewById(R.id.ivSpeedFast);

        layoutSpeedNormal = findViewById(R.id.layoutSpeedNormal);
        RxView.clicks(layoutSpeedNormal).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    resetSelect(layoutSpeedNormal, ivSpeedNormal);
                });

        tvSpeedNormalValue = findViewById(R.id.tvSpeedNormalValue);
        ivSpeedNormal = findViewById(R.id.ivSpeedNormal);
        //初始化默认选中“快的”
        resetSelect(layoutSpeedFast, ivSpeedFast);

        ImageView ivGasFeeDetail = findViewById(R.id.ivGasFeeDetail);
        TextView tvGasFeeSetting = findViewById(R.id.tvGasFeeSetting);
        RxView.clicks(tvGasFeeSetting).throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    if (layoutGasSetting.getVisibility() == View.GONE) {
                        ivGasFeeDetail.setRotation(0);
                        edGasLimit.setText(gasLimit.toString());
                        if (layoutSpeedBest.isSelected()) {
                            edGasPrice.setText(gasBean.getFastGasPrice().toPlainString());
                        } else if (layoutSpeedFast.isSelected()) {
                            edGasPrice.setText(gasBean.getProposeGasPrice().toPlainString());
                        } else if (layoutSpeedNormal.isSelected()) {
                            edGasPrice.setText(gasBean.getSafeGasPrice().toPlainString());
                        }
                        resetSelect(null, null);
                    } else {
                        ivGasFeeDetail.setRotation(-90);
                        edGasPrice.setText("");
                        edGasLimit.setText("");
                        resetSelect(layoutSpeedFast, ivSpeedFast);
                    }
                });
        edGasLimit = findViewById(R.id.edGasLimit);

        ECKeyPair ecKeyPair = null;
        try {
            ecKeyPair = Wallet.decrypt(SafePassword.get(), EthereumWalletUtils.getInstance().getWalletByChainCode(userWallet, chainCode).getWalletFile());
            privateKey = ecKeyPair.getPrivateKey().toString(16);
        } catch (CipherException e) {
            e.printStackTrace();
        }

        RxView.clicks(tvConfirm).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    //检查是否可转账状态0：可转账 1：不可转账
                    if(privateKey!=null){
                        mPresenter.getPrivateKey2(privateKey);
                    }
                });
        TextView tvTransferValue = findViewById(R.id.tvTransferValue);

        RxTextView.textChanges(edTransferCount).skipInitialValue()
                .debounce(200, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applySchedulers())
                .map(CharSequence::toString)
                .subscribe(t -> {
                    float value = 0;
                    try {
                        value = Float.parseFloat(t);
                    } catch (NumberFormatException ignored) {
                    }
                    String legalValue = TickerManager.getInstance()
                            .getLegalValueWithSymbol(tokensBean.getSymbol(), BigDecimal.valueOf(value), TickerManager.SCALE_DEFAULT);
                    tvTransferValue.setText(legalValue);
                });

        InitialValueObservable<CharSequence> observableAddress = RxTextView.textChanges(edAddress);
        InitialValueObservable<CharSequence> observableTransferCount = RxTextView.textChanges(edTransferCount);
        InitialValueObservable<CharSequence> observableGasPrice = RxTextView.textChanges(edGasPrice);
        InitialValueObservable<CharSequence> observableGasLimit = RxTextView.textChanges(edGasLimit);


        Observable.combineLatest(observableAddress, observableTransferCount, observableGasPrice, observableGasLimit, (s1, s2, s3, s4) -> {
            boolean condition = !(Kits.Empty.check(s1) || Kits.Empty.check(s2));
            if (layoutGasSetting.getVisibility() == View.GONE) {
                return condition;
            } else {
                return condition && !Kits.Empty.check(s3) && !Kits.Empty.check(s4);
            }
        }).subscribe(new ErrorHandleSubscriber<Boolean>(ComponentApplication.getRxErrorHandler()) {
            @Override
            public void onNext(@NotNull Boolean verify) {
                tvConfirm.setEnabled(verify);
            }
        });
        mPresenter.getGasDataSet(tokensBean.getChainCode());
        if (!tokensBean.isMain()) {
            mPresenter.getUnit(myAddress, tokensBean);
        }
    }

    //开始转账
    public void startTransfer(String jsonStr){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);
            Integer status = jsonObject.getInt("status");
            if(status == 0){
                String toAddress = Kits.Text.getText(edAddress);
                String transferCount = Kits.Text.getText(edTransferCount);
//                    if (!WalletUtils.isValidAddress(toAddress)) {
//                        ToastUtils.show(getString(R.string.error_address));
//                        return;
//                    }
                if (!AnyAddress.isValid(toAddress, CoinMyUtils.getCoinType(chainWallet.getChainCode()))) {
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
                if (chainWallet.getChainCode().equals(ChainCode.TRON)) {
                    gasFee = BigDecimal.ZERO;
                } else {
                    gasFee = calculateGasFee();
                }
                if (isBalanceEnough(gasFee)) {
                    TransferConfirmDialog.newInstance
                            (myAddress, toAddress, transferCount, tokensBean, gasFee)
                            .setOnEnsureClickedListener(() -> {
                                KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(this);
                                keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {
                                    @Override
                                    public void onPasswordComplete(String passWord) {
                                        if (SafePassword.isEquals(passWord)) {
                                            if (tokensBean.isMain()) {
                                                transMainCoin(toAddress, transferCount);
                                            } else {
                                                transToken(toAddress, transferCount);
                                            }
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
            }else{
                ToastUtils.show(R.string.connect_service);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setTransferable() {
        BigDecimal bigDecimal = BalanceManager.getInstance().get(userWallet, tokensBean);
        tvTransferable.setText(MessageFormat.format("{0}{1}",
                TickerManager.getInstance().getDecimalSymbolCount(tokensBean.getSymbol(), bigDecimal)
                        .toPlainString(),
                tokensBean.getSymbol()));
    }


    private void resetSelect(RadiusRelativeLayout selectLayout, ImageView selectImage) {

        layoutSpeedNormal.setSelected(false);
        layoutSpeedFast.setSelected(false);
        layoutSpeedBest.setSelected(false);

        ivSpeedNormal.setVisibility(View.GONE);
        ivSpeedFast.setVisibility(View.GONE);
        ivSpeedBest.setVisibility(View.GONE);

        if (selectLayout != null && selectImage != null) {
            layoutGasSetting.setVisibility(View.GONE);
            //用户选择
            selectLayout.setSelected(true);
            selectImage.setVisibility(View.VISIBLE);
            //layoutSpeedNormal.setEnabled(true);
            //layoutSpeedFast.setEnabled(true);
            //layoutSpeedBest.setEnabled(true);

            if (gasBean != null) {
                BigDecimal gasFeeNum = calculateGasFee();
                String gasFeeUnit = TokenManager.getInstance().getMainSymbolByChainCode(tokensBean.getChainCode());
                tvGasFee.setText(MessageFormat.format("{0} {1} ≈ {2}",
                        gasFeeNum.setScale(6, RoundingMode.DOWN).toPlainString(),
                        gasFeeUnit,
                        TickerManager.getInstance().getLegalValueWithSymbol(gasFeeUnit, gasFeeNum, 4)));
            }

        } else {
            //用户手动输入Gas费用
            layoutGasSetting.setVisibility(View.VISIBLE);
            //layoutSpeedNormal.setEnabled(false);
            //layoutSpeedFast.setEnabled(false);
            //layoutSpeedBest.setEnabled(false);
        }
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

    /**
     * 执行以太坊的交易过程
     * <p>
     * //     * @param password     安全密码
     *
     * @param toAddress 获取用户输入的地址
     * @param amount    获取用户输入的金额:注意这里的单位是eth
     */
    public void transMainCoin(String toAddress, String amount) {
        showLoading();
        if (TextUtils.isEmpty(toAddress) || TextUtils.isEmpty(toAddress.trim())) {
            ToastUtils.show(R.string.toast_empty_trans_address);
        } else if (TextUtils.isEmpty(amount) || TextUtils.isEmpty(amount.trim())) {
            ToastUtils.show(R.string.toast_empty_trans_amount);
        } else {
            //将字符串转为为BigDecimal类型， 并将单位转化为wei
            BigDecimal amountWei = Convert.toWei(amount, Convert.Unit.ETHER);

            //获取当前钱包的地址
            //构建Transaction对象，未签名， 其实是RawTransaction对象
            //需要传入的四个参数:
            // 1. nonce，当前钱包的总交易次数
            // 2. gasPrice, 邮费
            // 3. gasLimit, 给定最大邮费，超出这个数额就放弃执行交易
            // 4. to, 转账到哪个地址
            // 5. value, 转账的金额
            ThreadManager.getIO().execute(() -> {

                try {
                    //获取第一个参数nonce
                    BigInteger nonce = web3j.ethGetTransactionCount(myAddress, DefaultBlockParameterName.LATEST).send().getTransactionCount();
                    //获取第二个参数gasPrice
                    //gasPrice = web3j.ethGasPrice().send().getGasPrice();
                    //第三个参数 gasLimit
                    //第四个参数和第五个参数由用户在界面输入

                    RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce,
                            Convert.toWei(gasPrice.toPlainString(), Convert.Unit.GWEI).toBigInteger(),
                            gasLimit,
                            toAddress,
                            amountWei.toBigInteger());

                    byte[] signData = TransactionEncoder.encode(rawTransaction, EthereumNetworkBase.getChainIdByChainCode(GlobalConstant.RPC_TYPE_MAIN, tokensBean.getChainCode()));
                    SignatureFromKey returnSig = signWithKeystore(signData, chainWallet.getWalletFile2());
                    Sign.SignatureData sigData = sigFromByteArray(returnSig.signature);
                    sigData = TransactionEncoder.createEip155SignatureData(sigData, EthereumNetworkBase.getChainIdByChainCode(GlobalConstant.RPC_TYPE_MAIN, tokensBean.getChainCode()));
                    returnSig.signature = encode(rawTransaction, sigData);

                    String hexStringAA = Numeric.toHexString(returnSig.signature);
                    EthSendTransaction raw = web3j.ethSendRawTransaction(hexStringAA).send();
                    if (raw.hasError()) {
                        throw new Exception(raw.getError().getMessage());
                    } else {
                        raw.getTransactionHash();
                        LogManage.e(Constants.LOG_STRING, "onSendEth: 本次交易的哈希值是： \n" + raw.getTransactionHash());
                        ToastUtils.show(getString(R.string.toast_sent_transfer));
                        if (!Kits.Empty.check(raw.getTransactionHash())) {
                            TransferPointBean transferPointBean = new TransferPointBean();
                            transferPointBean.setTxId(raw.getTransactionHash());
                            transferPointBean.setChainCode(chainWallet.getChainCode());
                            transferPointBean.setAddress(myAddress);
                            mPresenter.submitTransfer(transferPointBean);
                        }
                        generateTrans(toAddress, amount, raw.getTransactionHash());
                    }

                    hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.show(getString(R.string.toast_transfer_fail));
                    hideLoading();
                }
            });
        }
    }

    private static byte[] encode(RawTransaction rawTransaction, Sign.SignatureData signatureData) {
        List<RlpType> values = TransactionEncoder.asRlpValues(rawTransaction, signatureData);
        RlpList rlpList = new RlpList(values);
        return RlpEncoder.encode(rlpList);
    }

    public static Sign.SignatureData sigFromByteArray(byte[] sig) {
        if (sig.length < 64 || sig.length > 65) return null;

        byte subv = sig[64];
        if (subv < 27) subv += 27;

        byte[] subrRev = Arrays.copyOfRange(sig, 0, 32);
        byte[] subsRev = Arrays.copyOfRange(sig, 32, 64);

        BigInteger r = new BigInteger(1, subrRev);
        BigInteger s = new BigInteger(1, subsRev);

        return new Sign.SignatureData(subv, subrRev, subsRev);
    }

    private synchronized SignatureFromKey signWithKeystore(byte[] transactionBytes, WalletFile walletFile) {

        try {
            SignatureFromKey returnSig = new SignatureFromKey();
            Credentials credentials = WalletUtils.loadJsonCredentials(SafePassword.get(), JSON.toJSONString(walletFile));

            Sign.SignatureData signatureData = Sign.signMessage(transactionBytes, credentials.getEcKeyPair());

            returnSig.signature = bytesFromSignature(signatureData);
            return returnSig;
        } catch (Exception ignored) {
            return null;
        }
    }

    public static byte[] bytesFromSignature(Sign.SignatureData signature) {
        byte[] sigBytes = new byte[65];
        Arrays.fill(sigBytes, (byte) 0);

        try {
            System.arraycopy(signature.getR(), 0, sigBytes, 0, 32);
            System.arraycopy(signature.getS(), 0, sigBytes, 32, 32);
            System.arraycopy(signature.getV(), 0, sigBytes, 64, 1);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return sigBytes;
    }

    private void generateTrans(String toAddress, String amount, String transactionHash) {

        TransferRecordBean.DataBean localPendding = new TransferRecordBean.DataBean();
        localPendding.setTxId(transactionHash);
        localPendding.setToken(tokensBean.getSymbol());//转的币

        localPendding.setFromAddr(myAddress);
        localPendding.setToAddr(toAddress);

        localPendding.setAmount(new BigDecimal(amount));

        localPendding.setGasLimit(new BigDecimal(gasLimit.toString()));
        localPendding.setGasPrice(new BigDecimal(gasPrice.toString()));

        localPendding.setGasUsed(BigDecimal.ZERO);
        localPendding.setConvertGasUsed(BigDecimal.ZERO);

        String gasFeeUnit = TokenManager.getInstance().getMainSymbolByChainCode(tokensBean.getChainCode());
        localPendding.setFeeToken(gasFeeUnit);//主链币

        localPendding.setTimeStamp(System.currentTimeMillis() / 1000);
        localPendding.setStatusType(TransactionStatus.PENDING);

        PenddingManager.getInstance().addPendding(localPendding, tokensBean);

        String fr = getIntent().getStringExtra(Constants.KEY_FR);
        if (fr.equals(TransferETHActivity.FR_WALLET)) {
            TransferRecordActivity.start(this, tokensBean);
        }
        finish();
    }

    /**
     * 执行Token的转账交易
     * <p>
     * //     * @param password  安全密码
     *
     * @param toAddress 获取用户输入的地址
     * @param amount    获取用户输入的金额
     */
    public void transToken(String toAddress, String amount) {
        showLoading();
        //校验用户输入是否为空
        if (TextUtils.isEmpty(toAddress) || TextUtils.isEmpty(toAddress.trim())) {
            ToastUtils.show(getString(R.string.toast_empty_trans_address));
        } else if (TextUtils.isEmpty(amount) || TextUtils.isEmpty(amount.trim())) {
            ToastUtils.show(getString(R.string.toast_empty_trans_amount));
        } else {
            ThreadManager.getIO().execute(() -> {
                try {
                    //获取钱包根节点公私钥
                    ECKeyPair ecKeyPair = Wallet.decrypt(SafePassword.get(), chainWallet.getWalletFile2());
                    //根据密钥对生成证书
                    Credentials credentials = Credentials.create(ecKeyPair);
                    BigDecimal amountInMWEI = new BigDecimal(amount).multiply(BigDecimal.TEN.pow(Integer.parseInt(unit)));
                    //创建智能合约的函数对象, 3个参数分别是：
                    Function function = new Function(
                            "transfer",//参数1：  智能合约里面的函数名称
                            //参数2： 智能合约要执行的这个函数要传入的参数列表
                            Arrays.asList(new Address(toAddress), new Uint256(amountInMWEI.toBigInteger())),
                            //参数3： 智能合约要执行的这个函数的返回值的类型
                            Collections.singletonList(new TypeReference<Type>() {
                            }));
                    String encodedFunction = FunctionEncoder.encode(function);
                    //创建未签名的交易对象, 需要传入5个参数:
                    // nonce,
                    // gasPrice,
                    // gasLimit,
                    // toAddress,
                    // encode
                    // 第一个参数nonce, 也就是钱包的历史交易次数
                    BigInteger nonce = web3j.ethGetTransactionCount(myAddress, DefaultBlockParameterName.LATEST).send().getTransactionCount();
                    //第二个参数gasPrice, 邮费
                    //gasPrice = web3j.ethGasPrice().send().getGasPrice();
                    //第三个参数gasLimit, 最大邮费数量
                    //第四个参数to, 说明要把这笔金额转到哪里去
                    //因为要执行的是智能合约里面的函数,所以这里传入智能合约地址,表示由智能合约来操作这笔金额
                    //第五个参数encode,传入进过encode的函数对象,也就是智能合约里面要执行的函数
                    //生成未经过签名的ranTransaction对象
                    RawTransaction rawTransaction = EthereumWalletUtils.getInstance().getTransaction(
                            nonce,
                            gasPrice.toString(),
                            gasLimit,
                            tokensBean.getContractId(),
                            encodedFunction);
//                    //使用证书对交易对象进行消息摘要运算,也就是签名,并返回签名的字节数组
//                    byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
//                    //将签名的字节数组转化成十六进制的字符串
//                    String hexString = Numeric.toHexString(signMessage);


                    byte[] signData = TransactionEncoder.encode(rawTransaction, EthereumNetworkBase.getChainIdByChainCode(GlobalConstant.RPC_TYPE_MAIN, tokensBean.getChainCode()));
                    SignatureFromKey returnSig = signWithKeystore(signData, chainWallet.getWalletFile2());
                    Sign.SignatureData sigData = sigFromByteArray(returnSig.signature);
                    sigData = TransactionEncoder.createEip155SignatureData(sigData, EthereumNetworkBase.getChainIdByChainCode(GlobalConstant.RPC_TYPE_MAIN, tokensBean.getChainCode()));
                    returnSig.signature = encode(rawTransaction, sigData);

                    String hexStringAA = Numeric.toHexString(returnSig.signature);
                    EthSendTransaction raw = web3j.ethSendRawTransaction(hexStringAA).send();
                    if (raw.hasError()) {
                        ToastUtils.show(raw.getError().getMessage());
//                        throw new Exception(raw.getError().getMessage());
                    } else {
                        raw.getTransactionHash();
                        LogManage.e(Constants.LOG_STRING, "onSendEth: 本次交易的哈希值是： \n" + raw.getTransactionHash());
                        ToastUtils.show(getString(R.string.toast_sent_transfer));
                        generateTrans(toAddress, amount, raw.getTransactionHash());
                    }
//!!!!!!!!!!----下面注释不要删除------------------！！！！！！！！！！！！！！！！！
                    //使用签名,进行交易,并返回本次交易的哈希值
//                    String transactionHash = web3j.ethSendRawTransaction(hexString).send().getTransactionHash();
//                    LogManage.e(Constants.LOG_STRING, "onSendToken:  本次Token交易的哈希值是： " + transactionHash);
//                    ToastUtils.show(getString(R.string.toast_sent_transfer));
//                    generateTrans(toAddress, amount, transactionHash);
                    hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.show(getString(R.string.toast_transfer_fail));
                    hideLoading();
                }
            });
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void getGasDataSet(GasBean gasBean) {
        String test = "{\"fastGasPrice\":5.202,\"gasLimit\":21000,\"proposeGasPrice\":5.1,\"safeGasPrice\":5}";
        if (Kits.Empty.check(gasBean)) {
            GasBean gasBeanTest = new GasBean();
            gasBeanTest.setFastGasPrice(new BigDecimal("1.2"));
            gasBeanTest.setProposeGasPrice(new BigDecimal("1.1"));
            gasBeanTest.setSafeGasPrice(new BigDecimal("1.0"));
            gasBeanTest.setGasLimit(new BigDecimal("21000"));
            gasBean = gasBeanTest;
        } else {
            LogManage.e(Constants.LOG_STRING + JSON.toJSONString(gasBean));
        }
        this.gasBean = gasBean;
        tvSpeedBestValue.setText(MessageFormat.format("{0}Gwei",
                gasBean.getFastGasPrice().toPlainString()));

        tvSpeedFastValue.setText(MessageFormat.format("{0}Gwei",
                gasBean.getProposeGasPrice().toPlainString()));

        tvSpeedNormalValue.setText(MessageFormat.format("{0}Gwei",
                gasBean.getSafeGasPrice().toPlainString()));

        gasPrice = gasBean.getProposeGasPrice();
        if (GlobalConstant.DEBUG_MODEL) {
            gasLimit = new BigInteger("100000");
        } else {
            if (tokensBean.isMain()) {
                gasLimit = new BigInteger(String.valueOf(gasBean.getGasLimit()));
            } else {
                gasLimit = new BigInteger("100000");
            }
        }

        BigDecimal gasFeeNum = calculateGasFee();
        String gasFeeUnit = TokenManager.getInstance().getMainSymbolByChainCode(tokensBean.getChainCode());
        tvGasFee.setText(MessageFormat.format("{0} {1} ≈ {2}",
                gasFeeNum.setScale(6, RoundingMode.DOWN).toPlainString(),
                gasFeeUnit,
                TickerManager.getInstance().getLegalValueWithSymbol(gasFeeUnit, gasFeeNum, 4)));
    }

    @Override
    public void setUnit(String unit) {
        this.unit = unit;
    }


    public String getPrivateKeyToAddress(String privateKey) {
        String address = Keys.toChecksumAddress(Keys.getAddress(ECKeyPair.create(Numeric.toBigInt(privateKey))));
        byte[] decode = Hex.decode(address.replace(Constants.PREFIX_16, "41"));
        return Base58Check.bytesToBase58(decode);
    }

    public String fromHexAddress(String address) {
        return AddressConver.encode58Check(ByteArray.fromHexString(address));
    }


    private boolean isBalanceEnough(BigDecimal fee) {
        boolean isEnough = true;
        String amountString = Kits.Text.getText(edTransferCount);
        BigDecimal amount = new BigDecimal(amountString);

        if (tokensBean.isMain()) {
            if (BalanceManager.getInstance().get(userWallet, tokensBean).compareTo(amount.add(fee)) == -1) {
                ToastUtils.show(getString(R.string.toast_balance_not_enough));
                isEnough = false;
            }
        } else {
            String mainSymbol = TokenManager.getInstance().getMainSymbolByChainCode(tokensBean.getChainCode());
            //balanceMain：token的主链上的币
            BigDecimal balanceMain = BalanceManager.getInstance()
                    .get(userWallet, tokensBean.getChainCode(), "");
            if (balanceMain.compareTo(fee) == -1) {//主币余额不足
                ToastUtils.show(tokensBean.getChainCode() + getString(R.string.toast_fee_not_enough));
                isEnough = false;
            } else if (BalanceManager.getInstance().get(userWallet, tokensBean).compareTo(amount) == -1) {//代币余额小于转账数量
                ToastUtils.show(tokensBean.getSymbol() + getString(R.string.toast_token_not_enough));
                isEnough = false;
            }
        }

        return isEnough;
    }


    /**
     * 计算gasFee
     */
    private BigDecimal calculateGasFee() {
        BigDecimal gasPriceWei = Convert.toWei(getGasPrice(), Convert.Unit.GWEI);
        String gasFee = gasPriceWei.multiply(getGasLimit()).toPlainString();
        return Convert.fromWei(gasFee, Convert.Unit.ETHER);
    }

    /**
     * 获取gasPrice
     */
    private String getGasPrice() {
        if (layoutGasSetting.getVisibility() == View.VISIBLE) {
            return Kits.Text.getText(edGasPrice);
        } else {
            if (layoutSpeedBest.isSelected()) {
                return gasBean.getFastGasPrice().toPlainString();
            } else if (layoutSpeedFast.isSelected()) {
                return gasBean.getProposeGasPrice().toPlainString();
            } else if (layoutSpeedNormal.isSelected()) {
                return gasBean.getSafeGasPrice().toPlainString();
            } else {//一般不会走这里
                return gasBean.getFastGasPrice().toPlainString();
            }
        }
    }

    /**
     * 获取gasLimit
     */
    private BigDecimal getGasLimit() {
        if (layoutGasSetting.getVisibility() == View.VISIBLE) {
            return new BigDecimal(Kits.Text.getText(edGasLimit));
        } else {
            return new BigDecimal(gasLimit.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != tvTransferable) {
            setTransferable();
        }
    }

    @Override
    public void showLoading() {

        DialogLoading.getInstance().showDialog(this);
    }

    @Override
    public void hideLoading() {
        DialogLoading.getInstance().closeDialog();
    }
}