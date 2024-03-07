package com.roo.trade.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.core.domain.dapp.DappBean;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.trade.DeFiDataBean;
import com.core.domain.trade.DeFiDetailBean;
import com.core.domain.trade.DeFiPriceChartBean;
import com.core.domain.trade.DeFiTradeBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.component.Dapp;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.dialog.AddLinkHintDialog;
import com.roo.core.ui.dialog.DappTipDialog;
import com.roo.core.ui.widget.DialogLoading;
import com.roo.core.ui.widget.DividerItemDecoration;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.DataUtil;
import com.roo.core.daoManagers.DeFiDaoManager;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.router.Router;
import com.roo.trade.R;
import com.roo.trade.di.component.DaggerDeFiDetailActivityComponent;
import com.roo.trade.mvp.contract.DeFiDetailActivityContract;
import com.roo.trade.mvp.presenter.DeFiDetailActivityPresenter;
import com.roo.trade.mvp.ui.adapter.DeFiTradeRecentAdapter;
import com.roo.trade.mvp.ui.dialog.TradingRiskWarningDialog;
import com.roo.view.chart.Axis;
import com.roo.view.chart.AxisValue;
import com.roo.view.chart.Line;
import com.roo.view.chart.PointValue;
import com.roo.view.chart.SlideSelectLineChart;
import com.roo.view.chart.SlidingLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * DeFi详情
 */
