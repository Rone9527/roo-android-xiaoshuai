package com.roo.dapp.mvp.beans;

import com.core.domain.dapp.EthereumNetworkBase;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.dapp.mvp.utils.Numeric;

import java.math.BigInteger;

public class WalletAddEthereumChainObject {
    public static class NativeCurrency {
        String name;
        String symbol;
        int decimals;
    }

    public NativeCurrency nativeCurrency;
    public String[] blockExplorerUrls;
    public String chainName;
    public String chainType; //ignore this
    public String chainId; //this is a hex number with "0x" prefix. If it is without "0x", process it as dec
    public String[] rpcUrls;

    public int getChainId() {
        try {
            if (Numeric.containsHexPrefix(chainId)) {
                return Numeric.toBigInt(chainId).intValue();
            } else {
                return new BigInteger(chainId).intValue();
            }
        } catch (NumberFormatException e) {
            if (GlobalConstant.DEBUG_MODEL) e.printStackTrace();
            return (0);
        }
    }

    public String getRpcUrl() {
        EthereumNetworkBase.getChainCodeByChainId(getChainId());
        return null;
    }
}
