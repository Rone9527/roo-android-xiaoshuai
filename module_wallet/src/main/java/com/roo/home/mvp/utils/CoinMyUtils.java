package com.roo.home.mvp.utils;


import com.core.domain.manager.ChainCode;

import wallet.core.jni.CoinType;

public class CoinMyUtils {

    public static final String FIL = "FIL";

    public static CoinType getCoinType(String chainCode) {
        CoinType mCoinType = CoinType.ETHEREUM;
        if (chainCode.equalsIgnoreCase(ChainCode.FILECOIN)) {
            mCoinType = CoinType.FILECOIN;
        } else if (chainCode.equalsIgnoreCase(ChainCode.BTC)) {
            mCoinType = CoinType.BITCOIN;
        } else if (chainCode.equalsIgnoreCase(ChainCode.TRON)) {
            mCoinType = CoinType.TRON;
        } else if (chainCode.equalsIgnoreCase(ChainCode.ETH)) {
            mCoinType = CoinType.ETHEREUM;
        } else if (chainCode.equalsIgnoreCase(ChainCode.POLYGON)) {
            mCoinType = CoinType.POLYGON;
        }else if (chainCode.equalsIgnoreCase(ChainCode.FANTOM)) {
            mCoinType = CoinType.ETHEREUM;
        }
        return mCoinType;
    }


    /**
     * 将byte转换成baiint，然后利用Integer.toHexString(int)
     * *来转换成16进制字符串。
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
