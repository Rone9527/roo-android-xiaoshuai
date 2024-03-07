package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.trade.NewAssetsBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.roo.core.app.annotation.CreateType;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.daoManagers.NewAssetsDaoManager;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TokenManager;
import com.roo.home.R;
import com.roo.home.di.component.DaggerAssetSelectComponent;
import com.roo.home.mvp.contract.AssetSelectContract;
import com.roo.home.mvp.presenter.AssetSelectPresenter;
import com.roo.home.mvp.ui.adapter.AssetSelectCoinAdapter;
import com.roo.home.mvp.ui.adapter.AssetSelectLinkAdapter;
import com.roo.router.Router;

import org.simple.eventbus.EventBus;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 添加资产
 */
public class AssetSelectActivity extends BaseActivity<AssetSelectPresenter> implements AssetSelectContract.View {

    @Inject
    AssetSelectLinkAdapter mAdapterLink;
    @Inject
    AssetSelectCoinAdapter mAdapterCoin;
    private RecyclerView mRecyclerViewCoin;
    private RecyclerView recyclerViewLink;
    private TextView tvAssetsNumb;
    private List<NewAssetsBean> myAssetsBeanList;

    public static void start(Context context, List<NewAssetsBean> myAssetsBeanList, boolean isFromHome) {

        Router.newIntent(context)
                .to(AssetSelectActivity.class)
                .putSerializable(Constants.KEY_DEFAULT, (Serializable) myAssetsBeanList)
                .putBoolean(Constants.KEY_FR, isFromHome)
                .launch();
    }

