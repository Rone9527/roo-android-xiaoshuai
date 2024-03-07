package com.core.domain.trade;

import java.util.ArrayList;
import java.util.List;

public class DeFiBean {
    public DeFiBean() {
    }

    private int totalPage;
    private List<DeFiDataBean> data;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }


    public List<DeFiDataBean> getData() {
        return data == null ? new ArrayList<>() : data;
    }

    public void setData(List<DeFiDataBean> data) {
        this.data = data;
    }
}
