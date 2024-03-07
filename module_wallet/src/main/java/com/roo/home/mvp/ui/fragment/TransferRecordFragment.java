package com.roo.home.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.mine.TransferRecordBean;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.home.R;
import com.roo.home.di.component.DaggerTransferRecordComponent;
import com.roo.home.mvp.contract.TransferRecordContract;
import com.roo.home.mvp.presenter.TransferRecordPresenter;
import com.roo.home.mvp.ui.activity.TransferDetailActivity;
import com.roo.home.mvp.ui.adapter.TransferRecordAdapter;
import com.roo.home.mvp.utils.PenddingManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.subgraph.orchid.encoders.Hex;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.util.ArrayList;
import java.util.List;

import wallet.core.jni.Base58;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class TransferRecordFragment extends BaseFragment<TransferRecordPresenter> implements TransferRecordContract.View, BaseQuickAdapter.OnItemClickListener {

    private SmartRefreshLayout smartRefreshLayout;
    private LinkTokenBean.TokensBean tokensBean;
    private UserWallet userWallet;
    private TransferRecordAdapter mAdapter;
    private int filterType;
    private RecyclerView recyclerView;

    public static TransferRecordFragment newInstance() {
        TransferRecordFragment fragment = new TransferRecordFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTransferRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_transferrecord, container, false);
        recyclerView = inflate.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TransferRecordAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        smartRefreshLayout = inflate.findViewById(R.id.refreshLayout);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onRefresh();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onLoadMore();
            }
        });
        return inflate;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tokensBean = getArguments().getParcelable(Constants.KEY_DEFAULT);
        filterType = getArguments().getInt(Constants.KEY_TYPE);
        initFilterType();
        mPresenter.onRefresh();

    }

    private void initFilterType() {

        LogManage.e("tokensBean.getChainCode()" + tokensBean.getChainCode());
        userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(getContext());
        LogManage.e("tokensBean.getChainCode()" + userWallet.getChainWallets());
        UserWallet.ChainWallet chainWallet = EthereumWalletUtils.getInstance()
                .getWalletByChainCode(userWallet, tokensBean.getChainCode());
        String address;
        switch (tokensBean.getChainCode()) {
            case ChainCode.TRON:
                if (userWallet.getPrivateKey2() != null) {
                    address = getPrivateKeyToAddress(userWallet.getPrivateKey2());
                } else {
                    HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
                    address = hdWallet.getAddressForCoin(CoinType.TRON);
                }
                break;
            default:
                address = Constants.PREFIX_16 + chainWallet.getWalletFile2().getAddress();
                break;

        }

        mAdapter.setAddress(address);
        mPresenter.setAddress(address);
        mPresenter.setTokensBean(tokensBean);
    }

    private String getPrivateKeyToAddress(String privateKey) {
        String address = Keys.toChecksumAddress(Keys.getAddress(ECKeyPair.create(Numeric.toBigInt(privateKey))));
        byte[] decode = Hex.decode(address.replace(Constants.PREFIX_16, "41"));
        return Base58.encode(decode);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }


    @Override
    public LinkTokenBean.TokensBean getTokensBean() {
        return tokensBean;
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (ClickUtil.clickInFronzen()) {
            return;
        }
        TransferRecordBean.DataBean item = mAdapter.getItem(position);
        TransferRecordBean.DataBean copyOf = new TransferRecordBean.DataBean();
        copyOf.setTxId(item.getTxId());
        copyOf.setBlockHash(item.getBlockHash());
        copyOf.setBlockNum(item.getBlockNum());
        copyOf.setToken(item.getToken());
        copyOf.setFromAddr(item.getFromAddr());
        copyOf.setToAddr(item.getToAddr());
        copyOf.setAmount(item.getAmount());
        copyOf.setGasLimit(item.getGasLimit());
        copyOf.setGasPrice(item.getGasPrice());
        copyOf.setGasUsed(item.getGasUsed());
        copyOf.setConvertGasUsed(item.getConvertGasUsed());
        copyOf.setFeeToken(item.getFeeToken());
        copyOf.setTimeStamp(item.getTimeStamp());
        copyOf.setStatusType(item.getStatusType());
        TransferDetailActivity.start(getActivity(), copyOf, tokensBean.getChainCode());
    }


    @Override
    public void onRefreshSuccess(List<TransferRecordBean.DataBean> data) {
        smartRefreshLayout.finishRefresh();
        PenddingManager.getInstance().checkPendding(getTokensBean(), data);
        ArrayList<TransferRecordBean.DataBean> pendding =
                PenddingManager.getInstance().getPendding(getTokensBean());
        if (!Kits.Empty.check(pendding)) {
            data.addAll(0, pendding);
        }

        if (data.size() > 0) {
            if (data.size() < Constants.PAGE_SIZE) {//没有更多数据
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        } else {
            String emptyStr = getString(R.string.no_data);
            ViewHelper.initEmptyView(R.layout.layout_empty_view,
                    mAdapter, recyclerView, emptyStr, R.drawable.ic_common_empty_data);
        }
        //不使用mAdapter.setNewData(data) 防止 Filter 数据过滤页面抖动问题
        mAdapter.getData().clear();
        mAdapter.getData().addAll(data);
        mAdapter.getFilter().filter(String.valueOf(filterType));
    }

    @Override
    public void onLoadMoreSuccess(List<TransferRecordBean.DataBean> data) {
        smartRefreshLayout.finishLoadMore();
        if (data.size() < Constants.PAGE_SIZE) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        }

        mAdapter.getFilter().filter(String.valueOf(filterType));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tokensBean != null)
            mPresenter.onRefresh();
    }
}