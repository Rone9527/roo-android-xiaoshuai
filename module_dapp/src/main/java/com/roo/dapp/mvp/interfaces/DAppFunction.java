package com.roo.dapp.mvp.interfaces;

public interface DAppFunction {
    void DAppError(Throwable error, Signable message);

    void DAppReturn(byte[] data, Signable message);
}
