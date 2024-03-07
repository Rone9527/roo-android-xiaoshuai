package com.roo.dapp.mvp.interfaces;

import com.roo.dapp.mvp.beans.Web3Transaction;

public interface OnSignTransactionListener {
    void onSignTransaction(Web3Transaction transaction, String url);
}
