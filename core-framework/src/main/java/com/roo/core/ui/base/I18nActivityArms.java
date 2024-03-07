package com.roo.core.ui.base;

import android.content.Context;

import com.finddreams.languagelib.MultiLanguageUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * @author oldnine
 * @since 2018/12/15
 */
public abstract class I18nActivityArms<P extends IPresenter> extends BaseActivity<P> {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(ViewPumpContextWrapper.wrap(newBase)));
    }
}
