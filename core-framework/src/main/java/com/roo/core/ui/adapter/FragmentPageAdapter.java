package com.roo.core.ui.adapter;




import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.Arrays;
import java.util.List;

public class FragmentPageAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private List<T> fragments;
    private List<String> fragmentTitle;

    public FragmentPageAdapter(FragmentManager fm, List<T> fragments, List<String> fragmentTitle) {
        super(fm);
        this.fragments = fragments;
        this.fragmentTitle = fragmentTitle;
    }

    public FragmentPageAdapter(FragmentManager fm, List<T> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.fragmentTitle = Arrays.asList(titles);
    }

    public FragmentPageAdapter(FragmentManager fm, List<T> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (fragmentTitle != null) {
            return fragmentTitle.get(position);
        }
        return "";
    }
}
