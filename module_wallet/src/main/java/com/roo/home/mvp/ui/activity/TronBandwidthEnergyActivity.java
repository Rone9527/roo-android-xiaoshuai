package com.roo.home.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aries.ui.view.radius.RadiusEditText;
import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.mine.AddressBookBean;
import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.TransactionResult;
import com.core.domain.trade.BalanceBean;
import com.core.domain.wallet.FreezeBalanceBean;
import com.core.domain.wallet.TronAccountInfo;
import com.core.domain.wallet.TronAccountResource;
import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.component.Mine;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.ui.dialog.TronFreezeConfirmDialog;
import com.roo.core.ui.widget.DialogLoading;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.BalanceManager;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TickerManager;
import com.roo.core.utils.utils.TimeUtils;
import com.roo.home.R;
import com.roo.home.di.component.DaggerTronBandwidthEnergyComponent;
import com.roo.home.mvp.contract.TronBandwidthEnergyContract;
import com.roo.home.mvp.presenter.TronBandwidthEnergyPresenter;
import com.roo.home.mvp.utils.BalanceUtils;
import com.roo.home.mvp.utils.Base58Check;
import com.roo.home.mvp.utils.ByteArray;
import com.roo.home.mvp.utils.HexUtil;
import com.roo.router.Router;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.subgraph.orchid.encoders.Hex;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.xyzlf.custom.keyboardlib.SimpleKeyboardListener;

import org.bitcoinj.core.Sha256Hash;
import org.json.JSONException;
import org.json.JSONObject;
import org.tron.common.crypto.ECKey;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import wallet.core.jni.AnyAddress;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.roo.home.mvp.utils.BalanceUtils.baseToSubunit;

public class TronBandwidthEnergyActivity extends BaseActivity<TronBandwidthEnergyPresenter> implements TronBandwidthEnergyContract.View, RadioGroup.OnCheckedChangeListener {
    private String myAddress;//我的地址
    private TextView tvBrandWith;
    private TextView tvEnergy;
    private UserWallet userWallet;
    private TextView tvBwp;
    private TextView tvFreeNetUsed;
    private ProgressBar pbBrandWidth;
    private TextView tvEnergyPrice;
    private TextView tvBalance;
    private RadiusEditText edTransferCount;
    private TextView tvCount;
    private boolean isChosenBrandWidth = true;//默认选中带宽
    private float mBrandPrice = 0;
    private float bEnergyPrice;
    private TextView tvConfirm;
    private RadiusEditText edAddress;
    private RadioGroup rg;
    private String resourceType;
    private String amount;
    private TextView tvFrozenBalanceEnergy;
    private TextView tvFrozenBalance;
    private RadioGroup rgFreeze;
    private TextView tvFrozenBalanceOneself;
    private TextView tvFrozenBalanceOneselfEnergy;
    private TextView tvThawTimeBrandWidth;//带宽可解冻时间
    private long expireTime;
    private TextView tvEnergyOther;
    private TextView tvFreeEnergyUsed;
    private SmartRefreshLayout smartRefreshLayout;
    private LinkTokenBean.TokensBean tokensBean;
    private TextView tvThawTimeEnergy;
    private ProgressBar pbEnergy;
    private TextView tvFrozenBalanceOtherBrand;

    public static void start(Context context, LinkTokenBean.TokensBean tokensBean) {
        Router.newIntent(context)
                .putParcelable(Constants.KEY_DEFAULT, tokensBean)
                .to(TronBandwidthEnergyActivity.class)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTronBandwidthEnergyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_tron_bandwidth_energy; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public String getPrivateKeyToAddress(String privateKey) {
        String address = Keys.toChecksumAddress(Keys.getAddress(ECKeyPair.create(Numeric.toBigInt(privateKey))));
        byte[] decode = Hex.decode(address.replace(Constants.PREFIX_16, "41"));
        return Base58Check.bytesToBase58(decode);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViewById();
        initListener();
        tokensBean = getIntent().getParcelableExtra(Constants.KEY_DEFAULT);
        userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);
        if (userWallet.getPrivateKey2() != null) {
            myAddress = getPrivateKeyToAddress(userWallet.getPrivateKey2());
        } else {
            HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
            myAddress = hdWallet.getAddressForCoin(CoinType.TRON);
        }
        BigDecimal amount = BalanceManager.getInstance().get(userWallet, tokensBean);
        BigDecimal coinAsset = TickerManager.getInstance().getDecimalSymbolCount(
                tokensBean.getSymbol(), amount
        );
        tvBalance.setText(String.format(getString(R.string.tv_balance_num_trx), coinAsset.toPlainString()));
        String hexString = ByteArray.toHexString(Base58Check.base58ToBytes(myAddress));
        LogManage.e("hexString----->" + hexString);

        loadData();

    }