public class DeFiDetailActivityActivity extends BaseActivity<DeFiDetailActivityPresenter> implements DeFiDetailActivityContract.View {
    private DeFiDataBean deFiDataBean;
    private DeFiDetailBean deFiDetailBean;
    private DeFiTradeRecentAdapter mAdapter;
    private UserWallet userWallet;
    private TextView tvName;
    private ImageView ivTip;
    private TextView tvContractAddress;
    private LinearLayout llCopyAddress;
    private TextView tvOnlinePrice;
    private TextView tvOnlineDate;
    private TextView tvPlatform;
    private TextView tvAmountHolders;
    private TextView tvAmountTransfer;
    private TextView tvLiquidPool;
    private TextView txtPrice;
    private TextView tvPrice;
    private TextView tvRatePrice;
    private TextView tvAmplitudePool;
    private ImageView ivLogoOne;
    private TextView tvAmountOne;
    private ImageView ivLogoTwo;
    private TextView tvAmountTwo;
    private TextView tvAmountTradeDaily;
    private TextView tvPriceUSD;
    private TextView tvAmplitudeDailyAmount;
    private TextView tvCountTradeDaily;
    private TextView tvAmplitudeDailyCount;
    private ImageView ivRefresh;
    private TextView tvRefresh;
    private TitleBarView mTitleBarView;
    private TextView tvStartTrade;
    private SlideSelectLineChart sslChart;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDeFiDetailActivityComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    public static void start(Context context, DeFiDataBean deFiDataBean) {
        Router.newIntent(context)
                .to(DeFiDetailActivityActivity.class)
                .putSerializable(Constants.KEY_DEFAULT, deFiDataBean)
                .launch();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_defi_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRightAction();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        deFiDataBean = (DeFiDataBean) getIntent().getSerializableExtra(Constants.KEY_DEFAULT);
        mTitleBarView = ViewHelper.initTitleBar(deFiDataBean.getPairName(), this);

        getRightAction();
        tvName = findViewById(R.id.tvName);
        ivTip = findViewById(R.id.ivTip);
        tvContractAddress = findViewById(R.id.tvContractAddress);
        llCopyAddress = findViewById(R.id.llCopyAddress);
        tvOnlinePrice = findViewById(R.id.tvOnlinePrice);
        tvOnlineDate = findViewById(R.id.tvOnlineDate);
        tvPlatform = findViewById(R.id.tvPlatform);
        tvAmountHolders = findViewById(R.id.tvAmountHolders);
        tvAmountTransfer = findViewById(R.id.tvAmountTransfer);
        tvLiquidPool = findViewById(R.id.tvLiquidPool);
        txtPrice = findViewById(R.id.txtPrice);
        tvPrice = findViewById(R.id.tvPrice);
        tvRatePrice = findViewById(R.id.tvRatePrice);
        tvAmplitudePool = findViewById(R.id.tvAmplitudePool);
        ivLogoOne = findViewById(R.id.ivLogoOne);
        tvAmountOne = findViewById(R.id.tvAmountOne);
        ivLogoTwo = findViewById(R.id.ivLogoTwo);
        tvAmountTwo = findViewById(R.id.tvAmountTwo);
        tvAmountTradeDaily = findViewById(R.id.tvAmountTradeDaily);
        tvPriceUSD = findViewById(R.id.tvPriceUSD);
        tvAmplitudeDailyAmount = findViewById(R.id.tvAmplitudeDailyAmount);
        tvCountTradeDaily = findViewById(R.id.tvCountTradeDaily);
        tvAmplitudeDailyCount = findViewById(R.id.tvAmplitudeDailyCount);
        tvRefresh = findViewById(R.id.tvRefresh);
        ivRefresh = findViewById(R.id.ivRefresh);
        tvStartTrade = findViewById(R.id.tvStartTrade);
        sslChart = findViewById(R.id.sslChart);
        RxView.clicks(llCopyAddress).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (!Kits.Empty.check(deFiDetailBean)) {
                        GlobalUtils.copyToClipboard(deFiDetailBean.getToken0Id(), this);
                        ToastUtils.show(getString(R.string.copy_success));
                    }
                });
        RxView.clicks(tvRefresh).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    DialogLoading.getInstance().showDialog(this);
                    mPresenter.getDeFiTradeRecent(deFiDataBean.getAscription(), deFiDataBean.getIdentity());
                });
        RxView.clicks(ivRefresh).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    DialogLoading.getInstance().showDialog(this);
                    mPresenter.getDeFiTradeRecent(deFiDataBean.getAscription(), deFiDataBean.getIdentity());
                });

        mAdapter = new DeFiTradeRecentAdapter();
        RecyclerView recyclerView = ViewHelper.initRecyclerView(mAdapter, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        RxView.clicks(ivTip).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    TradingRiskWarningDialog.newInstance().show(getSupportFragmentManager(), getLocalClassName());
                });
        RxView.clicks(tvStartTrade).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    onDappItemClick();
                });

        mPresenter.getDeFiDetail(deFiDataBean.getAscription(), deFiDataBean.getIdentity());
        mPresenter.getDeFiTradeRecent(deFiDataBean.getAscription(), deFiDataBean.getIdentity());
        mPresenter.getDeFiPriceChart(deFiDataBean.getAscription(), deFiDataBean.getIdentity());

    }

    private void getRightAction() {
        mTitleBarView.getLinearLayout(Gravity.END).removeAllViews();
        TitleBarView.ImageAction imageAction;

        if (DeFiDaoManager.isExist(deFiDataBean.getIdentity())) {
            imageAction = mTitleBarView.new ImageAction(R.drawable.ic_defi_saved, v -> {
                DeFiDaoManager.delete(deFiDataBean.getIdentity());
                getRightAction();
                ToastUtils.show(R.string.toast_delete_deFi_success);
            });
        } else {
            imageAction = mTitleBarView.new ImageAction(R.drawable.ic_defi_save_no, v -> {
                DeFiDaoManager.insert(deFiDataBean);
                getRightAction();
                ToastUtils.show(R.string.toast_add_deFi_success);
            });
        }
        mTitleBarView.addRightAction(imageAction);
    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void setDeFiDetailData(DeFiDetailBean detailData) {
        if (!Kits.Empty.check(detailData)) {
            setDataForUI(detailData);
        }
    }

    @Override
    public void setDataDeFiTrade(List<DeFiTradeBean> list) {
        if (Kits.Empty.check(list)) {
            findViewById(R.id.llRecent).setVisibility(View.GONE);
        } else {
            mAdapter.setNewData(list);
        }
        DialogLoading.getInstance().closeDialog();
    }

    @SuppressLint("NewApi")
    @Override
    public void setDataDeFiPriceChart(List<DeFiPriceChartBean> list) {
        if (Kits.Empty.check(list) || list.size() < 3) {
            findViewById(R.id.ll_chart).setVisibility(View.GONE);
            return;
        } else {
            findViewById(R.id.ll_chart).setVisibility(View.VISIBLE);
        }
        try {
            List<AxisValue> axisValueListX = new ArrayList<>();
            List<AxisValue> axisValueListY = new ArrayList<>();
            @SuppressLint({"NewApi", "LocalSuppress"})
            Double minDD = list.stream().mapToDouble(
                    value -> Double.parseDouble(
                            new BigDecimal(value.getPrice()).setScale(8, RoundingMode.DOWN).toPlainString())
            ).min().getAsDouble();

            minDD = minDD * 0.95;
            for (DeFiPriceChartBean deFiPriceChartBean : list) {
                AxisValue axisValueX = new AxisValue();
                AxisValue axisValueY = new AxisValue();
                axisValueX.setLabel(getTime(deFiPriceChartBean.getTimestamp()));
//                axisValueY.setLabel((Double.parseDouble(new BigDecimal(deFiPriceChartBean.getPrice()).setScale(2).toPlainString()) - minDD) + "");//使用差值画线
                String douV = (Double.parseDouble(
                        new BigDecimal(deFiPriceChartBean.getPrice()).setScale(8, RoundingMode.DOWN).toPlainString()) - minDD) + "";
                axisValueY.setLabel(new BigDecimal(douV).setScale(8, RoundingMode.DOWN).stripTrailingZeros().toPlainString());//使用差值画线
                axisValueListX.add(axisValueX);
                axisValueListY.add(axisValueY);
            }
            Axis axisX = new Axis(axisValueListX);
            axisX.setShowText(false);
            axisX.setHasLines(false);
            axisX.setAxisColor(Color.TRANSPARENT);

            Axis axisY = new Axis(axisValueListY);
            axisY.setShowText(false);
            axisY.setHasLines(false);
            axisY.setAxisColor(Color.TRANSPARENT);

            @SuppressLint({"NewApi", "LocalSuppress"})
            Double max = axisValueListY.stream().mapToDouble(axisValue -> Float.parseFloat(axisValue.getLabel())).max().getAsDouble();
            sslChart.setAxisX(axisX);
            sslChart.setAxisY(axisY);
            sslChart.setSlideLine(getSlideingLine());

            Line line = getFoldLineNew(axisValueListY, max, minDD);
            sslChart.setChartData(line);
            sslChart.show();
        } catch (Exception e) {
            findViewById(R.id.ll_chart).setVisibility(View.GONE);
        }
    }

    private SlidingLine getSlideingLine() {
        SlidingLine slidingLine = new SlidingLine();
        slidingLine.setSlideLineColor(Color.parseColor("#376AFF"))
                .setSlideLineWidth(30)
                .setSlidePointColor(Color.parseColor("#376AFF"))
                .setSlidePointRadius(6)
                .setDash(false);
        return slidingLine;
    }

    public String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time * 1000));//换成毫秒
    }

    private Line getFoldLineNew(List<AxisValue> listY, Double max, Double minDD) {

        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 0; i < listY.size(); i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX((i) / (listY.size() - 1f));
            pointValue.setLabel((Double.parseDouble(listY.get(i).getLabel()) + minDD) + "");
            pointValue.setY(Float.parseFloat(listY.get(i).getLabel()) / max.floatValue());
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }
        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#376AFF"))
                .setLineWidth(1.5f)
                .setPointColor(Color.parseColor("#376AFF"))
                .setCubic(true)
                .setPointRadius(2)
                .setFill(true)
                .setHasPoints(false)
                .setFillColor(Color.parseColor("#376AFF"))
                .setLabelColor(Color.parseColor("#376AFF"))
                .setHasLabels(true);
        return line;
    }


    private void setDataForUI(DeFiDetailBean detailData) {
        this.deFiDetailBean = detailData;
        tvName.setText(detailData.getToken0Symbol());
        tvContractAddress.setText(detailData.getToken0Id());
        tvOnlinePrice.setText(MessageFormat.format("1:{0}",
                DataUtil.getStringAboutOne(detailData.getInitPrice())));
        tvOnlineDate.setText(Kits.Date.getYmdhms(detailData.getCreateTimestamp() * 1000));
        tvPlatform.setText(detailData.getAscription());
        tvAmountHolders.setText(detailData.getHolders() == 0 ? "--" : MessageFormat.format("{0}", detailData.getHolders()));
        tvAmountTransfer.setText(detailData.getTxCount());
        tvLiquidPool.setText(MessageFormat.format("$ {0}", new BigDecimal(detailData.getReserveUSD()).setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString()));
        tvAmplitudePool.setText(getFormatPercentData(detailData.getRateOfReserveUSD()));
        setColorForTv(tvAmplitudePool, new BigDecimal(detailData.getRateOfReserveUSD()));
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_common_error_pic_circle)//图片加载出来前，显示的图片
                .fallback(R.drawable.ic_common_error_pic_circle) //url为空的时候,显示的图片
                .error(R.drawable.ic_common_error_pic_circle);
        Glide.with(this)
                .load(detailData.getBaseLogo()) //图片地址
                .apply(options)
                .into(ivLogoOne);
        Glide.with(this)
                .load(detailData.getLogo()) //图片地址
                .apply(options)
                .into(ivLogoTwo);

        tvAmountOne.setText(MessageFormat.format("{0} {1}",
                getFormatData(detailData.getToken1Reserve()), detailData.getToken1Symbol()));
        tvAmountTwo.setText(MessageFormat.format("{0} {1}",
                getFormatData(detailData.getToken0Reserve()), detailData.getToken0Symbol()));

        txtPrice.setText(MessageFormat.format("{0} ({1}: {2})",
                getString(R.string.t_price),
                detailData.getToken1Symbol(),
                detailData.getToken0Symbol()));

        tvPrice.setText(MessageFormat.format("1:{0}", DataUtil.getStringAboutOne(detailData.getToken0Price())));
        tvPriceUSD.setText(MessageFormat.format("≈ ${0}",
                new BigDecimal(detailData.getToken0PriceUSD()).setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString()));
        tvRatePrice.setText(getFormatPercentData(detailData.getRateOfPrice()));
        new BigDecimal(detailData.getToken0PriceUSD()).setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        setColorForTv(tvRatePrice, new BigDecimal(detailData.getRateOfPrice()));

        tvAmountTradeDaily.setText(MessageFormat.format("$ {0}",
                GlobalUtils.formatBigNumbers(new BigDecimal(detailData.getVolume24()).setScale(2, RoundingMode.DOWN))));
        tvAmplitudeDailyAmount.setText(getFormatPercentData(detailData.getRateOfVolume24()));
        setColorForTv(tvAmplitudeDailyAmount, new BigDecimal(detailData.getRateOfVolume24()));

        tvCountTradeDaily.setText(new BigDecimal(detailData.getTxCount24()).stripTrailingZeros().toPlainString());
        tvAmplitudeDailyCount.setText(getFormatPercentData(detailData.getRateOfTxCount24()));
        setColorForTv(tvAmplitudeDailyCount, new BigDecimal(detailData.getRateOfTxCount24()));
    }

    private String getFormatPercentData(String data) {
        String format;
        String d = BigDecimal.valueOf(Double.parseDouble(data) * 100)
                .setScale(2, RoundingMode.DOWN)
                .stripTrailingZeros().toPlainString();
        if (Float.parseFloat(data) > 0) {
            format = MessageFormat.format("+{0}%", d);
        } else {
            format = MessageFormat.format("{0}%", d);
        }
        return format;
    }

    private void setColorForTv(TextView tv, BigDecimal number) {
        if (number.compareTo(new BigDecimal(0)) == 0) {
            tv.setTextColor(getColor(R.color.color_editor));
        } else if (number.compareTo(new BigDecimal(0)) == -1) {
            tv.setTextColor(getColor(R.color.color_txt_red2));
        } else {
            tv.setTextColor(getColor(R.color.green_color));
        }
    }

    private String getFormatData(String value) {
        int isBigThanZero = new BigDecimal(value).compareTo(new BigDecimal(0));
        if (isBigThanZero == 0) {
            return value;
        } else if (isBigThanZero == -1) {
            return new BigDecimal(value).setScale(6, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
        } else {
            return new BigDecimal(value).setScale(2, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
        }
    }

    private void onDappItemClick() {
        if (ClickUtil.clickInFronzen()) {
            return;
        }
        userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);
        if (null == userWallet) {
            ToastUtils.show(getString(R.string.tip_no_wallet_here));
            return;
        }
        if (null == deFiDetailBean) {
            return;
        }
        if (!userWallet.getListMainChainCode().contains(deFiDetailBean.getChainCode())) {
            AddLinkHintDialog.newInstance(deFiDetailBean.getChainCode()).setOnClickListener(() -> {
                CC.obtainBuilder(Wallet.NAME)
                        .setContext(this)
                        .setActionName(Wallet.Action.AssetSelectActivity)
                        .build().call();
            }).show(getSupportFragmentManager(), getClass().getSimpleName());
            return;
        }

        LinkTokenBean.TokensBean tokensBean = getTokenBean(deFiDetailBean.getChainCode());
        if (SharedPref.getBoolean(Constants.KEY_SHOW_DAPP_TIPS, false)) {
            indexToDapp(tokensBean);
        } else {
            DappTipDialog.newInstance(deFiDetailBean.getAscription(), deFiDetailBean.getAscriptionIcon())
                    .setOnClickListener(() -> indexToDapp(tokensBean))
                    .show(getSupportFragmentManager(), getClass().getSimpleName());
        }
    }

    private void indexToDapp(LinkTokenBean.TokensBean tokensBean) {
        DappBean dappBean = new DappBean();

        dappBean.setChain(deFiDetailBean.getChainCode());
        dappBean.setName(deFiDetailBean.getAscription());
        dappBean.setIcon(deFiDetailBean.getAscriptionIcon());
        dappBean.setLinks(deFiDetailBean.getUrl());

        CC.obtainBuilder(Dapp.NAME)
                .addParam(Dapp.Param.TOKENS_BEAN, tokensBean)
                .addParam(Dapp.Param.DAPP_BEAN, dappBean)
                .setActionName(Dapp.Action.DappHostActivity)
                .build().call();
    }

    private LinkTokenBean.TokensBean getTokenBean(String chainCode) {
        if (userWallet == null) {
            return null;
        }
        ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
        for (LinkTokenBean.TokensBean item : tokenList) {
            if (item.getChainCode().equals(chainCode)) {
                return item;
            }
        }

        return null;
    }
}