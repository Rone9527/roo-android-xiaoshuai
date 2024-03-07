package com.roo.wallet.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.billy.cc.core.component.CC;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.DeviceUtils;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.adapter.FragmentPageAdapter;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.ui.widget.ExViewPager;
import com.roo.core.utils.LogManage;
import com.roo.router.Router;
import com.roo.wallet.R;
import com.roo.wallet.mvp.model.bean.GuiderBean;
import com.roo.wallet.mvp.ui.adapter.ScaleCircleNavigator;
import com.roo.wallet.mvp.ui.fragment.GuiderFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GuiderActivity extends I18nActivityArms {

    public static void start(Context context) {
        Router.newIntent(context)
                .to(GuiderActivity.class)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_guider;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();

        float screenHeight = DeviceUtils.getScreenHeight(this);
        float screenWidth = DeviceUtils.getScreenWidth(this);
        LogManage.i(">>initData()>>" + "screenHeight = [" + screenHeight + "], screenWidth = [" + screenWidth + "]");

        if (screenHeight / screenWidth > 16 / 10f) {
            findViewById(R.id.vPlaceHolder).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.vPlaceHolder).setVisibility(View.GONE);
        }

        ExViewPager exViewPager = findViewById(R.id.view_pager);
        exViewPager.setNeedAnim(true);

        ArrayList<GuiderBean.DataBean> dataBeans = GuiderBean.valueOf(this);
        ArrayList<BaseFragment> mFragmentFactory = new ArrayList<>();
        mFragmentFactory.add(GuiderFragment.newInstance(dataBeans.get(0)));
        mFragmentFactory.add(GuiderFragment.newInstance(dataBeans.get(1)));
        mFragmentFactory.add(GuiderFragment.newInstance(dataBeans.get(2)));

        MagicIndicator magicIndicator = findViewById(R.id.magicIndicator);
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(this);
        scaleCircleNavigator.setCircleCount(mFragmentFactory.size());

        scaleCircleNavigator.setNormalCircleColor(ContextCompat.getColor(this, R.color.white_color_assist_1));
        scaleCircleNavigator.setSelectedCircleColor(ContextCompat.getColor(this, R.color.blue_color));


        scaleCircleNavigator.setCircleClickListener(exViewPager::setCurrentItem);
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, exViewPager);

        exViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                magicIndicator.setVisibility(position == mFragmentFactory.size() - 1 ? View.GONE : View.VISIBLE);
            }
        });

        exViewPager.setOffscreenPageLimit(mFragmentFactory.size());
        exViewPager.setAdapter(new FragmentPageAdapter<>(getSupportFragmentManager(), mFragmentFactory));

        RxView.clicks(findViewById(R.id.layoutJump)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> gotoMainActivity());

    }

    public void gotoMainActivity() {
        SharedPref.putBoolean(Constants.KEY_GOTO_GUIDE, false);
        CC.obtainBuilder(Wallet.NAME)
                .setContext(this)
                .setActionName(Wallet.Action.WalletCreateActivity)
                .build().call();
        finish();
    }

}