    private void loadData() {
        String hexString = ByteArray.toHexString(Base58Check.base58ToBytes(myAddress));
        mPresenter.getAccountResource(hexString);
        mPresenter.getBalance();
        mPresenter.getTronAccountInfo(hexString);
    }

    private void initViewById() {
        TitleBarView mTitleBarView = ViewHelper.initTitleBar(getString(R.string.bandwidth_energy), this);
        rg = (RadioGroup) findViewById(R.id.rg_my_or_other);
        rg.setOnCheckedChangeListener(this);
        rgFreeze = (RadioGroup) findViewById(R.id.rg_freeze);
        rgFreeze.setOnCheckedChangeListener(this);
        tvBwp = findViewById(R.id.tv_bwp);
        tvBrandWith = findViewById(R.id.tv_brand_with);
        tvEnergy = findViewById(R.id.tv_energy);
        TextView tvFreezeTips = findViewById(R.id.tv_freeze_tips);
        String html = getString(R.string.tv_freeze_tips);
        tvFreezeTips.setText(Html.fromHtml(html));
        tvFreeNetUsed = findViewById(R.id.tv_freeNetUsed);
        pbBrandWidth = findViewById(R.id.pb_brandWidth);
        tvEnergyPrice = findViewById(R.id.tv_energy_price);
        tvBalance = findViewById(R.id.tv_balance);
        edTransferCount = findViewById(R.id.edTransferCount);
        tvCount = findViewById(R.id.tv_brandWidth_Energy_count);
        tvConfirm = findViewById(R.id.tvConfirm);
        edAddress = findViewById(R.id.edAddress);
        tvFrozenBalance = findViewById(R.id.tv_frozen_balance);
        tvFrozenBalanceEnergy = findViewById(R.id.tv_frozen_balance_energy);

        tvFrozenBalanceOneself = findViewById(R.id.tv_frozen_balance_oneself);
        tvThawTimeBrandWidth = findViewById(R.id.tv_thaw_time_brandWidth);

        tvFrozenBalanceOneselfEnergy = findViewById(R.id.tv_frozen_balance_oneself_energy);

        tvEnergyOther = findViewById(R.id.tv_energy_other);
        tvFreeEnergyUsed = findViewById(R.id.tv_freeEnergyUsed);

        tvThawTimeEnergy = findViewById(R.id.tv_thaw_time_energy);
        smartRefreshLayout = findViewById(R.id.refreshLayout);

        pbEnergy = findViewById(R.id.pb_energy);//能量消耗进度
        //他人冻结带宽
        tvFrozenBalanceOtherBrand = findViewById(R.id.tv_frozen_balance_other_brand);
    }

