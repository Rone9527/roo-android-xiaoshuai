package com.roo.trade.mvp.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.model.UserWallet;
import com.roo.core.model.common.TabEntity;
import com.roo.core.ui.adapter.FragmentPageAdapter;
import com.roo.core.ui.widget.ExViewPager;
import com.roo.core.utils.SimpleOnTabSelectListener;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.trade.R;
import com.roo.trade.di.component.DaggerMarketComponent;
import com.roo.trade.mvp.contract.MarketContract;
import com.roo.trade.mvp.presenter.MarketPresenter;
import com.roo.trade.mvp.ui.activity.DefiSearchActivity;
import com.roo.trade.mvp.ui.dialog.TradeChooseLinkDialog;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class MarketFragment extends BaseFragment<MarketPresenter> implements MarketContract.View {

    private ArrayList<BaseFragment> mFragmentFactory = new ArrayList<>();

    private ExViewPager exViewPager;
    private LinkTokenBean.TokensBean tokensBean = null;
    private UserWallet userWallet;
    private CommonTabLayout tabLayout;
    private TextView tvChange;
    private ImageView ivSearch;
    private String chainCode;

    public static MarketFragment newInstance() {
        MarketFragment fragment = new MarketFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMarketComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Subscriber(tag = EventBusTag.GOTO_DEX)
    public void onDexAdEvent(String event) {
        if (tokensBean == null) {
            return;
        }
        exViewPager.setCurrentItem(1);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_market, container, false);
        ViewHelper.initTitleBar("", inflate, this).setVisibility(View.GONE);
        tabLayout = inflate.findViewById(R.id.tabLayout);
        tvChange = inflate.findViewById(R.id.tvChange);
        ivSearch = inflate.findViewById(R.id.ivSearch);
        int barHeight = DeviceUtils.getStatuBarHeight(requireActivity());
        inflate.findViewById(R.id.layoutRoot).setPadding(0, barHeight, 0, 0);

        exViewPager = inflate.findViewById(R.id.view_pager);

        tabLayout.setOnTabSelectListener(new SimpleOnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                exViewPager.setCurrentItem(position);
//                tvChange.setVisibility(tabLayout.getTabCount() == 4 && position == 1 ? View.VISIBLE : View.GONE);
//                tvChange.setVisibility(View.GONE);//暂时隐藏
//
//                if ((tabLayout.getTabCount() == 4 && position == 3) || tabLayout.getTabCount() == 3 && position == 2) {
//                    ivSearch.setVisibility(View.VISIBLE);
//                } else {
//                    ivSearch.setVisibility(View.GONE);
//                }


                tvChange.setVisibility(View.GONE);//暂时隐藏

                if (position == 2) {
                    ivSearch.setVisibility(View.VISIBLE);
                } else {
                    ivSearch.setVisibility(View.GONE);
                }


            }
        });
        exViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }
        });
        exViewPager.setNoScroll(true);

        initViewPager(chainCode, 0);

        return inflate;
    }

    private void initViewPager(String chainCode, int tabIndex) {
        tokensBean = getTokenBean(chainCode);

        mFragmentFactory.clear();
        ArrayList<CustomTabEntity> tabList = new ArrayList<>();

        if (tokensBean == null) {
            mFragmentFactory.add(QuotesFragment.newInstance());
            mFragmentFactory.add(FinanceFragment.newInstance());


            tabList.add(new TabEntity(getString(R.string.quotation)));
            tabList.add(new TabEntity(getString(R.string.financial_management)));
        } else {
            mFragmentFactory.add(QuotesFragment.newInstance());
//            mFragmentFactory.add(CC.obtainBuilder(Dapp.NAME)
//                    .addParam(Dapp.Param.TOKENS_BEAN, tokensBean)
//                    .addParam(Dapp.Param.KEY_URL, "https://paraswap.io/#/")
//                    .setActionName(Dapp.Action.DappBrowserFragment)
//                    .build().call().getDataItemWithNoKey());
            mFragmentFactory.add(FinanceFragment.newInstance());

            tabList.add(new TabEntity(getString(R.string.quotation)));
//            tabList.add(new TabEntity(getString(R.string.flash_exchange)));
            tabList.add(new TabEntity(getString(R.string.financial_management)));

        }
        mFragmentFactory.add(DeFiParentFragment.newInstance());
        tabList.add(new TabEntity("DeFi"));

        tabLayout.setTabData(tabList);
        exViewPager.setAdapter(new FragmentPageAdapter<>(getChildFragmentManager(), mFragmentFactory));
        exViewPager.setOffscreenPageLimit(mFragmentFactory.size());

        RxView.clicks(tvChange).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            TradeChooseLinkDialog.newInstance(tokensBean == null ? "" : tokensBean.getChainCode())
                    .setOnClickListener((item) -> {
                        MarketFragment.this.chainCode = item.getCode();
                        initViewPager(item.getCode(), tabLayout.getCurrentTab());
                    }).show(getChildFragmentManager(), getClass().getSimpleName());
        });
        RxView.clicks(ivSearch).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            DefiSearchActivity.start(requireActivity());
        });
        tabLayout.setCurrentTab(tabIndex);
        exViewPager.setCurrentItem(tabIndex);
    }

    private LinkTokenBean.TokensBean getTokenBean(String chainCode) {
        userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrFirst(requireActivity());
        if (userWallet == null) {
            return null;
        }
        ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
        if (TextUtils.isEmpty(chainCode)) {
            if (userWallet.getListMainChainCode().contains(ChainCode.ETH)) {
                for (LinkTokenBean.TokensBean item : tokenList) {
                    if (item.getChainCode().equals(ChainCode.ETH)) {
                        return item;
                    }
                }
            } else if (userWallet.getListMainChainCode().contains(ChainCode.BSC)) {
                for (LinkTokenBean.TokensBean item : tokenList) {
                    if (item.getChainCode().equals(ChainCode.BSC)) {
                        return item;
                    }
                }
            }
        } else if (userWallet.getListMainChainCode().contains(chainCode)) {
            for (LinkTokenBean.TokensBean item : tokenList) {
                if (item.getChainCode().equals(chainCode)) {
                    return item;
                }
            }
        }
//        else if (userWallet.getListMainChainCode().contains(ChainCode.BSC)) {
//            for (LinkTokenBean.TokensBean item : tokenList) {
//                if (item.getChainCode().equals(ChainCode.BSC)) {
//                    return item;
//                }
//            }
//        }

        return null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (tokensBean != null) {
                UserWallet newly = EthereumWalletUtils.getInstance().getSelectedWalletOrFirst(requireActivity());
                if (EthereumWalletUtils.getInstance().isSameWallet(userWallet, newly)) {
                    return;
                }
                //钱包切换过，刷新下
                initViewPager(chainCode, 0);
            } else {
                //没有链钱包，每次切换页面都刷新下
                initViewPager(chainCode, tabLayout.getCurrentTab());
            }
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }
}