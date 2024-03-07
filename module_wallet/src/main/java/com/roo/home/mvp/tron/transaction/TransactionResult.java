package com.roo.home.mvp.tron.transaction;

import com.alibaba.fastjson.JSONObject;

/**
 * 交易广播结果
 *
 * @author: sunlight
 * @date: 2020/7/27 17:41
 */

public class TransactionResult {


    /**
     * 交易Hash
     */
    private String hash;
    /**
     * 广播结果
     */
    private Boolean result;
    /**
     * 广播状态
     */
    private String code;
    /**
     * 消息
     */
    private String message;
    private String txid;

    public TransactionResult(String txid, Boolean result, String code, String message) {
        this.txid = txid;
        this.result = result;
        this.code = code;
        this.message = message;

    }

    public static TransactionResult parse(String result) {
        JSONObject jsonResult = JSONObject.parseObject(result);
        return new TransactionResult(
                jsonResult.getString("txid"),
                jsonResult.getBoolean("result"),
                jsonResult.getString("code"),
                jsonResult.getString("message")
        );
    }
}
