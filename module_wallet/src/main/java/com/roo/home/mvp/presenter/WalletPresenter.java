package com.roo.home.mvp.presenter;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.alibaba.fastjson.JSON;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.trade.AllBalanceBean;
import com.core.domain.trade.BalanceBean;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.model.UserWallet;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.ThreadManager;
import com.roo.core.utils.utils.BalanceManager;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.home.mvp.contract.WalletContract;
import com.roo.home.mvp.ui.adapter.WalletAdapter;
import com.subgraph.orchid.encoders.Hex;

import org.jetbrains.annotations.NotNull;
import org.simple.eventbus.EventBus;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import wallet.core.jni.Base58;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;


@ActivityScope
public class WalletPresenter extends BasePresenter<WalletContract.Model, WalletContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    WalletAdapter mAdapter;
    private ScheduledFuture<?> scheduledBalance;

    @Inject
    public WalletPresenter(WalletContract.Model model, WalletContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        if (scheduledBalance != null && !scheduledBalance.isCancelled()) {
            scheduledBalance.cancel(true);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void initBalance() {
        BalanceManager.getInstance().restore();
        EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.LOADED_BALANCE);

        scheduledBalance = ThreadManager.getScheduled()
                .scheduleAtFixedRate(() -> {
                    getBalance();
                    EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.LOADED_BALANCE);

                }, 5000, 60000, TimeUnit.MILLISECONDS);
    }

    private String getPrivateKeyToAddress(String privateKey) {
        String address = Keys.toChecksumAddress(Keys.getAddress(ECKeyPair.create(Numeric.toBigInt(privateKey))));
        byte[] decode = Hex.decode(address.replace(Constants.PREFIX_16, "41"));
        return Base58.encode(decode);
    }

    public void getBalance() {
        UserWallet userWallet = EthereumWalletUtils.getInstance()
                .getSelectedWalletOrFirst(mRootView.getActivity());
        for (UserWallet.ChainWallet chainWallet : userWallet.getChainWallets()) {

            ArrayList<String> contractId = new ArrayList<>();
            String chainCode = chainWallet.getChainCode();
            for (LinkTokenBean.TokensBean tokensBean : userWallet.getTokenList()) {
                if (tokensBean.getChainCode().equals(chainCode)) {
                    contractId.add(tokensBean.getContractId());
                }
            }
            String address;
            if (chainCode.equalsIgnoreCase(ChainCode.TRON)) {
                if (userWallet.getPrivateKey2() != null) {
                    address = getPrivateKeyToAddress(userWallet.getPrivateKey2());
                } else {
                    HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
                    address = hdWallet.getAddressForCoin(CoinType.TRON);
                }
            } else {
                address = Constants.PREFIX_16 + EthereumWalletUtils.getInstance()
                        .getWalletByChainCode(userWallet, chainCode)
                        .getWalletFile2().getAddress();
            }


            mModel.getBalance(chainCode, address, contractId)
                    .compose(RxUtils.applySchedulersLife(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<List<BalanceBean>>>(mErrorHandler) {
                        @Override
                        public void onNext(@NotNull BaseResponse<List<BalanceBean>> t) {
                            if (Kits.Empty.check(t.getData())) {
                                return;
                            }
                            for (BalanceBean item : t.getData()) {
                                BalanceManager.getInstance().put(chainCode, item.getAddress(),
                                        item.getContractId(), item.getAvailableBalance());
                            }
                            BalanceManager.getInstance().store();
                        }
                    });
        }
    }

    public void doRefresh(UserWallet userWallet, String filterAsset) {
        ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
        if (Kits.Empty.check(tokenList)) {
            mAdapter.setNewData(new ArrayList<>());
        } else {
            Observable
                    .fromIterable(tokenList)
                    .filter(t -> {
                        if (filterAsset == null) {
                            return true;
                        } else {
                            return filterAsset.equalsIgnoreCase(t.getChainCode());
                        }
                    }).toList()
                    .map(t -> {
                        Collections.sort(t, (o1, o2) -> {
                            // 返回值为int类型，大于0表示正序，小于0表示逆序
                            return o2.getSort() - o1.getSort();
                        });
                        return t;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(t -> mAdapter.setNewData(t));
        }
    }

    public void getAllBalance(Context context) {
        UserWallet userWallet = EthereumWalletUtils.getInstance()
                .getSelectedWalletOrFirst(context);
        userWallet.getListMainChainCode();
        List<String> listChains = userWallet.getListMainChainCode();

        ArrayList<AllBalanceBean> balanceBeanList = new ArrayList<>();
        for (int i = 0; i < listChains.size(); i++) {
            AllBalanceBean bean = new AllBalanceBean();

            String address;
            if (listChains.get(i).equalsIgnoreCase(ChainCode.TRON)) {
                if (userWallet.getPrivateKey2() != null) {
                    address = getPrivateKeyToAddress(userWallet.getPrivateKey2());
                } else {
                    HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
                    address = hdWallet.getAddressForCoin(CoinType.TRON);
                }
            } else {
                address = Constants.PREFIX_16 + EthereumWalletUtils.getInstance()
                        .getWalletByChainCode(userWallet, listChains.get(i))
                        .getWalletFile2().getAddress();
            }

            bean.setAddress(address);
            bean.setChainCode(listChains.get(i));
            bean.setContractIds("");
            balanceBeanList.add(bean);
        }
        if (!Kits.Empty.check(balanceBeanList)) {
            request(balanceBeanList);
        }
    }

    private void request(ArrayList<AllBalanceBean> dtos) {
        mModel.getAllBalance(dtos)
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse t) {
                        if (!Kits.Empty.check(t.getData().toString())) {
                            mRootView.setTokensData(JSON.toJSONString(t.getData()));
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable t) {
                        super.onError(t);
                        LogManage.e(Constants.LOG_STRING + JSON.toJSONString(t.getMessage()));
                    }
                });
    }
}
