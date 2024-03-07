package com.roo.trade.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.trade.DeFiDataBean;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.dialog.BottomDialog;
import com.roo.core.ui.widget.DividerItemDecoration;
import com.roo.core.daoManagers.DeFiDaoManager;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.trade.R;
import com.roo.trade.di.component.DaggerDefiHotComponent;
import com.roo.trade.mvp.contract.DefiHotContract;
import com.roo.trade.mvp.presenter.DefiHotPresenter;
import com.roo.trade.mvp.ui.activity.DeFiDetailActivityActivity;
import com.roo.trade.mvp.ui.adapter.DeFiAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class DeFiOtherFragment extends BaseFragment<DefiHotPresenter> implements DefiHotContract.View, OnRefreshListener, OnLoadMoreListener {

    @Inject
    DeFiAdapter mAdapter;
    /**
     * 热度榜（hots）、资金榜（funds）、涨幅榜（incrs）、新上线（recents）
     */
    public static String TYPE_HOT = "hots";//
    public static String TYPE_FUNDS = "funds";
    public static String TYPE_INCREASE = "incrs";
    public static String TYPE_NEW_ONLINE = "recents";
    private String deFiType;
    private SmartRefreshLayout smartRefreshLayout;

    public static DeFiOtherFragment newInstance(String deFiType) {
        DeFiOtherFragment fragment = new DeFiOtherFragment();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_DEFAULT, deFiType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDefiHotComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_defi_other, container, false);
        smartRefreshLayout = ViewHelper.initRefreshLayout(inflate, this, this);
        deFiType = getArguments().getString(Constants.KEY_DEFAULT);
        RecyclerView recyclerView = ViewHelper.initRecyclerView(inflate, mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(inflater.getContext()));
        ViewHelper.initEmptyView(mAdapter, recyclerView);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            DeFiDetailActivityActivity.start(getActivity(), mAdapter.getItem(position));
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                DeFiDataBean item = Objects.requireNonNull(mAdapter.getItem(position));

                if (DeFiDaoManager.isExist(item.getIdentity())) {
                    BottomDialog.newInstance(getString(R.string.dialog_deFi_topic_delete), getString(R.string.delete_choose))
                            .setOnClickedListener(() -> {
                                DeFiDaoManager.delete(item.getIdentity());
                                ToastUtils.show(R.string.toast_delete_deFi_success);
                            })
                            .show(getFragmentManager(), getClass().getSimpleName());
                } else {
                    BottomDialog.newInstance(getString(R.string.dialog_deFi_topic_add), getString(R.string.add_choose))
                            .setOnClickedListener(() -> {
                                DeFiDaoManager.insert(item);
                                ToastUtils.show(R.string.toast_add_deFi_success);
                            })
                            .show(getFragmentManager(), getClass().getSimpleName());
                }
                return true;
            }
        });

        return inflate;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        onRefresh(smartRefreshLayout);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public SmartRefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }

    @Override
    public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
        mPresenter.getDeFi(false, "", deFiType);
    }

    @Override
    public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
        mPresenter.getDeFi(true, "", deFiType);
    }
}