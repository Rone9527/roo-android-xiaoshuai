package com.roo.dapp.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.billy.cc.core.component.CC;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.DappTypeBean;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jiameng.mmkv.SharedPref;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.daoManagers.DappDaoManager;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.dialog.DappSafetyTipsDialog;
import com.roo.core.ui.dialog.DappTipDialog;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.dapp.R;
import com.roo.dapp.di.component.DaggerTabComponent;
import com.roo.dapp.mvp.contract.TabContract;
import com.roo.dapp.mvp.presenter.TabPresenter;
import com.roo.dapp.mvp.ui.activity.DappHostActivity;
import com.roo.dapp.mvp.ui.adapter.DappAdapter;
import com.roo.dapp.mvp.ui.dialog.AddLinkHintDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

/**
 * //  ┏┓　　　┏┓
 * //┏┛┻━━━┛┻┓
 * //┃　　　　　　　┃
 * //┃　　　━　　　┃
 * //┃　┳┛　┗┳　┃
 * //┃　　　　　　　┃
 * //┃　　　┻　　　┃
 * //┃　　　　　　　┃
 * //┗━┓　　　┏━┛
 * //    ┃　　　┃   神兽保佑
 * //    ┃　　　┃   代码无BUG！
 * //    ┃　　　┗━━━┓
 * //    ┃　　　　　　　┣┓
 * //    ┃　　　　　　　┏┛
 * //    ┗┓┓┏━┳┓┏┛
 * //      ┃┫┫　┃┫┫
 * //      ┗┻┛　┗┻┛
 * Created by : Arvin
 * Created on : 2021/9/23
 * PackageName : com.roo.dapp.mvp.ui.fragment
 * Description :
 */
public class TabFragment extends BaseFragment<TabPresenter> implements TabContract.View {


    List<DappTypeBean.ListBean> mData;
    private SmartTabLayout mViewpagertab;
    private ViewPager mViewpager;

    public static Fragment newInstance(Bundle bundle) {
        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTabComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.smarttab_child, container, false);
        mViewpagertab = inflate.findViewById(R.id.viewpagertab_child);
        mViewpager = inflate.findViewById(R.id.viewpager_child);
        return inflate;
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mData =  bundle.getParcelableArrayList(Constants.KEY_BEAN);
        }
        initChildViewPager();
    }

    @Override
    public void setData(@Nullable @org.jetbrains.annotations.Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }


    private void initChildViewPager() {
        List<View> views = new ArrayList<View>();
        for (int i = 0; i < mData.size(); i++) {
            RecyclerView recyclerView_hot = new RecyclerView(Objects.requireNonNull(getContext()));
            DappAdapter dappListAdapter = new DappAdapter();
            recyclerView_hot.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView_hot.setAdapter(dappListAdapter);
            views.add(recyclerView_hot);
            dappListAdapter.replaceData(mData.get(i).getList());
            View footView = LayoutInflater.from(recyclerView_hot.getContext())
                    .inflate(R.layout.layout_dapp_foot, (ViewGroup) recyclerView_hot.getParent(), false);
            dappListAdapter.addFooterView(footView);
//            ViewHelper.addFooterView(R.layout.layout_dapp_foot, dappListAdapter, recyclerView_hot);
            dappListAdapter.setOnItemClickListener((adapter, view, position) -> {
                DappBean bean = dappListAdapter.getItem(position);
                onDappItemClick(bean);
            });

        }


        mViewpager.setAdapter(new AZPagerAdapter(views));
        mViewpagertab.setViewPager(mViewpager);
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
                                DappHostActivity.start(requireActivity(), tokensBean, bean);
                                DappDaoManager.syn(bean);
                            })
                            .show(getChildFragmentManager(), getClass().getSimpleName());
                } else {
                    if (SharedPref.getBoolean(Constants.KEY_SHOW_DAPP_TIPS, false)) {
                        DappHostActivity.start(requireActivity(), tokensBean, bean);
                        DappDaoManager.syn(bean);
                    } else {
                        DappTipDialog.newInstance(bean.getName(), bean.getIcon())
                                .setOnClickListener(() -> {
                                    DappHostActivity.start(requireActivity(), tokensBean, bean);
                                    DappDaoManager.syn(bean);
                                })
                                .show(getChildFragmentManager(), getClass().getSimpleName());
                    }
                }
                break;

            }
        }
    }

    public class AZPagerAdapter extends PagerAdapter {

        protected List<View> views;

        public AZPagerAdapter(List<View> viewList) {
            views = viewList;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mData.get(position).getName();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }
    }
}
