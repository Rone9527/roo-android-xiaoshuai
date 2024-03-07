package com.roo.home.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.mine.TransferRecordBean;
import com.flyco.tablayout.CommonTabLayout;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.BalanceManager;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TickerManager;
import com.roo.home.R;
import com.roo.home.di.component.DaggerTransferRecordComponent;
import com.roo.home.mvp.contract.TransferRecordContract;
import com.roo.home.mvp.model.bean.TabInfo;
import com.roo.home.mvp.presenter.TransferRecordPresenter;
import com.roo.home.mvp.ui.adapter.TransferRecordAdapter;
import com.roo.home.mvp.ui.adapter.ViewPageAdapter;
import com.roo.home.mvp.ui.fragment.TransferRecordFragment;
import com.roo.router.Router;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.subgraph.orchid.encoders.Hex;

import org.jetbrains.annotations.NotNull;
import org.simple.eventbus.EventBus;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import wallet.core.jni.Base58;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 交易记录
 */
public class TransferRecordActivity extends BaseActivity<TransferRecordPresenter>
        implements TransferRecordContract.View {
    private LinkTokenBean.TokensBean tokensBean;
    private TextView tvCoinAssetCnyValue, tvCoinAsset;
    private UserWallet userWallet;
    private ViewPager mViewpager;
    private SmartTabLayout mViewpagertab;
    private List<TabInfo> mTabInfoList;

    public static void start(Context context, LinkTokenBean.TokensBean tokensBean) {
        Router.newIntent(context)
                .to(TransferRecordActivity.class)
                .putParcelable(Constants.KEY_DEFAULT, tokensBean)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTransferRecordComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_transfer_record;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);
        tokensBean = getIntent().getParcelableExtra(Constants.KEY_DEFAULT);
        ViewHelper.initTitleBar(tokensBean.getSymbol(), this);
        mViewpager = findViewById(R.id.viewpager);
        mViewpagertab = findViewById(R.id.viewpagertab);


        initViewPager();



        tvCoinAssetCnyValue = findViewById(R.id.tvCoinAssetCnyValue);
        tvCoinAsset = findViewById(R.id.tvCoinAsset);
        RxView.clicks(findViewById(R.id.llAssetIntroduce)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    AssetIntroduceActivity.start(this, tokensBean);
                });
        if (!tokensBean.isMarket()) {
           findViewById(R.id.llTradePlatform).setVisibility(View.GONE);
        }
        RxView.clicks(findViewById(R.id.llTradePlatform)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    ExchangeQuotesActivity.start(this, tokensBean.getSymbol());
                });
        RxView.clicks(findViewById(R.id.llBlockChainBrowser)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    for (LinkTokenBean.NodesBean node : userWallet.getNodes()) {
                        if (node.getChainCode().equals(tokensBean.getChainCode())) {
                            HashMap<String, Object> param = new HashMap<>();
                            param.put(Constants.KEY_URL, tokensBean.isMain() ? node.getBrowserUrl() : node.getBrowserUrl() + "address/" + tokensBean.getContractId());
                            EventBus.getDefault().post(param, EventBusTag.GOTO_WEBVIEW);
                            break;
                        }
                    }
                });


        RelativeLayout tvTransfer = findViewById(R.id.rlTransfer);


        RxView.clicks(tvTransfer).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    switch (tokensBean.getChainCode()) {
                        case ChainCode.ETH:
                        case ChainCode.BSC:
                        case ChainCode.HECO:
                        case ChainCode.OEC:
                        case ChainCode.POLYGON:
                        case ChainCode.FANTOM:
                            TransferETHActivity.start(this, tokensBean, TransferETHActivity.FR_RECORD);
                            break;
                        case ChainCode.BTC:
                            LogManage.e(Constants.LOG_STRING, "");
                            break;
                        case ChainCode.TRON:
                            TransferTronActivity.start(this, tokensBean, TransferTronActivity.FR_RECORD);
                            break;
                        default:
                            break;
                    }
                });
        RelativeLayout tvCashier = findViewById(R.id.rlReceive);


        RxView.clicks(tvCashier).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    CashierActivity.start(this, tokensBean);
                });
        //带宽/能量
        TextView tvBandwidthAndEnergy = findViewById(R.id.tv_bandwidth_energy);
        if (tokensBean.getChainCode().equals(ChainCode.TRON)) {
            tvBandwidthAndEnergy.setVisibility(View.VISIBLE);
        }
        RxView.clicks(tvBandwidthAndEnergy).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    TronBandwidthEnergyActivity.start(this, tokensBean);
                });

    }

    private void initViewPager() {
        mTabInfoList = new ArrayList<>();
        Bundle bundle0 = new Bundle();
        bundle0.putParcelable(Constants.KEY_DEFAULT, tokensBean);
        bundle0.putInt(Constants.KEY_TYPE, 0);

        Bundle bundle1 = new Bundle();
        bundle1.putParcelable(Constants.KEY_DEFAULT, tokensBean);
        bundle1.putInt(Constants.KEY_TYPE, 1);

        Bundle bundle2 = new Bundle();
        bundle2.putParcelable(Constants.KEY_DEFAULT, tokensBean);
        bundle2.putInt(Constants.KEY_TYPE, 2);

        mTabInfoList.add(new TabInfo(getString(R.string.all), TransferRecordFragment.class, bundle0));
        mTabInfoList.add(new TabInfo(getString(R.string.transfer_into), TransferRecordFragment.class, bundle1));
        mTabInfoList.add(new TabInfo(getString(R.string.transfer_out), TransferRecordFragment.class, bundle2));
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTabInfoList, this);
        mViewpager.setAdapter(viewPageAdapter);
        mViewpagertab.setViewPager(mViewpager);
    }




    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }



    @Override
    protected void onResume() {
        super.onResume();
        BigDecimal amount = BalanceManager.getInstance().get(userWallet, tokensBean);
        LogManage.e("amount---->" + amount);
        String legalValue = TickerManager.getInstance().getLegalValueWithSymbol(
                tokensBean.getSymbol(), amount, 4
        );

        BigDecimal coinAsset = TickerManager.getInstance().getDecimalSymbolCount(
                tokensBean.getSymbol(), amount
        );

        tvCoinAssetCnyValue.setText(legalValue);
        tvCoinAsset.setText(GlobalUtils.formatBigNumbersNOKB(coinAsset, 4));

    }

    @Override
    public LinkTokenBean.TokensBean getTokensBean() {
        return tokensBean;
    }


    @Override
    public Activity getActivity() {
        return this;
    }


    @Override
    public void onRefreshSuccess(List<TransferRecordBean.DataBean> data) {

    }

    @Override
    public void onLoadMoreSuccess(List<TransferRecordBean.DataBean> data) {

    }


}