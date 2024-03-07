package com.roo.dapp.mvp.interfaces;

import com.roo.dapp.mvp.beans.Web3Transaction;

/**
 * Created by James on 26/01/2019.
 * Stormbird in Singapore
 */
public interface SendTransactionInterface {
    void transactionSuccess(Web3Transaction web3Tx, String hashData);

    void transactionError(long callbackId, Throwable error);
}
