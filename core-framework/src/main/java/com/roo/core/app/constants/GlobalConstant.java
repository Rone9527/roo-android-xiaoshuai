package com.roo.core.app.constants;

/**
 * @author oldnine
 * @since 2018/11/7
 */
public interface GlobalConstant {
    /*日志是否是Debug模式*/
    boolean DEBUG_MODEL = true;

    /*服务器类型-公司*/
    boolean SERVER_TYPE = true ;

    /*RPC接口类型-主网--测试网*/
    boolean RPC_TYPE_MAIN = true;

    String BASE_URL = SERVER_TYPE ? "https://api.faithwalletapi.com/" : "http://192.168.4.9/";

    String URL_WS = SERVER_TYPE ? "wss://api.faithwalletapi.com/ws" : "ws://192.168.4.9/ws";

    //波场节点
//    String BASE_NODE_TRON = SERVER_TYPE ? "https://api.shasta.trongrid.io" : "https://api.trongrid.io";
    String BASE_NODE_TRON = "https://api.trongrid.io";
//    String BASE_NODE_TRON = "http://47.243.167.199:8090";

    public static final class DEBUG {
        //浏览器测试网地址
        public static final String ETHSCAN_URL = "https://ropsten.etherscan.io/tx/";
        public static final String FILSCAN_URL = "https://filfox.info/en/message/";
        public static final String TRXSCAN_URL = "https://shasta.tronscan.org/#/transaction/";
        public static final String BTCSCAN_URL = "https://live.blockcypher.com/btc/tx/";
        public static final String BNBSCAN_URL = "https://testnet.bscscan.com/tx/";
        public static final String HTSCAN_URL = "https://testnet.hecoinfo.com/tx/";
        public static final String POLYGON_URL = "https://mumbai.polygonscan.com/tx/";
        public static final String OEC_URL = "https://www.oklink.com/okexchain-test/tx/";
        public static final String FANTOM_URL = "https://testnet.ftmscan.com/tx/";
    }

    public static final class RELEASE {
        ////浏览器主网地址
        public static final String ETHSCAN_URL = "https://cn.etherscan.com/tx/";
        public static final String FILSCAN_URL = "https://filfox.info/en/message/";
        public static final String TRXSCAN_URL = "https://tronscan.io/#/transaction/";
        public static final String BTCSCAN_URL = "https://live.blockcypher.com/btc/tx/";
        public static final String BNBSCAN_URL = "https://bscscan.com/tx/";
        public static final String HTSCAN_URL = "https://hecoinfo.com/tx/";
        public static final String POLYGON_URL = "https://polygonscan.com/tx/";
        public static final String OEC_URL = "https://www.oklink.com/okexchain/tx/";
        public static final String FANTOM_URL = "https://ftmscan.com/tx/";
    }

}
