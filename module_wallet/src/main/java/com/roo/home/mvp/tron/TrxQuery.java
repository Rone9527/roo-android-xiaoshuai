package com.roo.home.mvp.tron;

import com.alibaba.fastjson.JSONObject;

import org.tron.protos.Protocol;
import org.tron.walletserver.WalletApi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * TRX查询工具类
 *
 * @author: sunlight
 * @date: 2020/7/28 11:04
 */
public class TrxQuery {

    /**
     * 查询最新区块数据
     *
     * @return 数据
     * @throws Exception 异常
     */
    public static Protocol.Block getLatestBlock() throws Exception {
        Protocol.Block newestBlock = WalletApi.getBlock(-1L);
        return newestBlock;
    }

}
