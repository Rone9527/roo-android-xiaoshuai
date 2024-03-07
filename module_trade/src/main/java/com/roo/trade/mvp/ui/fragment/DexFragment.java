package com.roo.trade.mvp.ui.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.trade.R;
import com.roo.trade.di.component.DaggerDexComponent;
import com.roo.trade.mvp.contract.DexContract;
import com.roo.trade.mvp.presenter.DexPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 闪兑
 */
public class DexFragment extends BaseFragment<DexPresenter> implements DexContract.View {

    public static DexFragment newInstance() {
        DexFragment fragment = new DexFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDexComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_dex, container, false);

        return inflate;
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
}