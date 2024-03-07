package com.core.domain.trade;

public class DeFiTradeBean {
    public DeFiTradeBean() {
    }

    /**
     * "txid": "0x822688f0baf6d1fc821c1fe719cd221063873bb91c356d2cdc82364860939b32-0",
     * "timestamp": 1627859169,
     * "type": "mint",---------------------- buy(买入)、sell(卖出)、mint(增加流动性)、burn(减少流动性)")
     * "token0Symbol": "WBTC",
     * "token1Symbol": "USDC",
     * "token0Amount": "0.07828939",
     * "token1Amount": "3176.879387"
     */
    private String txid;
    private long timestamp;
    private String type;
    private String token0Symbol;
    private String token1Symbol;
    private String token0Amount;
    private String token1Amount;

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken0Symbol() {
        return token0Symbol;
    }

    public void setToken0Symbol(String token0Symbol) {
        this.token0Symbol = token0Symbol;
    }

    public String getToken1Symbol() {
        return token1Symbol;
    }

    public void setToken1Symbol(String token1Symbol) {
        this.token1Symbol = token1Symbol;
    }

    public String getToken0Amount() {
        return token0Amount;
    }

    public void setToken0Amount(String token0Amount) {
        this.token0Amount = token0Amount;
    }

    public String getToken1Amount() {
        return token1Amount;
    }

    public void setToken1Amount(String token1Amount) {
        this.token1Amount = token1Amount;
    }
}
