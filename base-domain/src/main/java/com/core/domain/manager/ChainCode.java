package com.core.domain.manager;


import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        ChainCode.ETH,
        ChainCode.BSC,
        ChainCode.HECO,
        ChainCode.OEC,
        ChainCode.BTC,
        ChainCode.TRON,
        ChainCode.FILECOIN,
        ChainCode.POLYGON,
        ChainCode.FANTOM,
})
@Retention(RetentionPolicy.SOURCE)
public @interface ChainCode {
    String ETH = "ETH";
    String BSC = "BSC";
    String HECO = "HECO";
    String BTC = "BTC";
    String OEC = "OEC";
    String POLYGON = "POLYGON";
    String TRON = "TRON";
    String FILECOIN = "FILECOIN";
    String FANTOM = "FANTOM";
}
