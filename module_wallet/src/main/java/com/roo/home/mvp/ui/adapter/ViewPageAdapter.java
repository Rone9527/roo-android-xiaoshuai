package com.roo.home.mvp.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.roo.home.mvp.model.bean.TabInfo;

import java.util.List;

/**
 * Created by : Arvin
 * Created on : 2021/9/24
 * PackageName : com.roo.home.mvp.ui.adapter
 * Description :
 */


public class ViewPageAdapter extends FragmentPagerAdapter {
    public Context mContext;
    public List<TabInfo> mTabInfoList;
    public FragmentManager mFm;

    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior, List<TabInfo> tabInfoList, Context context) {
        super(fm, behavior);
        mTabInfoList = tabInfoList;
        mContext = context;
        mFm = fm;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        TabInfo tabInfo = mTabInfoList.get(position);
        FragmentFactory factory = mFm.getFragmentFactory();
        Fragment fragment = factory.instantiate(mContext.getClassLoader(), tabInfo.clz.getName());
        fragment.setArguments(tabInfo.args);
        return fragment;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        TabInfo tabInfo = mTabInfoList.get(position);
        return tabInfo.title;
    }

    @Override
    public int getCount() {
        if (mTabInfoList != null) {
            return mTabInfoList.size();
        }
        return 0;
    }
}
