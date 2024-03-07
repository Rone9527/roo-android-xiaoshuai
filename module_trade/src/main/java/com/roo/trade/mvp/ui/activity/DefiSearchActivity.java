package com.roo.trade.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.core.domain.trade.DeFiDataBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.roo.core.ui.widget.DividerItemDecoration;
import com.roo.core.utils.ClickUtil;
import com.roo.core.daoManagers.DeFiDaoManager;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.KeyboardUtil;
import com.roo.core.utils.Kits;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.router.Router;
import com.roo.trade.R;
import com.roo.trade.di.component.DaggerDefiSearchComponent;
import com.roo.trade.mvp.contract.DefiSearchContract;
import com.roo.trade.mvp.presenter.DefiSearchPresenter;
import com.roo.trade.mvp.ui.adapter.DefiSearchAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class DefiSearchActivity extends BaseActivity<DefiSearchPresenter> implements DefiSearchContract.View {
    @Inject
    DefiSearchAdapter mAdapter;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private EditText editText;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(DefiSearchActivity.class)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDefiSearchComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_defi_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar("", this).setVisibility(View.GONE);
        int barHeight = DeviceUtils.getStatuBarHeight(this);
        findViewById(R.id.layoutRoot).setPadding(0, barHeight, 0, 0);
        editText = findViewById(R.id.editText);
        smartRefreshLayout = ViewHelper.initRefreshLayout(this, (OnLoadMoreListener) refreshLayout -> {
            mPresenter.getDeFi(false, Kits.Text.getTextTrim(editText));
        });

        RxView.clicks(findViewById(R.id.ivBack)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> finish());

        ImageView ivCancel = findViewById(R.id.ivCancel);
        RxView.clicks(ivCancel).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> editText.setText(""));

        RxTextView.textChanges(editText).skipInitialValue()
                .debounce(200, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applySchedulers())
                .map(CharSequence::toString)
                .subscribe(t -> {
                    String text = Kits.Text.getText(editText);
                    if (TextUtils.isEmpty(text)) {
                        ivCancel.setVisibility(View.INVISIBLE);
                        mPresenter.getDeFi();
                    } else {
                        ivCancel.setVisibility(View.VISIBLE);
                    }
                });

        editText.setOnEditorActionListener((text, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
                KeyboardUtil.hideKeyboard(editText);
                mPresenter.getDeFi(true, Kits.Text.getTextTrim(editText));
                return true;
            }
            return false;
        });

        recyclerView = ViewHelper.initRecyclerView(mAdapter, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        String emptyStr = getString(R.string.defi_search_no_data);
        ViewHelper.initEmptyView(mAdapter, recyclerView, emptyStr,
                R.drawable.ic_common_empty_data_search);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (ClickUtil.clickInFronzen()) {
                return;
            }
            DeFiDataBean item = Objects.requireNonNull(mAdapter.getItem(position));
            int id = view.getId();
            if (id == R.id.ivCopy) {
                GlobalUtils.copyToClipboard(item.getContractId(), this);
                ToastUtils.show(getString(R.string.copy_success));
            } else if (id == R.id.layoutRoot) {
                DeFiDetailActivityActivity.start(this, item);
            } else if (id == R.id.tvAdd) {
                if (DeFiDaoManager.isExist(item.getIdentity())) {
                    DeFiDaoManager.delete(item.getIdentity());
                } else {
                    DeFiDaoManager.insert(item);
                }
                mAdapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Kits.Empty.check(editText.getText())) {
            mPresenter.getDeFi();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public RefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

}