package com.core.domain.trade;

import java.io.Serializable;

public class NewAssetsBean implements Serializable {
    public NewAssetsBean() {
    }

    private String address;
    private String availableBalance;
    private String chainCode;
    private String contractId;
    private String symbol;
    private TokenChildBean tokenVO;
    private boolean isAdded;

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public TokenChildBean getTokenVO() {
        return tokenVO;
    }

    public void setTokenVO(TokenChildBean tokenVO) {
        this.tokenVO = tokenVO;
    }

    public class TokenChildBean implements Serializable {
        public TokenChildBean() {
        }

        @Override
        public String toString() {
            return "TokenChildBean{" +
                    "contractId='" + contractId + '\'' +
                    ", decimals=" + decimals +
                    ", name='" + name + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", totalSupply='" + totalSupply + '\'' +
                    ", isMarket=" + isMarket +
                    '}';
        }

        private String contractId;
        private int decimals;
        private String name;
        private String symbol;
        private String totalSupply;
        private boolean isMarket;

        public boolean isMarket() {
            return isMarket;
        }

        public void setMarket(boolean market) {
            isMarket = market;
        }



        public String getContractId() {
            return contractId;
        }

        public void setContractId(String contractId) {
            this.contractId = contractId;
        }

        public int getDecimals() {
            return decimals;
        }

        public void setDecimals(int decimals) {
            this.decimals = decimals;
        }

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

        public String getTotalSupply() {
            return totalSupply;
        }

        public void setTotalSupply(String totalSupply) {
            this.totalSupply = totalSupply;
        }
    }
}

