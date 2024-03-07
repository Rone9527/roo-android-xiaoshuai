package com.roo.mine.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.ui.widget.DividerItemDecoration;
import com.roo.core.utils.ViewHelper;
import com.roo.mine.R;
import com.roo.mine.di.component.DaggerLegalCurrencyComponent;
import com.roo.mine.mvp.contract.LegalCurrencyContract;
import com.roo.mine.mvp.presenter.LegalCurrencyPresenter;
import com.roo.mine.mvp.ui.adapter.LegalCurrencyAdapter;
import com.roo.router.Router;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 计价货币
 */
public class LegalCurrencyActivity extends BaseActivity<LegalCurrencyPresenter>
        implements LegalCurrencyContract.View {

    public static final int REQUEST_CODE_CURRENCY = 9875;

    @Inject
    LegalCurrencyAdapter mAdapter;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(LegalCurrencyActivity.class)
                .requestCode(REQUEST_CODE_CURRENCY)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLegalCurrencyComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_legal_currency;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar(getString(R.string.denomination_currency), this);
        RecyclerView recyclerView = ViewHelper.initRecyclerView(mAdapter, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mAdapter.setSelected(mAdapter.getItem(position));
            SharedPref.putObject(Constants.KEY_OBJ_LEGAL_CURRENT, mAdapter.getSelected());
            EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.LEGAL_CHANGED);
            setResult(RESULT_OK);
            finish();
        });
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

}