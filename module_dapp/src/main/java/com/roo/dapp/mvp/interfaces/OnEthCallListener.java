package com.roo.dapp.mvp.interfaces;

import com.roo.dapp.mvp.utils.autoWeb3.Web3Call;

/**
 * Created by JB on 19/02/2021.
 */
public interface OnEthCallListener {
    void onEthCall(Web3Call txdata);
}
