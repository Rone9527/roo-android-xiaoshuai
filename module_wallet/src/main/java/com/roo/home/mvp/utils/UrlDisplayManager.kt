package com.roo.home.mvp.utils

import com.core.domain.BuildConfig
import com.core.domain.manager.ChainCode
import com.roo.core.app.constants.GlobalConstant


class UrlDisplayManager {
//    var ETHEREUM = 60 //ETH
//    var BITCOIN = 0 //BTC
//    var FILECOIN = 461 //FIL
//    var TRON = 195 //TRX
//    var HT = 1010 //HT
//    var BNB = 42840 //BNB

    //    ChainCode.ETH,
//    ChainCode.BSC,
//    ChainCode.HECO,
//    ChainCode.OKT,
//    ChainCode.MATIC,
//    ChainCode.POLYGON,
//    ChainCode.BTC,
//    ChainCode.TRON
    var toggle: Boolean? = false

    fun display(chainCode: String): String {
        return when (chainCode) {
            ChainCode.BTC -> if (toggle == true) GlobalConstant.DEBUG.BTCSCAN_URL else GlobalConstant.RELEASE.BTCSCAN_URL
            ChainCode.ETH -> if (toggle == true) GlobalConstant.DEBUG.ETHSCAN_URL else GlobalConstant.RELEASE.ETHSCAN_URL
            ChainCode.FILECOIN -> if (toggle == true) GlobalConstant.DEBUG.FILSCAN_URL else GlobalConstant.RELEASE.FILSCAN_URL
            ChainCode.TRON -> if (toggle == true) GlobalConstant.DEBUG.TRXSCAN_URL else GlobalConstant.RELEASE.TRXSCAN_URL
            ChainCode.HECO -> if (toggle == true) GlobalConstant.DEBUG.HTSCAN_URL else GlobalConstant.RELEASE.HTSCAN_URL
            ChainCode.BSC -> if (toggle == true) GlobalConstant.DEBUG.BNBSCAN_URL else GlobalConstant.RELEASE.BNBSCAN_URL
            ChainCode.OEC -> if (toggle == true) GlobalConstant.DEBUG.OEC_URL else GlobalConstant.RELEASE.OEC_URL
            ChainCode.POLYGON -> if (toggle == true) GlobalConstant.DEBUG.POLYGON_URL else GlobalConstant.RELEASE.POLYGON_URL
            ChainCode.FANTOM -> if (toggle == true) GlobalConstant.DEBUG.FANTOM_URL else GlobalConstant.RELEASE.FANTOM_URL
            else -> { // 注意这个块
                if (toggle == true) GlobalConstant.DEBUG.ETHSCAN_URL else GlobalConstant.RELEASE.ETHSCAN_URL
            }
        }
    }
}