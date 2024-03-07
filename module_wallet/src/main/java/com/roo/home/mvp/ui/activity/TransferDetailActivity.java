package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.mine.TransferRecordBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.annotation.TransactionStatus;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TickerManager;
import com.roo.home.R;
import com.roo.home.di.component.DaggerTransferDetailComponent;
import com.roo.home.mvp.contract.TransferDetailContract;
import com.roo.home.mvp.presenter.TransferDetailPresenter;
import com.roo.home.mvp.utils.Base58Check;
import com.roo.home.mvp.utils.UrlDisplayManager;
import com.roo.router.Router;
import com.subgraph.orchid.encoders.Hex;

import org.simple.eventbus.EventBus;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 转账详情
 */
public class TransferDetailActivity extends BaseActivity<TransferDetailPresenter> implements TransferDetailContract.View {

    public static void start(Context context, TransferRecordBean.DataBean recordBean, String chainCode) {
        start(context, recordBean, chainCode, false);
    }

    public static void start(Context context, TransferRecordBean.DataBean recordBean,
                             String chainCode,
                             boolean isSideIn) {
        Router.newIntent(context)
                .to(TransferDetailActivity.class)
                .putString(Constants.KEY_MSG, chainCode)
                .putParcelable(Constants.KEY_DEFAULT, recordBean)
                .putBoolean(Constants.KEY_FR, isSideIn)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTransferDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_transfer_detail;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String chainCode = getIntent().getStringExtra(Constants.KEY_MSG);
        ViewHelper.initTitleBar("", this);
        TransferRecordBean.DataBean item = getIntent().getParcelableExtra(Constants.KEY_DEFAULT);

        TextView tvTransferCount = findViewById(R.id.tvTransferCount);
        TextView tvGasFee = findViewById(R.id.tvGasFee);
        TextView tvPayAddress = findViewById(R.id.tvPayAddress);
        TextView tvCashierAddress = findViewById(R.id.tvCashierAddress);
        TextView tvHash = findViewById(R.id.tvHash);
        TextView tvDesc = findViewById(R.id.tvDesc);
        tvDesc.setOnClickListener(v -> {
            UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);
            ArrayList<LinkTokenBean.NodesBean> nodes = userWallet.getNodes();
            if (Kits.Empty.check(nodes)) {
                return;
            }
            for (LinkTokenBean.NodesBean node : nodes) {
                if (node.getChainCode().equals(chainCode)) {
//                    String format = MessageFormat.format("{0}tx/{1}", node.getBrowserUrl(), item.getTxId());
                    String format = new UrlDisplayManager().display(chainCode) + item.getTxId();
                    LogManage.e("format--->" + format);
                    HashMap<String, Object> param = new HashMap<>();
                    param.put(Constants.KEY_URL, format);

                    EventBus.getDefault().post(param, EventBusTag.GOTO_WEBVIEW);
                    break;
                }
            }
        });

        ImageView ivTransferStatus = findViewById(R.id.ivTransferStatus);
        LottieAnimationView animationView = findViewById(R.id.animationView);
        ImageView ivShadow = findViewById(R.id.ivShadow);
        TextView tvTime = findViewById(R.id.tvTime);
        TextView tvTransferStatus = findViewById(R.id.tvTransferStatus);

