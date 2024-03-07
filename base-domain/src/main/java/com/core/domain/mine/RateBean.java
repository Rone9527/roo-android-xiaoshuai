package com.core.domain.mine;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/7 14:33
 *     desc        : 描述--//LegalCurrencyBean 计价货币
 * </pre>
 */

public class RateBean implements Serializable {

    /**
     * KRW : 1136.36
     * USD : 1
     * CNY : 6.44
     */
    private BigDecimal KRW;

    private BigDecimal USD;

    private BigDecimal CNY;

    public BigDecimal getKRW() {
        return KRW;
    }

    public void setKRW(BigDecimal KRW) {
        this.KRW = KRW;
    }

    public BigDecimal getUSD() {
        return USD;
    }

    public void setUSD(BigDecimal USD) {
        this.USD = USD;
    }

    public BigDecimal getCNY() {
        return CNY;
    }

    public void setCNY(BigDecimal CNY) {
        this.CNY = CNY;
    }

    public static RateBean valueOf() {
        RateBean cnyLegal = new RateBean();
        cnyLegal.setKRW(BigDecimal.valueOf(1136.36));
        cnyLegal.setUSD(BigDecimal.valueOf(1));
        cnyLegal.setCNY(BigDecimal.valueOf(6.44));
        return cnyLegal;
    }

}
