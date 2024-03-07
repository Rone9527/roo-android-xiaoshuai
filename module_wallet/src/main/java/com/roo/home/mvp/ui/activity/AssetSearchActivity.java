package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.billy.cc.core.component.CC;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.roo.core.app.annotation.CreateType;
import com.roo.core.app.component.Main;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TokenManager;
import com.roo.home.R;
import com.roo.home.di.component.DaggerAssetSearchComponent;
import com.roo.home.mvp.contract.AssetSearchContract;
import com.roo.home.mvp.presenter.AssetSearchPresenter;
import com.roo.home.mvp.ui.adapter.AssetSearchAdapter;
import com.roo.router.Router;

import org.simple.eventbus.EventBus;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 资产搜索
 */
public class AssetSearchActivity extends BaseActivity<AssetSearchPresenter> implements AssetSearchContract.View {
    @Inject
    AssetSearchAdapter mAdapter;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(AssetSearchActivity.class)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAssetSearchComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_asset_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar("", this).setVisibility(View.GONE);
        int barHeight = DeviceUtils.getStatuBarHeight(this);
        findViewById(R.id.layoutRoot).setPadding(0, barHeight, 0, 0);
        EditText editText = findViewById(R.id.editText);

        RxView.clicks(findViewById(R.id.ivBack)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> finish());
        RecyclerView recyclerView = ViewHelper.initRecyclerView(mAdapter, this);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (ClickUtil.clickInFronzen()) {
                return;
            }
            if (view.getId() == R.id.ivAssetAdd) {
                LinkTokenBean.TokensBean tokensBean = mAdapter.getItem(position);
                addAsset(tokensBean, false);
            }
        });

        View footerView = ViewHelper.initFooterView(R.layout.layout_footer_add_asset, mAdapter, recyclerView);
        RxView.clicks(footerView.findViewById(R.id.tvAddAsset)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> AddCustomAssetActivity.start(this));

        View emptyView = LayoutInflater.from(this)
                .inflate(R.layout.lyaout_add_asset_empty_view,
                        (ViewGroup) recyclerView.getParent(), false);
        mAdapter.setEmptyView(emptyView);
        RxView.clicks(emptyView.findViewById(R.id.tvAddAsset)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> AddCustomAssetActivity.start(this));

        ImageView ivCancel = findViewById(R.id.ivCancel);
        RxView.clicks(ivCancel).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> editText.setText(""));

        RxTextView.textChanges(editText).skipInitialValue()
                .debounce(200, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applySchedulers())
                .map(CharSequence::toString)
                .subscribe(t -> {
                    ivCancel.setVisibility(TextUtils.isEmpty(t) ? View.GONE : View.VISIBLE);
                    mPresenter.filter(t.trim().toUpperCase());
                });

    }

    private void addAsset(LinkTokenBean.TokensBean tokensBean, boolean addCustom) {
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);


        List<String> listMainChainCode = userWallet.getListMainChainCode();
        //没有这个主链，就去创建主链钱包
        if (Kits.Empty.check(listMainChainCode) || !listMainChainCode.contains(tokensBean.getChainCode())) {
            createWallets(userWallet, tokensBean.getChainCode());
        }

        ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
        if (TokenManager.getInstance().isTokenHasSelected(tokensBean)) {
            if (tokensBean.isMain()) {
                ToastUtils.show(R.string.toast_main_forbidden_delete);
                return;
            }
            Iterator<LinkTokenBean.TokensBean> iterator = tokenList.iterator();
            while (iterator.hasNext()) {
                LinkTokenBean.TokensBean next = iterator.next();
                if (Objects.equals(next.getChainCode(), tokensBean.getChainCode())
                        && next.getSymbol().equalsIgnoreCase(tokensBean.getSymbol())) {
                    iterator.remove();
                    break;
                }
            }
        } else {
            //添加到首位
            tokenList.add(0,tokensBean);

            ArrayList<LinkTokenBean.NodesBean> chainNode = TokenManager.getInstance().getChainNode();
            if (!Kits.Empty.check(chainNode)) {
                userWallet.setNodes(chainNode);
            }

        }
        userWallet.setTokenList(tokenList);

        EthereumWalletUtils.getInstance().updateWallet(this, userWallet, false);

        mAdapter.setTokenMap();
        mAdapter.notifyDataSetChanged();

        String address = Constants.PREFIX_16 + EthereumWalletUtils.getInstance()
                .getWalletByChainCode(userWallet, tokensBean.getChainCode()).getWalletFile().getAddress();
        mPresenter.onTokenAdd(tokensBean.getChainCode(),
                address, tokensBean.getContractId());

        if (addCustom) {
            CC.obtainBuilder(Main.NAME)
                    .setContext(this)
                    .setActionName(Main.Action.MainActivity)
                    .build().call();
            ToastUtils.show(R.string.success_add);
            setResult(RESULT_OK);
            finish();
        } else {
            EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.UPLOAD_JPUSH);
        }
    }


    private void createWallets(UserWallet userWallet, String chainCode) {
        switch (chainCode) {
            case ChainCode.ETH:
            case ChainCode.BSC:
            case ChainCode.HECO:
            case ChainCode.TRON:
            case ChainCode.OEC:
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (requestCode == AddCustomAssetActivity.REQUEST_CODE_CUSTOMASSET) {
                LinkTokenBean.TokensBean tokensBean = Objects.requireNonNull(data)
                        .getParcelableExtra(Constants.KEY_DEFAULT);

                addAsset(tokensBean, true);
            }
        }

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

}