    public static void start(Context context) {
        Router.newIntent(context)
                .to(AssetSelectActivity.class)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAssetSelectComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_asset_select;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar("", this).setVisibility(View.GONE);

        int barHeight = DeviceUtils.getStatuBarHeight(this);
        findViewById(R.id.layoutRoot).setPadding(0, barHeight, 0, 0);

        RxView.clicks(findViewById(R.id.ivBack)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> finish());


        RxView.clicks(findViewById(R.id.layoutInput)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> AssetSearchActivity.start(this));


        RxView.clicks(findViewById(R.id.llMyAssets)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o ->
                        MyAssetsActivity.start(this, myAssetsBeanList)
                );
        RxView.clicks(findViewById(R.id.llAutoToken)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> AddCustomAssetActivity.start(this));
        tvAssetsNumb = findViewById(R.id.tvAssetsNumb);

        recyclerViewLink = ViewHelper.initRecyclerView(mAdapterLink, this,
                new LinearLayoutManager(this), R.id.recycler_view_link);

        mRecyclerViewCoin = ViewHelper.initRecyclerView(mAdapterCoin, this);

        mAdapterLink.setOnItemClickListener((adapter, view, position) -> {
            for (LinkTokenBean t : mAdapterLink.getData()) {
                t.setSelect(false);
            }
            LinkTokenBean item = mAdapterLink.getItem(position);
            item.setSelect(true);
            mAdapterLink.notifyDataSetChanged();

            mAdapterCoin.setNewData(item.getTokens());
        });
        mAdapterCoin.setOnItemChildClickListener((adapter, view, position) -> {
            if (ClickUtil.clickInFronzen()) {
                return;
            }
            if (view.getId() == R.id.ivAssetAdd) {

                UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);
                LinkTokenBean.TokensBean selected = mAdapterCoin.getItem(position);
                List<String> listMainChainCode = userWallet.getListMainChainCode();
                //没有这个主链，就去创建主链钱包
                if (Kits.Empty.check(listMainChainCode) || !listMainChainCode.contains(selected.getChainCode())) {
                    createWallets(userWallet, selected.getChainCode());
                }

                ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();

                if (TokenManager.getInstance().isTokenHasSelected(selected)) {
                    if (selected.isMain()) {
                        ToastUtils.show(R.string.toast_main_forbidden_delete);
                        return;
                    }
                    Iterator<LinkTokenBean.TokensBean> iterator = tokenList.iterator();
                    while (iterator.hasNext()) {
                        LinkTokenBean.TokensBean next = iterator.next();
                        if (Objects.equals(next.getChainCode(), selected.getChainCode())
                                && next.getSymbol().equalsIgnoreCase(selected.getSymbol())) {
                            iterator.remove();
                            break;
                        }
                    }
                } else {

                    tokenList.add(selected);

                    ArrayList<LinkTokenBean.NodesBean> chainNode = TokenManager.getInstance().getChainNode();
                    if (!Kits.Empty.check(chainNode)) {
                        userWallet.setNodes(chainNode);
                    }


                }
                userWallet.setTokenList(tokenList);

                EthereumWalletUtils.getInstance().updateWallet(this, userWallet, false);

                mAdapterCoin.setTokenMap();
                mAdapterCoin.notifyDataSetChanged();

                UserWallet.ChainWallet chainWallet = EthereumWalletUtils.getInstance()
                        .getWalletByChainCode(userWallet, selected.getChainCode());

                if (chainWallet != null) {
                    String address = Constants.PREFIX_16 + chainWallet.getWalletFile().getAddress();
                    mPresenter.onTokenAdd(selected.getChainCode(),
                            address, selected.getContractId());
                }

                EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.UPLOAD_JPUSH);

            }
        });
        myAssetsBeanList = (List<NewAssetsBean>) getIntent().getSerializableExtra(Constants.KEY_DEFAULT);
        if (!getIntent().getBooleanExtra(Constants.KEY_FR, false)) {
            findViewById(R.id.llTop).setVisibility(View.GONE);
        } else {
            setNum(myAssetsBeanList);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mAdapterCoin != null) {
            mAdapterCoin.setTokenMap();
            mAdapterCoin.notifyDataSetChanged();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void loadData(List<LinkTokenBean> data) {
        if (Kits.Empty.check(data)) {
            recyclerViewLink.setVisibility(View.GONE);
            findViewById(R.id.layoutHeader).setVisibility(View.GONE);
            ViewHelper.initEmptyView(mAdapterCoin, mRecyclerViewCoin);
            return;
        } else {
            recyclerViewLink.setVisibility(View.VISIBLE);
            findViewById(R.id.layoutHeader).setVisibility(View.VISIBLE);
            ArrayList<LinkTokenBean.TokensBean> dataSet = new ArrayList<>();
            for (LinkTokenBean linkTokenBean : data) {
                for (LinkTokenBean.TokensBean token : linkTokenBean.getTokens()) {
                    if (token.isIsRecommend()) {
                        dataSet.add(token);
                    }
                }
            }
            mAdapterCoin.setNewData(dataSet);
        }
        mAdapterLink.setNewData(data);
    }

    private void createWallets(UserWallet userWallet, String chainCode) {
        switch (chainCode) {
            case ChainCode.ETH:
            case ChainCode.BSC:
            case ChainCode.HECO:
            case ChainCode.OEC:
            case ChainCode.TRON:
            case ChainCode.POLYGON:
            case ChainCode.FANTOM:
                //创建的钱包或者助记词导入的钱包，使用助记词生成钱包
                if (userWallet.getCreateType() == CreateType.CREATE || userWallet.getCreateType() == CreateType.WORDS) {
                    //助记词创建钱包
                    createEthWalletByPhrase(userWallet, chainCode);
                } else {
                    //私钥导入
                    createEthWalletByPK(userWallet, chainCode);
                }
                break;
            case ChainCode.BTC:

                break;
            default:
                break;
        }
    }

    //以太坊通过助记词创建钱包
    private void createEthWalletByPhrase(UserWallet userWallet, String chainCode) {
        try {
            ECKeyPair ecKeyPair = EthereumWalletUtils.getInstance().generateKeyPair(userWallet.getMnemonics());
            WalletFile walletFile = Wallet.createLight(SafePassword.get(), ecKeyPair);
            userWallet.getListMainChainCode().add(chainCode);
            UserWallet.ChainWallet chainWallet = new UserWallet.ChainWallet();
            chainWallet.setWalletFile(walletFile);
            chainWallet.setChainCode(chainCode);
            userWallet.getChainWallets().add(chainWallet);
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    private void createEthWalletByPK(UserWallet userWallet, String chainCode) {
        try {
            ECKeyPair ecKeyPair = EthereumWalletUtils.getInstance().getKeyPair(userWallet.getPrivateKey());
            WalletFile walletFile = Wallet.createLight(SafePassword.get(), ecKeyPair);

            LogManage.e(Constants.LOG_STRING, JSON.toJSONString(walletFile));
            UserWallet.ChainWallet chainWallet = new UserWallet.ChainWallet();
            userWallet.getListMainChainCode().add(chainCode);
            chainWallet.setWalletFile(walletFile);
            chainWallet.setChainCode(chainCode);
            userWallet.getChainWallets().add(chainWallet);
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    private void setNum(List<NewAssetsBean> myAssetsBeanList) {

        String name = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this).getRemark();

        int unAddedAssetsAmount = NewAssetsDaoManager.getUnAddedAmount(myAssetsBeanList, name);
        if (unAddedAssetsAmount <= 0) {
            tvAssetsNumb.setVisibility(View.GONE);
        } else {
            tvAssetsNumb.setVisibility(View.VISIBLE);
        }
        if (unAddedAssetsAmount > 99) {
            unAddedAssetsAmount = 99;
        }
        tvAssetsNumb.setText(unAddedAssetsAmount + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Kits.Empty.check(myAssetsBeanList)) {
            setNum(myAssetsBeanList);
        }
    }
}