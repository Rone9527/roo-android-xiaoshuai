package com.core.domain.trade;

import java.math.BigDecimal;

/**
 * @author oldnine
 * @since 2018/11/10
 */
public class TickerBean {

    /**
     * symbol : BTC_USDT
     * baseAsset : BTC
     * quoteAsset : USDT
     * decimals : 18
     * price : 35015
     * volume : 17811889990
     * priceChangePercent : -0.43
     */

    private String symbol;
    private String baseAsset;
    private String quoteAsset;
    private int decimals = 2;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal volume = BigDecimal.ZERO;
    private BigDecimal priceChangePercent = BigDecimal.ZERO;
    private BigDecimal marketCapUsd = BigDecimal.ZERO;
    private boolean isShow;

    public String getSymbol() {
        return symbol;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBaseAsset() {
        return baseAsset;
    }

    public void setBaseAsset(String baseAsset) {
        this.baseAsset = baseAsset;
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public void setQuoteAsset(String quoteAsset) {
        this.quoteAsset = quoteAsset;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getPriceChangePercent() {
        return priceChangePercent;
    }

    public void setPriceChangePercent(BigDecimal priceChangePercent) {
        this.priceChangePercent = priceChangePercent;
    }

    public BigDecimal getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(BigDecimal marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }
}