        tvHash.setText(item.getBlockHash());
        tvPayAddress.setText(item.getFromAddr());
        RxView.clicks(tvPayAddress).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    GlobalUtils.copyToClipboard(item.getFromAddr(), this);
                    ToastUtils.show(getString(R.string.copy_success));
                });


        tvCashierAddress.setText(item.getToAddr());
        RxView.clicks(tvCashierAddress).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    GlobalUtils.copyToClipboard(item.getToAddr(), this);
                    ToastUtils.show(getString(R.string.copy_success));
                });

        RxView.clicks(tvHash).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    GlobalUtils.copyToClipboard(item.getBlockHash(), this);
                    ToastUtils.show(getString(R.string.copy_success));
                });
        tvGasFee.setText(MessageFormat.format("{0} {1} ≈ {2}",
                item.getConvertGasUsed().toPlainString(),
                item.getFeeToken(),
                TickerManager.getInstance().getLegalValueWithSymbol(item.getFeeToken(),
                        item.getConvertGasUsed(), 4)));

        BigDecimal amount = item.getAmount();

        if (amount.floatValue() > 1000) {
            DecimalFormat format = new DecimalFormat("#,###");
            tvTransferCount.setText(MessageFormat.format(isSideIn(item, chainCode) ?
                            "+{0} {1}" : "-{0} {1}", format.format(amount),
                    item.getToken()));
        } else {
            tvTransferCount.setText(MessageFormat.format(isSideIn(item, chainCode) ?
                            "+{0} {1}" : "-{0} {1}", amount.setScale(8, RoundingMode.DOWN).toPlainString(),
                    item.getToken()));
        }
        tvTime.setText(Kits.Date.getMdyhms(item.getTimeStamp() * 1000));

        switch (item.getStatusType()) {
            case TransactionStatus.PENDING:
                animationView.setVisibility(View.VISIBLE);
                ivShadow.setVisibility(View.VISIBLE);
                ivTransferStatus.setVisibility(View.GONE);
                tvTransferStatus.setText(getString(R.string.state_pending));
                break;
            case TransactionStatus.IN_BLOCK:
                animationView.setVisibility(View.VISIBLE);
                ivShadow.setVisibility(View.VISIBLE);
                ivTransferStatus.setVisibility(View.GONE);
                tvTransferStatus.setText(getString(R.string.state_packeting));
                break;
            case TransactionStatus.FAIL:
                animationView.setVisibility(View.GONE);
                ivShadow.setVisibility(View.GONE);
                ivTransferStatus.setVisibility(View.VISIBLE);
                ivTransferStatus.setImageResource(R.drawable.ic_home_transfer_detail_fail);
                tvTransferStatus.setText(getString(R.string.state_transfer_failed));
                break;
            case TransactionStatus.CONFIRMED:
                animationView.setVisibility(View.GONE);
                ivShadow.setVisibility(View.GONE);
                ivTransferStatus.setVisibility(View.VISIBLE);
                ivTransferStatus.setImageResource(R.drawable.ic_home_transfer_detail_success);
                if (isSideIn(item, chainCode)) {
                    tvTransferStatus.setText(getString(R.string.state_succ_cashier));
                } else {
                    tvTransferStatus.setText(getString(R.string.state_succ_transfer));
                }
                break;
        }
    }
    public String getPrivateKeyToAddress(String privateKey) {
        String address = Keys.toChecksumAddress(Keys.getAddress(ECKeyPair.create(Numeric.toBigInt(privateKey))));
        byte[] decode = Hex.decode(address.replace(Constants.PREFIX_16, "41"));
        return Base58Check.bytesToBase58(decode);
    }
    /*是否是转入*/
    public boolean isSideIn(TransferRecordBean.DataBean item, String chainCode) {
        if (getIntent().getBooleanExtra(Constants.KEY_FR, false)) {
            return true;
        }
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);
        UserWallet.ChainWallet chainWallet = EthereumWalletUtils.getInstance().getWalletByChainCode(userWallet, chainCode);
        String address = "";
        switch (chainCode) {
            case ChainCode.TRON:
                if (userWallet.getPrivateKey2() != null) {
                    address = getPrivateKeyToAddress(userWallet.getPrivateKey2());
                } else {
                    HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
                    address = hdWallet.getAddressForCoin(CoinType.TRON);
                }

                break;
            default:
                address = Constants.PREFIX_16 + chainWallet.getWalletFile2().getAddress();
                break;
        }


        return address.equalsIgnoreCase(item.getToAddr());
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

}