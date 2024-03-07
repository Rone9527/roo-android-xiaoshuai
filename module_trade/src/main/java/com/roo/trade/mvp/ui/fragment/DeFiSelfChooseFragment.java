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
import com.roo.core.ui.dialog.BottomDialog;
import com.roo.core.ui.widget.DividerItemDecoration;
import com.roo.core.daoManagers.DeFiDaoManager;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.trade.R;
import com.roo.trade.di.component.DaggerSelfChooseComponent;
import com.roo.trade.mvp.contract.SelfChooseContract;
import com.roo.trade.mvp.presenter.SelfChoosePresenter;
import com.roo.trade.mvp.ui.activity.DeFiDetailActivityActivity;
import com.roo.trade.mvp.ui.adapter.DeFiAdapter;

import java.util.List;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class DeFiSelfChooseFragment extends BaseFragment<SelfChoosePresenter> implements SelfChooseContract.View {

    @Inject
    DeFiAdapter mAdapter;

    public static DeFiSelfChooseFragment newInstance() {
        DeFiSelfChooseFragment fragment = new DeFiSelfChooseFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSelfChooseComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_selfchoose, container, false);
        mAdapter = new DeFiAdapter();
        RecyclerView recyclerView = ViewHelper.initRecyclerView(inflate, mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(inflater.getContext()));
        ViewHelper.initEmptyView(mAdapter, recyclerView, getString(R.string.none_self_choose));
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            DeFiDetailActivityActivity.start(getActivity(), mAdapter.getItem(position));
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                BottomDialog.newInstance(getString(R.string.dialog_deFi_topic_delete), getString(R.string.delete_choose))
                        .setOnClickedListener(() -> {
                            DeFiDaoManager.delete(mAdapter.getItem(position).getIdentity());
                            ToastUtils.show(R.string.toast_delete_deFi_success);
                            mAdapter.setNewData(DeFiDaoManager.select());
                        })
                        .show(getFragmentManager(), getClass().getSimpleName());
                return true;
            }
        });
        ViewHelper.initRefreshLayout(inflate, refreshLayout -> {
            refreshLayout.finishRefresh(2000);
            mPresenter.getDeFiSelfChoose();
        });

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mPresenter != null) {
            mPresenter.getDeFiSelfChoose();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && mPresenter != null) {
            mPresenter.getDeFiSelfChoose();
        }
    }

    @Override
    public void setData(List<DeFiDataBean> list) {
        mAdapter.setNewData(list);
    }
}