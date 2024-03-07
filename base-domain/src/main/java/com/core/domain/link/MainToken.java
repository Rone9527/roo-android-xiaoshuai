package com.core.domain.link;

import com.core.domain.manager.ChainCode;

public enum MainToken {

    BTC("BTC", ChainCode.BTC, "比特币", "BTC", 18),

    ETH("ETH", ChainCode.ETH, "以太坊", "ETH", 18),

    HECO("HT", ChainCode.HECO, "火币", "HT", 18),

    BSC("BNB", ChainCode.BSC, "币安币", "BNB", 18),

    OEC("OKT", ChainCode.OEC, "OKT", "OKT", 18),

    TRON("TRX", ChainCode.TRON, "波场币", "TRX", 6),

    POLYGON("MATIC", ChainCode.POLYGON, "MATIC", "MATIC", 18),

    FANTOM("FTM", ChainCode.FANTOM, "FANTOM", "FTM", 18);

    private String symbol;
    private String chainCode;
    private String name;
    private String nameEn;
    private int decimals;

    MainToken(String symbol, String chainCode, String name, String nameEn, int decimals) {
        this.symbol = symbol;
        this.chainCode = chainCode;
        this.name = name;
        this.nameEn = nameEn;
        this.decimals = decimals;
    }

    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

}
