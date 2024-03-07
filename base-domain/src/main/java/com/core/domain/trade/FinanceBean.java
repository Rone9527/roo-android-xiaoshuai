package com.core.domain.trade;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author oldnine
 * @since 2018/11/10
 */
public class FinanceBean {

    private int totalPage;
    private List<DataDTO> data;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public class DataDTO {
        private long id;
        private String name;
        private String ascription;
        private String sort;
        private BigDecimal rateOfReturn;
        private String link;
        private String logo;
        private String chainCode;
        private String tag;
        private String createTime;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAscription() {
            return ascription;
        }

        public void setAscription(String ascription) {
            this.ascription = ascription;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public BigDecimal getRateOfReturn() {
            return rateOfReturn;
        }

        public void setRateOfReturn(BigDecimal rateOfReturn) {
            this.rateOfReturn = rateOfReturn;
        }

        public String getChainCode() {
            return chainCode;
        }

        public void setChainCode(String chainCode) {
            this.chainCode = chainCode;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
