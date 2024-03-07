package com.roo.trade.mvp.ui.activity;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.trade.R;
import com.roo.trade.di.component.DaggerDexDetailComponent;
import com.roo.trade.mvp.contract.DexDetailContract;
import com.roo.trade.mvp.presenter.DexDetailPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class DexDetailActivity extends BaseActivity<DexDetailPresenter> implements DexDetailContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDexDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_dexdetail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar("",this);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

}