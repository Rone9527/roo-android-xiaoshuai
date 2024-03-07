package com.core.domain.dapp;

import java.util.List;

public class JpushUpload {

    /**
     * deviceType : string
     * jpushId : string
     * subAddrList : [{"address":"string","chainCode":"string","walletName":"string"}]
     */

    private String deviceType;
    private String jpushId;
    /**
     * address : string
     * chainCode : string
     * walletName : string
     */

    private List<SubAddrListBean> subAddrList;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getJpushId() {
        return jpushId;
    }

    public void setJpushId(String jpushId) {
        this.jpushId = jpushId;
    }

    public List<SubAddrListBean> getSubAddrList() {
        return subAddrList;
    }

    public void setSubAddrList(List<SubAddrListBean> subAddrList) {
        this.subAddrList = subAddrList;
    }

    public static class SubAddrListBean {

        private String address;
        private String chainCode;
        private String walletName;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getChainCode() {
            return chainCode;
        }

        public void setChainCode(String chainCode) {
            this.chainCode = chainCode;
        }

        public String getWalletName() {
            return walletName;
        }

        public void setWalletName(String walletName) {
            this.walletName = walletName;
        }
    }
}
