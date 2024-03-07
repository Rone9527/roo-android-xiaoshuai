package com.roo.dapp.mvp.presenter;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.mine.GasBean;
import com.core.domain.trade.SignatureFromKey;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.model.UserWallet;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.dapp.mvp.beans.Web3Transaction;
import com.roo.dapp.mvp.contract.DappBrowserContract;
import com.roo.dapp.mvp.interfaces.SendTransactionInterface;
import com.roo.dapp.mvp.interfaces.Signable;

import org.jetbrains.annotations.NotNull;
import org.web3j.crypto.WalletFile;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@FragmentScope
public class DappBrowserPresenter extends BasePresenter<DappBrowserContract.Model, DappBrowserContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public DappBrowserPresenter(DappBrowserContract.Model model, DappBrowserContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }


    public void sendTransaction(LinkTokenBean.TokensBean tokensBean, Web3Transaction tx,
                                int chainId, SendTransactionInterface callback) {
        WalletFile walletFile = EthereumWalletUtils.getInstance()
                .getWalletByChainCode(
                        EthereumWalletUtils.getInstance().getSelectedWalletOrNull(mRootView.getActivity()),
                        tokensBean.getChainCode()).getWalletFile();
        Web3Transaction web3Transaction = tx;//=formatTx(tx);

        if (web3Transaction.isConstructor()) {
            mModel.createWithSig(tokensBean, walletFile, web3Transaction.gasPrice, web3Transaction.gasLimit, web3Transaction.payload, chainId)
                    .subscribe(txData -> callback.transactionSuccess(web3Transaction, txData.txHash),
                            error -> callback.transactionError(web3Transaction.leafPosition, error));
        } else {
            mModel.createWithSig(tokensBean, walletFile, web3Transaction, chainId)
                    .subscribe(txData -> callback.transactionSuccess(web3Transaction, txData.txHash),
                            error -> callback.transactionError(web3Transaction.leafPosition, error));
        }
    }

    public SignatureFromKey signMessage(Signable messageTBS, LinkTokenBean.TokensBean tokensBean) {

        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(mRootView.getActivity());
        WalletFile walletFile = EthereumWalletUtils.getInstance()
                .getWalletByChainCode(userWallet, tokensBean.getChainCode()).getWalletFile();

        return mModel.signMessage(messageTBS, walletFile);
    }

    public void getGasDataSet(String chainCode) {
        mModel.getGasDataSet(chainCode).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<GasBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<GasBean> t) {
                        mRootView.getGasDataSet(t.getData());
                    }
                });
    }
}