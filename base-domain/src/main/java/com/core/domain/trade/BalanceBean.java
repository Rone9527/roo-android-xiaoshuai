package com.core.domain.trade;

import java.math.BigDecimal;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/8/11 17:34
 *     desc        : 描述--//BalanceBean TODO
 * </pre>
 */

public class BalanceBean {

    /**
     * address : 0xfefdf43ce0524397c4eb078acb74ce6e5f4988d6
     * availableBalance : 0
     * contractId : xxxxxx
     */

    private String address;

    @Override
    public String toString() {
        return "BalanceBean{" +
                "address='" + address + '\'' +
                ", availableBalance=" + availableBalance +
                ", contractId='" + contractId + '\'' +
                '}';
    }

    private BigDecimal availableBalance;
    private String contractId;

    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    private String chainCode;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}
