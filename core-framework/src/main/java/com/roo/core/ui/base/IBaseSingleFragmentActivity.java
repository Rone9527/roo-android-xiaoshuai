package com.roo.core.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.finddreams.languagelib.MultiLanguageUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.roo.core.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * Created: AriesHoo on 2017/7/3 16:04
 * Function: title 基类
 * Desc:
 */

public abstract class IBaseSingleFragmentActivity extends BaseActivity {

    private Fragment fragment;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(ViewPumpContextWrapper.wrap(newBase)));
    }

    protected int getLayoutRes() {
        return 0;
    }

    protected abstract Fragment createFragment();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @CallSuper
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getLayoutRes() == 0) {
            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setId(R.id.container);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            frameLayout.setLayoutParams(params);
            setContentView(frameLayout);
        } else {
            setContentView(getLayoutRes());
        }
        fragment = createFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment).commit();

        initedData(savedInstanceState);
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void initedData(@Nullable Bundle savedInstanceState) {
    }
}
