package com.roo.home.mvp.presenter;

import android.app.Application;

import com.core.domain.manager.ChainCode;
import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.TransactionResult;
import com.core.domain.trade.BalanceBean;
import com.core.domain.wallet.FreezeBalanceBean;
import com.core.domain.wallet.TronAccountInfo;
import com.core.domain.wallet.TronAccountResource;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.home.mvp.contract.TronBandwidthEnergyContract;
import com.roo.home.mvp.utils.Base58Check;
import com.subgraph.orchid.encoders.Hex;

import org.jetbrains.annotations.NotNull;
import org.tron.common.utils.ByteArray;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;

@ActivityScope
public class TronBandwidthEnergyPresenter extends BasePresenter<TronBandwidthEnergyContract.Model, TronBandwidthEnergyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TronBandwidthEnergyPresenter(TronBandwidthEnergyContract.Model model, TronBandwidthEnergyContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getAccountResource(String address) {
        HashMap address1 = new HashMap<>();
        address1.put("address", address);
        Gson gson = new Gson();
        String postInfoStr = gson.toJson(address1);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), postInfoStr);
        mModel.getAccountResource(body).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<TronAccountResource>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull TronAccountResource t) {
                        mRootView.getAccountResourceSuccess(t);
                    }

                    @Override
                    public void onError(@NotNull Throwable t) {
                        super.onError(t);
                        LogManage.e("t---" + t.getLocalizedMessage());
                    }
                });
    }

    public String getPrivateKeyToAddress(String privateKey) {
        String address = Keys.toChecksumAddress(Keys.getAddress(ECKeyPair.create(Numeric.toBigInt(privateKey))));
        byte[] decode = Hex.decode(address.replace(Constants.PREFIX_16, "41"));
        return Base58Check.bytesToBase58(decode);
    }

    public void getBalance() {
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(mApplication);
        String address;
        if (userWallet.getPrivateKey2() != null) {
            address = getPrivateKeyToAddress(userWallet.getPrivateKey2());
        } else {
            HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
            address = hdWallet.getAddressForCoin(CoinType.TRON);
        }
        mModel.getBalance(ChainCode.TRON, address, new ArrayList<>())
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<BalanceBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<BalanceBean>> t) {
                        mRootView.getBalanceSuccess(t);
                    }
                });
    }


    public void freezeBalance(String jsonString) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonString);
        mModel.freezeBalance(body).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<ResponseBody>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull ResponseBody t) {
                        mRootView.getFreezeBalanceSuccess(t);
                    }

                    @Override
                    public void onError(@NotNull Throwable t) {
                        super.onError(t);
                        LogManage.e("t---" + t.getLocalizedMessage());
                    }
                });
    }
    public void unFreezeBalance(String jsonString) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonString);
        mModel.unFreezeBalance(body).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<ResponseBody>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull ResponseBody t) {
                        mRootView.unFreezeBalanceSuccess(t);

                    }

                    @Override
                    public void onError(@NotNull Throwable t) {
                        super.onError(t);
                        LogManage.e("t---" + t.getLocalizedMessage());
                    }
                });
    }

    public void broadcastTransaction(String transaction) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), transaction);
        mModel.broadcastTransaction(body).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<TransactionResult>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull TransactionResult t) {
                        mRootView.broadcastHexDataSet(t);
                    }
                });
    }

    public void broadcastHex(String signedTransactionBytes) {

        Map<String, String> params = new HashMap<>(1);
        params.put("transaction", signedTransactionBytes);
        String postInfoStr = new Gson().toJson(params);
        LogManage.e("postInfoStr--->"+postInfoStr);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), postInfoStr);
        mModel.broadcastHex(body).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<TransactionResult>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull TransactionResult t) {
                        mRootView.broadcastHexDataSet(t);
                    }
                });

    }

    public void getTronAccountInfo(String address) {
        HashMap address1 = new HashMap<>();
        address1.put("address", address);
        Gson gson = new Gson();
        String postInfoStr = gson.toJson(address1);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), postInfoStr);
        mModel.getTronAccountInfo(body).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<TronAccountInfo>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull TronAccountInfo t) {
                        mRootView.getTronAccountInfoSuccess(t);
                    }
                });
    }




//    public void getNowBlock() {
//        mModel.getNowBlock().compose(RxUtils.applySchedulersLife(mRootView))
//                .subscribe(new ErrorHandleSubscriber<BlockHeader>(mErrorHandler) {
//                    @Override
//                    public void onNext(@NotNull BlockHeader t) {
//                        mRootView.getNowBlockDataSet(t);
//                    }
//                });
//    }


}