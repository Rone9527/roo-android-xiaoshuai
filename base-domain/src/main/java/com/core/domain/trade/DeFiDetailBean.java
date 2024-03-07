package com.core.domain.trade;

public class DeFiDetailBean {
    /**
     * "ascription": "string",
     * "createTimestamp": 0,
     * "holders": 0,
     * "identity": "string",
     * "initPrice": 0,
     * "logo": "string",
     * "rateOfPrice": 0,
     * "rateOfReserveUSD": 0,
     * "rateOfTxCount24": 0,
     * "rateOfVolume24": 0,
     * "reserveUSD": 0,
     * "token0Id": "string",
     * "token0Price": 0,
     * "token0PriceUSD": 0,
     * "token0Reserve": 0,
     * "token0Symbol": "string",
     * "token1Reserve": 0,
     * "token1Symbol": "string",
     * "txCount": 0,
     * "txCount24": 0,
     * "volume24": 0
     */
    public DeFiDetailBean() {
    }

    private String ascriptionIcon;//defi图标
    private String ascription;
    private long createTimestamp;
    private long holders;
    private String identity;
    private String initPrice;
    private String logo;
    private String rateOfPrice;
    private String rateOfReserveUSD;
    private String rateOfTxCount24;
    private String rateOfVolume24;
    private String reserveUSD;
    private String token0Id;
    private String token0Price;
    private String token0PriceUSD;
    private String token0Reserve;
    private String token0Symbol;
    private String token1Reserve;
    private String token1Symbol;
    private String txCount;
    private String txCount24;
    private String volume24;
    private String baseLogo;
    private String url;
    private String chainCode;

    public String getAscriptionIcon() {
        return ascriptionIcon;
    }

    public void setAscriptionIcon(String ascriptionIcon) {
        this.ascriptionIcon = ascriptionIcon;
    }

    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public String getBaseLogo() {
        return baseLogo;
    }

    public void setBaseLogo(String baseLogo) {
        this.baseLogo = baseLogo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAscription() {
        return ascription;
    }

    public void setAscription(String ascription) {
        this.ascription = ascription;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public long getHolders() {
        return holders;
    }

    public void setHolders(long holders) {
        this.holders = holders;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(String initPrice) {
        this.initPrice = initPrice;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRateOfPrice() {
        return rateOfPrice;
    }

    public void setRateOfPrice(String rateOfPrice) {
        this.rateOfPrice = rateOfPrice;
    }

    public String getRateOfReserveUSD() {
        return rateOfReserveUSD;
    }

    public void setRateOfReserveUSD(String rateOfReserveUSD) {
        this.rateOfReserveUSD = rateOfReserveUSD;
    }

    public String getRateOfTxCount24() {
        return rateOfTxCount24;
    }

    public void setRateOfTxCount24(String rateOfTxCount24) {
        this.rateOfTxCount24 = rateOfTxCount24;
    }

    public String getRateOfVolume24() {
        return rateOfVolume24;
    }

    public void setRateOfVolume24(String rateOfVolume24) {
        this.rateOfVolume24 = rateOfVolume24;
    }

    public String getReserveUSD() {
        return reserveUSD;
    }

    public void setReserveUSD(String reserveUSD) {
        this.reserveUSD = reserveUSD;
    }

    public String getToken0Id() {
        return token0Id;
    }

    public void setToken0Id(String token0Id) {
        this.token0Id = token0Id;
    }

    public String getToken0Price() {
        return token0Price;
    }

    public void setToken0Price(String token0Price) {
        this.token0Price = token0Price;
    }

    public String getToken0PriceUSD() {
        return token0PriceUSD;
    }

    public void setToken0PriceUSD(String token0PriceUSD) {
        this.token0PriceUSD = token0PriceUSD;
    }

    public String getToken0Reserve() {
        return token0Reserve;
    }

    public void setToken0Reserve(String token0Reserve) {
        this.token0Reserve = token0Reserve;
    }

    public String getToken0Symbol() {
        return token0Symbol;
    }

    public void setToken0Symbol(String token0Symbol) {
        this.token0Symbol = token0Symbol;
    }

    public String getToken1Reserve() {
        return token1Reserve;
    }

    public void setToken1Reserve(String token1Reserve) {
        this.token1Reserve = token1Reserve;
    }

    public String getToken1Symbol() {
        return token1Symbol;
    }

    public void setToken1Symbol(String token1Symbol) {
        this.token1Symbol = token1Symbol;
    }

    public String getTxCount() {
        return txCount;
    }

    public void setTxCount(String txCount) {
        this.txCount = txCount;
    }

    public String getTxCount24() {
        return txCount24;
    }

    public void setTxCount24(String txCount24) {
        this.txCount24 = txCount24;
    }

    public String getVolume24() {
        return volume24;
    }

    public void setVolume24(String volume24) {
        this.volume24 = volume24;
    }
}
