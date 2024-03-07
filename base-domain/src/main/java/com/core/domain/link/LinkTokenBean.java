package com.core.domain.link;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/12 14:42
 *     desc        : 描述--//LinkTokenBean
 * </pre>
 */
public class LinkTokenBean {

    /**
     * code : ETH
     * name : 以太坊
     * nameEn : Ethereum
     * icon : https://etherscan.io/images/logo-ether.png?v=0.0.2
     * tokens : [{"chainCode":"ETH","name":"泰达币","nameEn":"Tether","symbol":"USDT","contractId":"0xdac17f958d2ee523a2206206994597c13d831ec7","decimals":6,"icon":"https://static.bafang.com/cdn/explorer/ethToken/ETH_USDT_0xdac17f958d2ee523a2206206994597c13d831ec7.jpg","isRecommend":true,"isSearch":true},{"chainCode":"ETH","name":"袋鼠币","nameEn":"Roo","symbol":"ROO","contractId":"0xDc6a8fe2F99417a6a1F8E9A9A14C48d30B8b5897","decimals":18,"isRecommend":true,"isSearch":true}]
     * nodes : [{"chainCode":"ETH","network":"TEST","rpcUrl":"http://127.0.0.1:7545/","sort":1}]
     */
    private String code;
    private String name;
    private String nameEn;
    private boolean select;
    /**
     * chainCode : ETH
     * name : 泰达币
     * nameEn : Tether
     * symbol : USDT
     * contractId : 0xdac17f958d2ee523a2206206994597c13d831ec7
     * decimals : 6
     * icon : https://static.bafang.com/cdn/explorer/ethToken/ETH_USDT_0xdac17f958d2ee523a2206206994597c13d831ec7.jpg
     * isRecommend : true
     * isSearch : true
     */

    private ArrayList<TokensBean> tokens;
    /**
     * chainCode : ETH
     * network : TEST
     * rpcUrl : http://127.0.0.1:7545/
     * sort : 1
     */

    private ArrayList<NodesBean> nodes;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public ArrayList<TokensBean> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<TokensBean> tokens) {
        this.tokens = tokens;
    }

    public ArrayList<NodesBean> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<NodesBean> nodes) {
        this.nodes = nodes;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isSelect() {
        return select;
    }

    public static class TokensBean implements Parcelable {
        @Override
        public String toString() {
            return "TokensBean{" +
                    "chainCode='" + chainCode + '\'' +
                    ", name='" + name + '\'' +
                    ", nameEn='" + nameEn + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", contractId='" + contractId + '\'' +
                    ", decimals=" + decimals +
                    ", isRecommend=" + isRecommend +
                    ", isSearch=" + isSearch +
                    ", sort=" + sort +
                    ", select=" + select +
                    ", isMain=" + isMain +
                    ", isMarket=" + isMarket +
                    '}';
        }

        private String chainCode;
        private String name;
        private String nameEn;
        private String symbol;
        private String contractId;
        private int decimals;
        private boolean isRecommend;
        private boolean isSearch;
        private int sort = 0;
        private boolean select;
        private boolean isMain;//是否是主链币
        private boolean isMarket;//是否显示交易所详情



        public static String key(TokensBean tokensBean) {
            return tokensBean.getContractId() + tokensBean.getSymbol();
        }

        public static TokensBean valueOf(MainToken mainToken) {
            TokensBean tokensBean = new TokensBean();
            tokensBean.setIsRecommend(true);
            tokensBean.setIsSearch(true);
            tokensBean.setMain(true);
            tokensBean.setMarket(true);
            tokensBean.setSymbol(mainToken.getSymbol());
            tokensBean.setChainCode(mainToken.getChainCode());
            tokensBean.setName(mainToken.getName());
            tokensBean.setNameEn(mainToken.getNameEn());
            tokensBean.setDecimals(mainToken.getDecimals());
            return tokensBean;
        }

        public boolean isMarket() {
            return isMarket;
        }

        public void setMarket(boolean market) {
            isMarket = market;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getChainCode() {
            return chainCode;
        }

        public void setChainCode(String chainCode) {
            this.chainCode = chainCode;
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

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
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

        public boolean isIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(boolean isRecommend) {
            this.isRecommend = isRecommend;
        }

        public boolean isIsSearch() {
            return isSearch;
        }

        public void setIsSearch(boolean isSearch) {
            this.isSearch = isSearch;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public TokensBean() {
        }

        public boolean isMain() {
            return isMain;
        }

        public void setMain(boolean main) {
            isMain = main;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.chainCode);
            dest.writeString(this.name);
            dest.writeString(this.nameEn);
            dest.writeString(this.symbol);
            dest.writeString(this.contractId);
            dest.writeInt(this.decimals);
            dest.writeByte(this.isRecommend ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isSearch ? (byte) 1 : (byte) 0);
            dest.writeInt(this.sort);
            dest.writeByte(this.select ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isMain ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isMarket ? (byte) 1 : (byte) 0);
        }

        public void readFromParcel(Parcel source) {
            this.chainCode = source.readString();
            this.name = source.readString();
            this.nameEn = source.readString();
            this.symbol = source.readString();
            this.contractId = source.readString();
            this.decimals = source.readInt();
            this.isRecommend = source.readByte() != 0;
            this.isSearch = source.readByte() != 0;
            this.sort = source.readInt();
            this.select = source.readByte() != 0;
            this.isMain = source.readByte() != 0;
            this.isMarket = source.readByte() != 0;
        }

        protected TokensBean(Parcel in) {
            this.chainCode = in.readString();
            this.name = in.readString();
            this.nameEn = in.readString();
            this.symbol = in.readString();
            this.contractId = in.readString();
            this.decimals = in.readInt();
            this.isRecommend = in.readByte() != 0;
            this.isSearch = in.readByte() != 0;
            this.sort = in.readInt();
            this.select = in.readByte() != 0;
            this.isMain = in.readByte() != 0;
            this.isMarket = in.readByte() != 0;
        }

        public static final Parcelable.Creator<TokensBean> CREATOR = new Parcelable.Creator<TokensBean>() {
            @Override
            public TokensBean createFromParcel(Parcel source) {
                return new TokensBean(source);
            }

            @Override
            public TokensBean[] newArray(int size) {
                return new TokensBean[size];
            }
        };
    }

    public static class NodesBean {
        private String chainCode;
        private String network;
        private String rpcUrl;
        private int sort;
        /**
         * browserUrl : https://testnet.hecoinfo.com/tx/{txid}
         */
        private String browserUrl;

        public String getChainCode() {
            return chainCode;
        }

        public void setChainCode(String chainCode) {
            this.chainCode = chainCode;
        }

        public String getNetwork() {
            return network;
        }

        public void setNetwork(String network) {
            this.network = network;
        }

        public String getRpcUrl() {
            return rpcUrl;
        }

        public void setRpcUrl(String rpcUrl) {
            this.rpcUrl = rpcUrl;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getBrowserUrl() {
            return browserUrl;
        }

        public void setBrowserUrl(String browserUrl) {
            this.browserUrl = browserUrl;
        }
    }
}
