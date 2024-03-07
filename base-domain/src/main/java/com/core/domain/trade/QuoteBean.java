package com.core.domain.trade;

public class QuoteBean {
    private String coinCode;
    private String exchangeCode;
    private String name;
    private String nameZh;
    private String pair1;
    private String pair2;
    private String price;
    private String vol;
    private String logo;
    private String symbolPair;
    private String exchangeUrl;
    private String tickerid;

    public QuoteBean() {
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getPair1() {
        return pair1;
    }

    public void setPair1(String pair1) {
        this.pair1 = pair1;
    }

    public String getPair2() {
        return pair2;
    }

    public void setPair2(String pair2) {
        this.pair2 = pair2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSymbolPair() {
        return symbolPair;
    }

    public void setSymbolPair(String symbolPair) {
        this.symbolPair = symbolPair;
    }

    public String getExchangeUrl() {
        return exchangeUrl;
    }

    public void setExchangeUrl(String exchangeUrl) {
        this.exchangeUrl = exchangeUrl;
    }

    public String getTickerid() {
        return tickerid;
    }

    public void setTickerid(String tickerid) {
        this.tickerid = tickerid;
    }
}
