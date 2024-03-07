package com.roo.dapp.mvp.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.billy.cc.core.component.CC;
import com.core.domain.dapp.DappBannerBean;
import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.DappTypeBean;
import com.core.domain.link.LinkTokenBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.jiameng.mmkv.SharedPref;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.daoManagers.DappDaoManager;
import com.roo.core.model.UserWallet;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.ui.dialog.DappSafetyTipsDialog;
import com.roo.core.ui.dialog.DappTipDialog;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.GlideImageLoader;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.dapp.R;
import com.roo.dapp.di.component.DaggerDappComponent;
import com.roo.dapp.di.module.DappModule;
import com.roo.dapp.mvp.contract.DappContract;
import com.roo.dapp.mvp.presenter.DappPresenter;
import com.roo.dapp.mvp.ui.activity.DappFavoriteActivity;
import com.roo.dapp.mvp.ui.activity.DappHostActivity;
import com.roo.dapp.mvp.ui.activity.DappSearchActivity;
import com.roo.dapp.mvp.ui.adapter.DappAdapter;
import com.roo.dapp.mvp.ui.adapter.DappGridAdapter;
import com.roo.dapp.mvp.ui.dialog.AddLinkHintDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.simple.eventbus.EventBus;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class DappFragment extends BaseFragment<DappPresenter> implements DappContract.View {

    @Inject
    DappAdapter mAdapter;
    private Banner banner;
    private List<DappTypeBean> data = new ArrayList<>();
    private int selectTopIndex = Constants.FAVORITE;
    private int selectTagIndex = 0;
    private TextView tvRecentAll;
    private TextView tvRecent;
    private TextView tvFavorite;
    private TextView tvTopSpace;
    private DappGridAdapter adapterFavRecent;
    private DappGridAdapter adapterHot;

    private List<DappBannerBean> listBanner;
    private ViewPager mViewpager;
    private ViewPageAdapter mMyPagerAdapter;
    private SmartTabLayout mViewpagertab;

    public static DappFragment newInstance() {
        DappFragment fragment = new DappFragment();
        return fragment;
    }

    @Override
    public DappGridAdapter getAdapterFavRecent() {
        return adapterFavRecent;
    }

    @Override
    public DappGridAdapter getAdapterHot() {
        return adapterHot;
    }


    @Override
    public int getSelectTopIndex() {
        return selectTopIndex;
    }

    public void setSelectTopIndex(int selectTopIndex) {
        this.selectTopIndex = selectTopIndex;

        tvFavorite.setTextSize(selectTopIndex == Constants.FAVORITE ? 16 : 14);
        tvRecent.setTextSize(selectTopIndex == Constants.RECENT ? 16 : 14);

        tvFavorite.setText(mContext.getString(R.string.favorites));
        tvRecent.setText(mContext.getString(R.string.recent));

        int selectCount = DappDaoManager.selectCount(getSelectTopIndex());
        tvRecentAll.setVisibility(getSelectTopIndex() == Constants.FAVORITE && selectCount != 0
                ? View.VISIBLE : View.GONE);
        tvRecentAll.setText(MessageFormat.format(getString(R.string.all_dapps), selectCount));

        tvFavorite.setSelected(selectTopIndex == Constants.FAVORITE);
        tvRecent.setSelected(selectTopIndex == Constants.RECENT);

        tvTopSpace.setText(getSelectTopIndex() == Constants.RECENT ? getString(R.string.empty_browsing_records) : getString(R.string.empty_favorite));

        mPresenter.getDappTop();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDappComponent
                .builder()
                .appComponent(appComponent)
                .dappModule(new DappModule(this))
                .build()
                .inject(this);

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_dapp, container, false);
        int barHeight = DeviceUtils.getStatuBarHeight(requireActivity());
        inflate.findViewById(R.id.layoutRoot).setPadding(0, barHeight, 0, 0);

        banner = inflate.findViewById(R.id.banner);
        tvRecentAll = inflate.findViewById(R.id.tvRecentAll);
        tvRecent = inflate.findViewById(R.id.tvRecent);
        tvFavorite = inflate.findViewById(R.id.tvFavorite);

        mViewpager = inflate.findViewById(R.id.viewpager);
        mViewpagertab = inflate.findViewById(R.id.viewpagertab);


        RxView.clicks(tvRecent).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    setSelectTopIndex(Constants.RECENT);
                });
        RxView.clicks(tvRecentAll).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    DappFavoriteActivity.start(requireActivity(), getSelectTopIndex());
                });
        RxView.clicks(tvFavorite).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    setSelectTopIndex(Constants.FAVORITE);
                });

        adapterFavRecent = new DappGridAdapter();
        adapterHot = new DappGridAdapter();

        RecyclerView rvFavRecent = inflate.findViewById(R.id.recycler_view_fav_recent);
        RecyclerView rvHot = inflate.findViewById(R.id.recycler_view_hot);


        ViewHelper.initRecyclerView(rvFavRecent, adapterFavRecent, new GridLayoutManager(requireActivity(), 5));
        ViewHelper.initRecyclerView(rvHot, adapterHot, new GridLayoutManager(requireActivity(), 5));


        View emptyView = LayoutInflater.from(requireActivity())
                .inflate(R.layout.item_dapp_top_space, (ViewGroup) rvFavRecent.getParent(), false);
        adapterFavRecent.setEmptyView(emptyView);
        tvTopSpace = emptyView.findViewById(R.id.empty_string);


        ViewHelper.initRefreshLayout(inflate, refreshLayout -> {
            onRefresh();
            refreshLayout.finishRefresh(2000);
        });


        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            DappBean bean = mAdapter.getItem(position);
            onDappItemClick(bean);
        });
        adapterFavRecent.setOnItemClickListener((adapter, view, position) -> {
            DappBean bean = adapterFavRecent.getItem(position);
            onDappItemClick(bean);
        });
        adapterHot.setOnItemClickListener((adapter, view, position) -> {
            DappBean bean = adapterHot.getItem(position);
            onDappItemClick(bean);
        });

        RxView.clicks(inflate.findViewById(R.id.iv_search)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> DappSearchActivity.start(requireActivity()));

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(5000);
        banner.isAutoPlay(true);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(position -> {
            if (Kits.Empty.check(listBanner.get(position).getHref())) {
                return;
            }
            HashMap<String, Object> param = new HashMap<>();
            param.put(Constants.KEY_URL, listBanner.get(position).getHref());
            param.put(Constants.KEY_FR, Constants.WEBVIEW_FROM_DAPP_BANNER);
            EventBus.getDefault().post(param, EventBusTag.GOTO_WEBVIEW);
        });
        onRefresh();
        return inflate;
    }

    private void onRefresh() {
        mPresenter.getDappBanner();
        mPresenter.getDappsHot();
        if (data.size() == 0)
            mPresenter.getDappType();
    }

    private void onDappItemClick(DappBean bean) {
        if (ClickUtil.clickInFronzen()) {
            return;
        }

        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(getActivity());
        if (null == userWallet) {
            ToastUtils.show(getString(R.string.tip_no_wallet_here));
            return;
        }
        ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
        if (!userWallet.getListMainChainCode().contains(bean.getChain())) {
            AddLinkHintDialog.newInstance(bean.getChain()).setOnClickListener(() -> {
                CC.obtainBuilder(Wallet.NAME)
                        .setContext(requireActivity())
                        .setActionName(Wallet.Action.AssetSelectActivity)
                        .build().call();
            }).show(getChildFragmentManager(), getClass().getSimpleName());
            return;
        }
        for (LinkTokenBean.TokensBean tokensBean : tokenList) {
            if (tokensBean.getChainCode().equalsIgnoreCase(bean.getChain())) {
                //有一个有值都弹出新窗口
                if (!TextUtils.isEmpty(bean.getTelegram()) || !TextUtils.isEmpty(bean.getOfficialEmail()) || !TextUtils.isEmpty(bean.getTwitter())) {
                    DappSafetyTipsDialog.newInstance(bean)
                            .setOnClickListener(() -> {
                                DappHostActivity.start(requireActivity(), tokensBean, mPresenter.updateDapp(bean));
                            })
                            .show(getChildFragmentManager(), getClass().getSimpleName());
                } else {
                    if (SharedPref.getBoolean(Constants.KEY_SHOW_DAPP_TIPS, false)) {
                        DappHostActivity.start(requireActivity(), tokensBean, mPresenter.updateDapp(bean));
                    } else {
                        DappTipDialog.newInstance(bean.getName(), bean.getIcon())
                                .setOnClickListener(() -> {
                                    DappHostActivity.start(requireActivity(), tokensBean, mPresenter.updateDapp(bean));
                                })
                                .show(getChildFragmentManager(), getClass().getSimpleName());
                    }
                }
                break;

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            setSelectTopIndex(selectTopIndex);
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

    @Override
    public void getBannerData(List<DappBannerBean> list) {
        listBanner = new ArrayList<>();
        listBanner.addAll(list);
        ArrayList dataSet = new ArrayList<>();
        for (DappBannerBean item : list) {
            dataSet.add(item.getImage());
        }
        banner.setImages(dataSet).start();
    }

    @Override
    public void getDappTypeSuccess(BaseResponse<List<DappTypeBean>> t) {
        data.clear();
        data.addAll(t.getData());
        initDappViewPager();
    }


    private void initDappViewPager() {
        mMyPagerAdapter = new ViewPageAdapter(getFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewpager.setAdapter(mMyPagerAdapter);
        mViewpagertab.setViewPager(mViewpager);

    }

    class ViewPageAdapter extends FragmentPagerAdapter {

        public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();//全部
            List<DappTypeBean.ListBean> list = data.get(position).getList();
            bundle.putParcelableArrayList(Constants.KEY_BEAN, (ArrayList<? extends Parcelable>) list);
            return TabFragment.newInstance(bundle);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return data.get(position).getName();
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }
}