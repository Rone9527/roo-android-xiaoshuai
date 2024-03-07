package com.core.domain.trade;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity
public class DeFiDataBean implements Serializable {
    static final long serialVersionUID = 42L;
    @Id
    private Long id;
    private String identity;
    private String logo;
    private String ascription;
    private String contractId;
    private String pairName;
    private String price;
    private String priceUSD;
    private String rateOfPrice;
    private String volumeUSD;
    private String chainCode;

    public DeFiDataBean() {
    }

    @Generated(hash = 549932006)
    public DeFiDataBean(Long id, String identity, String logo, String ascription,
            String contractId, String pairName, String price, String priceUSD,
            String rateOfPrice, String volumeUSD, String chainCode) {
        this.id = id;
        this.identity = identity;
        this.logo = logo;
        this.ascription = ascription;
        this.contractId = contractId;
        this.pairName = pairName;
        this.price = price;
        this.priceUSD = priceUSD;
        this.rateOfPrice = rateOfPrice;
        this.volumeUSD = volumeUSD;
        this.chainCode = chainCode;
    }

    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentity() {
        return this.identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAscription() {
        return this.ascription;
    }

    public void setAscription(String ascription) {
        this.ascription = ascription;
    }

    public String getContractId() {
        return this.contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getPairName() {
        return this.pairName;
    }

    public void setPairName(String pairName) {
        this.pairName = pairName;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceUSD() {
        return this.priceUSD;
    }

    public void setPriceUSD(String priceUSD) {
        this.priceUSD = priceUSD;
    }

    public String getRateOfPrice() {
        return this.rateOfPrice;
    }

    public void setRateOfPrice(String rateOfPrice) {
        this.rateOfPrice = rateOfPrice;
    }

    public String getVolumeUSD() {
        return this.volumeUSD;
    }

    public void setVolumeUSD(String volumeUSD) {
        this.volumeUSD = volumeUSD;
    }

}
