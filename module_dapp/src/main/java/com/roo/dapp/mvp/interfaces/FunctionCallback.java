package com.roo.dapp.mvp.interfaces;


/**
 * Created by James on 6/04/2019.
 * Stormbird in Singapore
 */
public interface FunctionCallback {
    void signMessage(Signable sign, DAppFunction dAppFunction);

    void functionSuccess();

    void functionFailed();
}
