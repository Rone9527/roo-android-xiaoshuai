package com.core.domain.dapp;

/* Weiwu 12 Jan 2020: This class eventually will replace the EthereumNetworkBase class in :app
 * one all interface methods are implemented.
 */

import com.core.domain.manager.ChainCode;

public abstract class EthereumNetworkBase { // implements EthereumNetworkRepositoryType
    public static final int ETH = 1;
    public static final int ROPSTEN = 3;
    public static final int RINKEBY = 4;
    public static final int GOERLI = 5;
    public static final int KOVAN = 42;
    public static final int BSC = 56;
    public static final int BSC_TEST = 97;
    public static final int HECO = 128;
    public static final int HECO_TEST = 256;
    public static final int OEC = 66;
    public static final int OEC_TEST = 66;
    public static final int POLYGON = 137;
    public static final int POLYGON_TEST = 80001;
    public static final int FANTOM = 250;
    public static final int FANTOM_TEST = 250;

    public static final int CLASSIC_ID = 61;
    public static final int POA_ID = 99;
    public static final int SOKOL_ID = 77;
    public static final int XDAI_ID = 100;
    public static final int ARTIS_SIGMA1_ID = 246529;
    public static final int ARTIS_TAU1_ID = 246785;
    public static final int FANTOM_ID = 250;
    public static final int FANTOM_TEST_ID = 4002;
    public static final int AVALANCHE_ID = 43114;
    public static final int FUJI_TEST_ID = 43113;
    public static final int OPTIMISTIC_MAIN_ID = 10;
    public static final int OPTIMISTIC_TEST_ID = 69;


    public static int getChainIdByChainCode(boolean rpcType, String chainCode) {
        switch (chainCode) {
            case ChainCode.ETH:
                return rpcType ? ETH : RINKEBY;//Rinkeby 测试网络--4; Kovan 测试网络--42;Ropsten 测试网络--3;Goerli 测试网络--5
            case ChainCode.BSC:
                return rpcType ? BSC : BSC_TEST;
            case ChainCode.HECO:
                return rpcType ? HECO : HECO_TEST;
            case ChainCode.OEC:
                return rpcType ? OEC : OEC_TEST;
            case ChainCode.POLYGON:
                return rpcType ? POLYGON : POLYGON_TEST;
            case ChainCode.FANTOM:
                return rpcType ? FANTOM : FANTOM_TEST;
            case ChainCode.TRON:
                return 195;
        }
        return 1;
    }

    public static String getChainCodeByChainId(int ChainId) {
        switch (ChainId) {
            case ETH:
                return ChainCode.ETH;
            case BSC:
                return ChainCode.BSC;
            case HECO:
                return ChainCode.HECO;
            case OEC:
                return ChainCode.OEC;
            case POLYGON:
                return ChainCode.POLYGON;
            case FANTOM:
                return ChainCode.FANTOM;
        }
        return ChainCode.ETH;
    }
}
