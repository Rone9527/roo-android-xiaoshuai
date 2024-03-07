package com.roo.trade.mvp.ui.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.billy.cc.core.component.CC;
import com.core.domain.dapp.DappBean;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.trade.FinanceBean;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.component.Dapp;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.dialog.AddLinkHintDialog;
import com.roo.core.ui.dialog.DappTipDialog;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.trade.R;
import com.roo.trade.di.component.DaggerFinanceComponent;
import com.roo.trade.mvp.contract.FinanceContract;
import com.roo.trade.mvp.presenter.FinancePresenter;
import com.roo.trade.mvp.ui.adapter.FinanceAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 理财
 */
public class FinanceFragment extends BaseFragment<FinancePresenter> implements FinanceContract.View, OnRefreshListener, OnLoadMoreListener {
    @Inject
    FinanceAdapter mAdapter;
    private SmartRefreshLayout smartRefreshLayout;

    public static FinanceFragment newInstance() {
        FinanceFragment fragment = new FinanceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static FinanceFragment newInstance(LinkTokenBean.TokensBean tokensBean) {
        FinanceFragment fragment = new FinanceFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY_DEFAULT, tokensBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFinanceComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_finance, container, false);
        smartRefreshLayout = ViewHelper.initRefreshLayout(inflate, this, this);
        RecyclerView recyclerView = ViewHelper.initRecyclerView(inflate, mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(getActivity());
            if (null == userWallet) {
                ToastUtils.show(getString(R.string.tip_no_wallet_here));
                return;
            }
            FinanceBean.DataDTO item = mAdapter.getItem(position);
            if (!userWallet.getListMainChainCode().contains(item.getChainCode())) {
                AddLinkHintDialog.newInstance(item.getChainCode()).setOnClickListener(() -> {
                    CC.obtainBuilder(Wallet.NAME)
                            .setContext(requireActivity())
                            .setActionName(Wallet.Action.AssetSelectActivity)
                            .build().call();
                }).show(getChildFragmentManager(), getClass().getSimpleName());
                return;
            }

            if (SharedPref.getBoolean(Constants.KEY_SHOW_DAPP_TIPS, false)) {
                indexToDapp(position);
            } else {
                DappTipDialog.newInstance(item.getName(), item.getLogo())
                        .setOnClickListener(() -> indexToDapp(position))
                        .show(getChildFragmentManager(), getClass().getSimpleName());
            }
        });
        ViewHelper.initEmptyView(mAdapter, recyclerView);

        return inflate;
    }

    private void indexToDapp(int position) {
        FinanceBean.DataDTO item = mAdapter.getItem(position);
        DappBean dappBean = new DappBean();

        dappBean.setChain(item.getChainCode());
        dappBean.setName(item.getName());
        dappBean.setIcon(item.getLogo());
        dappBean.setLinks(item.getLink());
        LinkTokenBean.TokensBean tokenBean = getTokenBean(item.getChainCode());
        if (tokenBean == null) {
            return;
        }
        CC.obtainBuilder(Dapp.NAME)
                .addParam(Dapp.Param.TOKENS_BEAN, tokenBean)
                .addParam(Dapp.Param.DAPP_BEAN, dappBean)
                .setActionName(Dapp.Action.DappHostActivity)
                .build().call();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (mPresenter != null) {
            onRefresh(smartRefreshLayout);
        }
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
    public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
        mPresenter.getFinancial(false, "", "", "", "");
    }

    @Override
    public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
        mPresenter.getFinancial(true, "", "", "", "");
    }

    @Override
    public SmartRefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }


    private LinkTokenBean.TokensBean getTokenBean(String chainCode) {
        UserWallet userWallet = EthereumWalletUtils.getInstance()
                .getSelectedWalletOrFirst(requireActivity());
        if (userWallet == null) {
            return null;
        }

        if (userWallet.getListMainChainCode().contains(chainCode)) {

            ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
            if (userWallet.getListMainChainCode().contains(chainCode)) {
                for (LinkTokenBean.TokensBean item : tokenList) {
                    if (item.getChainCode().equals(chainCode)) {
                        return item;
                    }
                }
            }
        } else {
            AddLinkHintDialog.newInstance(chainCode).setOnClickListener(() -> {
                CC.obtainBuilder(Wallet.NAME)
                        .setContext(requireActivity())
                        .setActionName(Wallet.Action.AssetSelectActivity)
                        .build().call();
            }).show(getChildFragmentManager(), getClass().getSimpleName());
        }
        return null;
    }
}