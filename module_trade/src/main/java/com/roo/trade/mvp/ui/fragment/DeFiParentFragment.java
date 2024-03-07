package com.roo.trade.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.core.domain.link.LinkTokenBean;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.common.TabEntity;
import com.roo.core.ui.adapter.FragmentPageAdapter;
import com.roo.core.ui.widget.ExViewPager;
import com.roo.core.utils.SimpleOnTabSelectListener;
import com.roo.trade.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class DeFiParentFragment extends BaseFragment {
    private CommonTabLayout tabLayout;
    private ExViewPager exViewPager;

    public static DeFiParentFragment newInstance() {
        DeFiParentFragment fragment = new DeFiParentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static DeFiParentFragment newInstance(LinkTokenBean.TokensBean tokensBean) {
        DeFiParentFragment fragment = new DeFiParentFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY_DEFAULT, tokensBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_defi, container, false);
        tabLayout = inflate.findViewById(R.id.tabLayout);
        exViewPager = inflate.findViewById(R.id.view_pager);
        tabLayout.setOnTabSelectListener(new SimpleOnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                exViewPager.setCurrentItem(position);
            }
        });
        exViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }
        });
        exViewPager.setNoScroll(true);

        ArrayList<BaseFragment> mFragmentFactory = new ArrayList<>();
        ArrayList<CustomTabEntity> tabList = new ArrayList<>();

        mFragmentFactory.add(DeFiSelfChooseFragment.newInstance());
        tabList.add(new TabEntity(getString(R.string.self_choose)));

        mFragmentFactory.add(DeFiOtherFragment.newInstance(DeFiOtherFragment.TYPE_HOT));
        tabList.add(new TabEntity(getString(R.string.string_hot)));
        mFragmentFactory.add(DeFiOtherFragment.newInstance(DeFiOtherFragment.TYPE_FUNDS));
        tabList.add(new TabEntity(getString(R.string.funds_list)));
        mFragmentFactory.add(DeFiOtherFragment.newInstance(DeFiOtherFragment.TYPE_INCREASE));
        tabList.add(new TabEntity(getString(R.string.Increase_list)));
        mFragmentFactory.add(DeFiOtherFragment.newInstance(DeFiOtherFragment.TYPE_NEW_ONLINE));
        tabList.add(new TabEntity(getString(R.string.new_online)));

        tabLayout.setTabData(tabList);
        exViewPager.setAdapter(new FragmentPageAdapter<>(getChildFragmentManager(), mFragmentFactory));
        exViewPager.setOffscreenPageLimit(mFragmentFactory.size());

        tabLayout.setCurrentTab(1);
        exViewPager.setCurrentItem(1);

        return inflate;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

}
