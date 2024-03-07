package com.roo.dapp.mvp.contract;

import android.app.Activity;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.mine.GasBean;
import com.core.domain.trade.SignatureFromKey;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;
import com.roo.dapp.mvp.interfaces.Signable;
import com.roo.dapp.mvp.beans.TransactionData;
import com.roo.dapp.mvp.beans.Web3Transaction;
import com.roo.dapp.mvp.utils.autoWeb3.Web3View;

import org.web3j.crypto.WalletFile;

import java.math.BigInteger;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface DappBrowserContract {
    interface View extends IView {

        Activity getActivity();

        void getGasDataSet(GasBean data);

        Web3View getWeb3View();

    }

    interface Model extends IModel {

        Single<TransactionData> createWithSig(LinkTokenBean.TokensBean tokensBean,
                                              WalletFile walletFile,
                                              Web3Transaction web3Tx,
                                              int chainId);

        Single<TransactionData> createWithSig(LinkTokenBean.TokensBean tokensBean,
                                              WalletFile walletFile,
                                              BigInteger gasPrice,
                                              BigInteger gasLimit,
                                              String payload,
                                              int chainId);

        SignatureFromKey signMessage(Signable message, WalletFile walletFile);

        Observable<BaseResponse<GasBean>> getGasDataSet(String chainCode);

    }
}