package com.roo.mine.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import com.aries.ui.view.title.TitleBarView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.mine.R;
import com.roo.mine.di.component.DaggerLanguageComponent;
import com.roo.mine.mvp.contract.LanguageContract;
import com.roo.mine.mvp.presenter.LanguagePresenter;
import com.roo.mine.mvp.ui.adapter.LanguageAdapter;
import com.roo.router.Router;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 多语言
 */
public class LanguageActivity extends BaseActivity<LanguagePresenter> implements LanguageContract.View {
    @Inject
    LanguageAdapter mAdapter;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(LanguageActivity.class)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLanguageComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_language;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        TitleBarView mTitleBarView = ViewHelper.initTitleBar(getString(R.string.title_multi_lag), this);
        mTitleBarView.setActionTextColor(ContextCompat.getColor(this, R.color.blue_color));
        mTitleBarView.addRightAction(mTitleBarView.new TextAction(getString(R.string.save),
                v -> {
                    //SharedPref.getString()
                    setResult(RESULT_OK);
                    finish();
                }));
        ViewHelper.initRecyclerView(mAdapter, this);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }
}