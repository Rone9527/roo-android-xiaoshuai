package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.jess.arms.di.component.AppComponent;
import com.roo.core.app.component.Main;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.home.R;
import com.roo.home.mvp.ui.adapter.WalletListAdapter;
import com.roo.router.Router;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 管理钱包-钱包列表
 */
public class WalletListActivity extends I18nActivityArms {

    private WalletListAdapter mAdapter;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(WalletListActivity.class)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wallet_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        TitleBarView mTitleBarView = ViewHelper.initTitleBar(getString(R.string.title_manage_wallets), this);
        mTitleBarView.addRightAction(mTitleBarView.new TextAction(getString(R.string.t_add), v -> {
            WalletCreateNormalActivity.start(this);
        }));
        mAdapter = new WalletListAdapter();
        RecyclerView recyclerView = ViewHelper.initRecyclerView(mAdapter, this);
        ViewHelper.initEmptyView(mAdapter, recyclerView);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (ClickUtil.clickInFronzen()) {
                return;
            }
            CC.obtainBuilder(Wallet.NAME)
                    .setContext(this)
                    .addParam(Constants.KEY_DEFAULT, mAdapter.getItem(position).getRemark())
                    .setActionName(Wallet.Action.WalletManagerActivity)
                    .build().call();
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //没有钱包自动跳转到创建钱包页面后，如果用户点击了返回则关闭应用
        if (Kits.Empty.check(EthereumWalletUtils.getInstance().getWallet(this))) {
            CC.obtainBuilder(Main.NAME)
                    .setContext(this)
                    .addParam(Main.Param.EXIT, true)//关闭应用
                    .setActionName(Main.Action.MainActivity)
                    .build().call();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataForAdapter();
    }

    private void setDataForAdapter() {
        ArrayList<UserWallet> wallets = EthereumWalletUtils.getInstance().getWallet(this);
        Collections.sort(wallets, new Comparator<UserWallet>() {
            @Override
            public int compare(UserWallet o1, UserWallet o2) {
                // 返回值为int类型，大于0表示正序，小于0表示逆序
                return (int) (o1.getCreateTime() - o2.getCreateTime());
            }
        });
        mAdapter.setNewData(wallets);
    }
}