    private void initListener() {
        tvBrandWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBrandWith.setBackgroundResource(R.drawable.rectangle_bg_blue_left_radius_4);
                tvBrandWith.setTextColor(getResources().getColor(R.color.white, null));

                tvEnergy.setBackgroundResource(R.drawable.rectangle_bg_gray_right_radius_4);
                tvEnergy.setTextColor(getResources().getColor(R.color.text_color_dark_normal, null));
                isChosenBrandWidth = true;
                refreshUI();
            }
        });
        tvEnergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBrandWith.setBackgroundResource(R.drawable.rectangle_bg_gray_left_radius_4);
                tvBrandWith.setTextColor(getResources().getColor(R.color.text_color_dark_normal, null));

                tvEnergy.setBackgroundResource(R.drawable.rectangle_bg_blue_right_radius_4);
                tvEnergy.setTextColor(getResources().getColor(R.color.white, null));
                isChosenBrandWidth = false;
                refreshUI();
            }
        });

        RxTextView.textChanges(edTransferCount).skipInitialValue()
                .debounce(200, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applySchedulers())
                .map(CharSequence::toString)
                .subscribe(t -> {
                    amount = Kits.Text.getText(edTransferCount);
                    refreshUI();
                });
        RxTextView.textChanges(edAddress).skipInitialValue()
                .debounce(200, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applySchedulers())
                .map(CharSequence::toString)
                .subscribe(t -> {
                    refreshUI();
                });

        RxView.clicks(findViewById(R.id.ivAddressBook)).throttleFirst(1, TimeUnit.SECONDS)
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
        RxView.clicks(tvConfirm).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {

                    int checkedRadioButtonId = rgFreeze.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == R.id.rb_freeze) {//判断当前状态（冻结 解冻）
                        freezeBalance();
                    } else {
                        UnfreezeBalance();
                    }
                });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                smartRefreshLayout.finishRefresh();
                loadData();
            }
        });
    }

    private void UnfreezeBalance() {

        Map map = new HashMap<>();
        map.put("owner_address", ByteArray.toHexString(Base58Check.base58ToBytes(myAddress)));
        map.put("resource", isChosenBrandWidth ? "BANDWIDTH" : "ENERGY");

        int checkedRadioButtonId = rg.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.rb_other) {
            String toAddress = Kits.Text.getText(edAddress);
            if (!myAddress.equalsIgnoreCase(toAddress)) {//自己和他人不是同一地址,才执行相应逻辑
                map.put("receiver_address", ByteArray.toHexString(Base58Check.base58ToBytes(toAddress)));
            }
        }
        Gson gson = new Gson();
        String postInfoStr = gson.toJson(map);
        LogManage.d("UnfreezeBalance---->" + postInfoStr);
        mPresenter.unFreezeBalance(postInfoStr);
    }

    private void freezeBalance() {

        amount = Kits.Text.getText(edTransferCount);
        BigInteger use = null;
        if (!TextUtils.isEmpty(amount)) {
            use = baseToSubunit(amount, 6);
        } else {
            use = new BigInteger("1");
        }

        Map map = new HashMap<>();
        map.put("owner_address", ByteArray.toHexString(Base58Check.base58ToBytes(myAddress)));
        map.put("resource", isChosenBrandWidth ? "BANDWIDTH" : "ENERGY");
        map.put("frozen_duration", 3);
        map.put("frozen_balance", use.intValue());
        int checkedRadioButtonId = rg.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.rb_other) {//他人
            String toAddress = Kits.Text.getText(edAddress);
            if (!myAddress.equalsIgnoreCase(toAddress)) {//自己和他人不是同一地址,才执行相应逻辑
                map.put("receiver_address", ByteArray.toHexString(Base58Check.base58ToBytes(toAddress)));
            }

        }
        Gson gson = new Gson();
        String postInfoStr = gson.toJson(map);
        LogManage.d("postInfoStr---->" + postInfoStr);

        mPresenter.freezeBalance(postInfoStr);

    }

    @SuppressLint("SetTextI18n")
    private void refreshUI() {

        float v = 0;
        if (TextUtils.isEmpty(amount)) {

        } else {
            v = Float.valueOf(amount).intValue();
            LogManage.e("v---->" + v);
        }

        int checkedRadioButtonId = rgFreeze.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.rb_freeze) {//判断当前状态（冻结 解冻）
            if (isChosenBrandWidth) {//判断选中的是带宽还是能量
                tvCount.setText("≈ " + (v * mBrandPrice) + getResources().getString(R.string.bandwidth));
            } else {
                tvCount.setText("≈ " + (v * bEnergyPrice) + getResources().getString(R.string.energy));
            }

            int rgButtonId = rg.getCheckedRadioButtonId();
            if (rgButtonId == R.id.rb_oneself) {//自己
                if (!TextUtils.isEmpty(amount) && v >= 1) {
                    tvConfirm.setEnabled(true);
                } else {
                    tvConfirm.setEnabled(false);
                }
            } else {//他人
                if (v >= 1 && AnyAddress.isValid(Kits.Text.getText(edAddress), CoinType.TRON)) {
                    tvConfirm.setEnabled(true);
                } else {
                    tvConfirm.setEnabled(false);
                }
            }


        } else {//解冻

            int rgButtonId = rg.getCheckedRadioButtonId();
            if (rgButtonId == R.id.rb_oneself) {//自己
                tvConfirm.setEnabled(true);
            } else {//他人
                if (AnyAddress.isValid(Kits.Text.getText(edAddress), CoinType.TRON)) {
                    tvConfirm.setEnabled(true);
                } else {
                    tvConfirm.setEnabled(false);
                }
            }
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
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.rb_oneself) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(1);
            radioButton.setTextColor(getResources().getColor(R.color.white_color_assist_2, null));

            RadioButton radioButton0 = (RadioButton) radioGroup.getChildAt(0);
            radioButton0.setTextColor(getResources().getColor(R.color.blue_color, null));

            findViewById(R.id.layoutRecipient).setVisibility(View.GONE);

            refreshUI();
        }
        if (checkedId == R.id.rb_other) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(1);
            radioButton.setTextColor(getResources().getColor(R.color.blue_color, null));

            RadioButton radioButton0 = (RadioButton) radioGroup.getChildAt(0);
            radioButton0.setTextColor(getResources().getColor(R.color.white_color_assist_2, null));

            findViewById(R.id.layoutRecipient).setVisibility(View.VISIBLE);

            refreshUI();
        }


        if (checkedId == R.id.rb_freeze) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(0);
            setDrawableLeft(radioButton, R.drawable.ic_check);

            RadioButton radioButton1 = (RadioButton) radioGroup.getChildAt(1);
            setDrawableLeft(radioButton1, R.drawable.ic_un_check);

            findViewById(R.id.ll_amount).setVisibility(View.VISIBLE);
            refreshUI();
        }
        if (checkedId == R.id.rb_thaw) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(0);
            setDrawableLeft(radioButton, R.drawable.ic_un_check);

            RadioButton radioButton1 = (RadioButton) radioGroup.getChildAt(1);
            setDrawableLeft(radioButton1, R.drawable.ic_check);

            findViewById(R.id.ll_amount).setVisibility(View.GONE);
            refreshUI();
        }
    }


    /**
     * 设置textview 的drawable属性
     *
     * @param attention
     * @param drawableId
     */
    private void setDrawableLeft(RadioButton attention, int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        attention.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    public void getAccountResourceSuccess(TronAccountResource t) {
        LogManage.e(t.toString());

        BigDecimal b = new BigDecimal(1.0f / t.TotalNetWeight * t.TotalNetLimit);
        mBrandPrice = b.setScale(5, BigDecimal.ROUND_HALF_UP).floatValue();
        tvBwp.setText(mBrandPrice + " B/TRX");

        if (t.freeNetUsed == 0) {
            float f = new BigDecimal(t.freeNetLimit / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            tvFreeNetUsed.setText("剩余：" + f + "KB/" + f + "KB");
            pbBrandWidth.setProgress(100);
        } else {
            float fUse = new BigDecimal((t.freeNetLimit - t.freeNetUsed) / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            float fTotal = new BigDecimal(t.freeNetLimit / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            tvFreeNetUsed.setText("剩余：" + fUse + "KB/" + fTotal + "KB");
            LogManage.e("fUse / fTotal----" + (int) (fUse / fTotal * 100));
            pbBrandWidth.setProgress((int) (fUse / fTotal * 100));
        }


        //能量模块

        BigDecimal bEnergy = new BigDecimal(1.0f / t.TotalEnergyWeight * t.TotalEnergyLimit);
        bEnergyPrice = bEnergy.setScale(5, BigDecimal.ROUND_HALF_UP).floatValue();
        tvEnergyPrice.setText(bEnergyPrice + " μs/TRX");


        if (t.EnergyUsed == 0) {//没有消耗能量
            String format = String.format(getString(R.string.energy_used), t.EnergyLimit + "", t.EnergyLimit + "");
            tvFreeEnergyUsed.setText(format);
            pbEnergy.setProgress(100);
        } else {
            float fUse = t.EnergyUsed;
            float fTotal = t.EnergyLimit;
            LogManage.e("String.format / fTotal----" + (int) (fUse / fTotal * 100));
//            tvFreeEnergyUsed.setText("剩余：" + (fTotal-fUse) + "μs/" + fTotal + "μs");

            String format = String.format(getString(R.string.energy_used), (fTotal - fUse) + "", fTotal + "");
            tvFreeEnergyUsed.setText(format);
            pbEnergy.setProgress((int) ((fTotal - fUse) / fTotal * 100));
        }


    }

    @Override
    public void getBalanceSuccess(BaseResponse<List<BalanceBean>> t) {
        if (t != null) {
            if (t.getData().size() > 0) {
                BigDecimal availableBalance = t.getData().get(0).getAvailableBalance();
                tvBalance.setText(String.format(getString(R.string.tv_balance_num_trx), availableBalance.toPlainString()));
            }
        }

    }

    @Override
    public void getFreezeBalanceSuccess(ResponseBody responseBody) {
        hideLoading();
        String string = null;
        try {
            string = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            LogManage.e("string---->" + string);
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.has("Error")) {  //判断JSONObject是否包含含有retweeted_status属性值
                String Error = jsonObject.getString("Error");
                if (Error.contains("frozenBalance must be less than accountBalance")) {
                    ToastUtils.show("余额不足");
                }
            } else {
                FreezeBalanceBean t = new Gson().fromJson(string, FreezeBalanceBean.class);
                LogManage.e("FreezeBalanceBean---->" + t.toString());
                LogManage.e("字节数--》" + t.raw_data_hex.length());
                LogManage.e("字节数--》" + t.raw_data_hex.getBytes(StandardCharsets.UTF_8).length);
                resourceType = isChosenBrandWidth ? "带宽" : "能量";
                int checkedRadioButtonId = rg.getCheckedRadioButtonId();
                String recipientAccount = checkedRadioButtonId == R.id.rb_oneself ? "当前账户地址" : Kits.Text.getText(edAddress);
                TronFreezeConfirmDialog.newInstance(resourceType, recipientAccount, amount, t.raw_data_hex.length())
                        .setOnEnsureClickedListener(() -> {
                            KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(this);
                            keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {
                                @Override
                                public void onPasswordComplete(String passWord) {
                                    if (SafePassword.isEquals(passWord)) {
                                        freezeBalanceContract(t);
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

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void broadcastHexDataSet(TransactionResult t) {
        LogManage.e("t---->" + t.toString());
        hideLoading();
        if (t.result) {
            loadData();
            ToastUtils.show("广播成功");
            edTransferCount.setText("");
        }
    }

    @Override
    public void unFreezeBalanceSuccess(ResponseBody t) {

        try {
            String string = t.string();
            JSONObject jsonObject = new JSONObject(string);
            LogManage.e("t---" + string);
            if (jsonObject.has("Error")) {
                String Error = jsonObject.getString("Error");
                if (Error != null) {
                    if (Error.contains("It's not time to unfreeze")) {
                        ToastUtils.show("未到解冻时间");
                    }
                    if (Error.contains("no frozenBalance")) {
                        if (Error.contains("Energy")) {
                            ToastUtils.show("您没有抵押能量，无法执行赎回操作");
                        } else if (Error.contains("BANDWIDTH")) {
                            ToastUtils.show("您没有抵押带宽，无法执行赎回操作");
                        }

                    }
                    if (Error.contains("delegated Resource does not exist")) {
                        ToastUtils.show("您没有为该账号抵押");
                    }
                }
            } else {
                FreezeBalanceBean unfreezeBalanceBean = new Gson().fromJson(string, FreezeBalanceBean.class);

                KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(this);
                keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {
                    @Override
                    public void onPasswordComplete(String passWord) {
                        if (SafePassword.isEquals(passWord)) {
                            UnfreezeBalanceContract(unfreezeBalanceBean);
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
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getTronAccountInfoSuccess(TronAccountInfo t) {
        hideLoading();
        BigDecimal bigDecimalBrandOneSelf = new BigDecimal(0);//带宽（自己）
        BigDecimal bigDecimalBrandOther = new BigDecimal(0);//带宽（他人）
        if (t.frozen != null) {
            if (t.frozen.size() > 0) {
                //冻结余额数
                int frozenBalance = t.frozen.get(0).frozen_balance;
                //解冻时间
                expireTime = t.frozen.get(0).expire_time;
                LogManage.e("frozenBalance--->" + frozenBalance);
                LogManage.e("expireTime--->" + expireTime);
                bigDecimalBrandOneSelf = BalanceUtils.subunitToBase(new BigInteger(frozenBalance + ""), 6);
                tvFrozenBalanceOneself.setText("自己:" + bigDecimalBrandOneSelf + " TRX");
                String date_ymd_mm_dd_ss = TimeUtils.getDate_ymd_mm_dd_ss(expireTime);
                tvThawTimeBrandWidth.setText(date_ymd_mm_dd_ss);
                long diffHours = (expireTime - System.currentTimeMillis()) / (60 * 60 * 1000);
                findViewById(R.id.ll_thaw_time_brandWidth).setVisibility(View.VISIBLE);
//                tvThawTimeBrandWidth.setText(diffHours + "h");

            }
        } else {
            tvFrozenBalanceOneself.setText("自己:" + bigDecimalBrandOneSelf + " TRX");
        }

        //他人冻结带宽
        bigDecimalBrandOther = BalanceUtils.subunitToBase(new BigInteger(t.acquired_delegated_frozen_balance_for_bandwidth + ""), 6);
        tvFrozenBalanceOtherBrand.setText("他人:" + bigDecimalBrandOther + " TRX");
        tvFrozenBalance.setText(bigDecimalBrandOneSelf.add(bigDecimalBrandOther) + "TRX");
        if (t.account_resource != null) {
            BigDecimal bigDecimal2 = BalanceUtils.subunitToBase(new BigInteger(t.account_resource.acquired_delegated_frozen_balance_for_energy + ""), 6);
            tvEnergyOther.setText("他人:" + bigDecimal2 + " TRX");
            if (t.account_resource.frozen_balance_for_energy != null) {//自己冻结
                BigDecimal bigDecimal = BalanceUtils.subunitToBase(new BigInteger(t.account_resource.frozen_balance_for_energy.frozen_balance + ""), 6);

                //能量
                tvFrozenBalanceEnergy.setText(bigDecimal.add(bigDecimal2) + "TRX");
                tvFrozenBalanceOneselfEnergy.setText("自己:" + bigDecimal + " TRX");

            } else {//他人冻结
                if (t.account_resource.acquired_delegated_frozen_balance_for_energy > 0) {
                    tvFrozenBalanceEnergy.setText(bigDecimal2 + "TRX");
                } else {
                    tvFrozenBalanceEnergy.setText("0TRX");
                }
                tvFrozenBalanceOneselfEnergy.setText("自己:" + "0 TRX");
            }
            if (t.account_resource.frozen_balance_for_energy != null) {
                findViewById(R.id.ll_thaw_time_energy).setVisibility(View.VISIBLE);
                String date_ymd_mm_dd_ss = TimeUtils.getDate_ymd_mm_dd_ss(t.account_resource.frozen_balance_for_energy.expire_time);
                tvThawTimeEnergy.setText(date_ymd_mm_dd_ss);
            }


        }


    }


    private void freezeBalanceContract(FreezeBalanceBean freezeBalanceBean) {
        showLoading();
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
            byte[] rawData = Numeric.hexStringToByteArray(freezeBalanceBean.raw_data_hex);
            String bytes = signTransaction2Byte(rawData, Numeric.hexStringToByteArray(privateKeyHex));
            ArrayList<String> signature = new ArrayList<>();
            signature.add(bytes);
            Map map = new HashMap();
            map.put("txID", freezeBalanceBean.txID);
            map.put("raw_data", freezeBalanceBean.raw_data);
            map.put("raw_data_hex", freezeBalanceBean.raw_data_hex);
            map.put("signature", signature);
            String hexStrin = new Gson().toJson(map);
            mPresenter.broadcastTransaction(hexStrin);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    private static String signTransaction2Byte(byte[] transactionBytes, byte[] privateKey) throws InvalidProtocolBufferException {

        ECKey ecKey = ECKey.fromPrivate(privateKey);
        byte[] hash = Sha256Hash.hash(transactionBytes);
        ECKey.ECDSASignature sign1 = ecKey.sign(hash);
        String s = sign1.toHex();
        return "0x" + s;
    }

    private void UnfreezeBalanceContract(FreezeBalanceBean unfreezeBalanceBean) {
        showLoading();
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
            byte[] rawData = Numeric.hexStringToByteArray(unfreezeBalanceBean.raw_data_hex);
            String bytes = signTransaction2Byte(rawData, Numeric.hexStringToByteArray(privateKeyHex));
            ArrayList<String> signature = new ArrayList<>();
            signature.add(bytes);
            Map map = new HashMap();
            map.put("txID", unfreezeBalanceBean.txID);
            map.put("raw_data", unfreezeBalanceBean.raw_data);
            map.put("raw_data_hex", unfreezeBalanceBean.raw_data_hex);
            map.put("signature", signature);
            String hexStrin = new Gson().toJson(map);
            mPresenter.broadcastTransaction(hexStrin);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getNowBlockDataSet(BlockHeader t) {

    }


}