package com.core.domain.mine;

import java.io.Serializable;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/7 14:33
 *     desc        : 描述--//LegalCurrencyBean 计价货币
 * </pre>
 */

public class LegalCurrencyBean implements Serializable {

    public static final String USD = "USD";
    public static final String KRW = "KRW";
    public static final String CNY = "CNY";
    /**
     * name : 人民币
     * symbol : CNY
     * icon : ¥ $
     * decimals : 1
     */
    private String name;
    private String symbol;
    private String icon;
    private int decimals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getDecimals() {
        return decimals;
    }

    public static LegalCurrencyBean valueOfUSD() {
        LegalCurrencyBean cnyLegal = new LegalCurrencyBean();
        cnyLegal.setName("美元");
        cnyLegal.setSymbol(USD);
        cnyLegal.setIcon("$");
        cnyLegal.setDecimals(4);
        return cnyLegal;
    }

    public static LegalCurrencyBean valueOfCny() {
        LegalCurrencyBean cnyLegal = new LegalCurrencyBean();
        cnyLegal.setName("人民币");
        cnyLegal.setSymbol(CNY);
        cnyLegal.setIcon("¥");
        cnyLegal.setDecimals(1);
        return cnyLegal;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

}
