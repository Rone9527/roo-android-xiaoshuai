package com.core.domain.dapp;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class DappHistoryBean implements MultiItemEntity {

    private String name;
    private String createTime;
    private int itemType;

    public static DappHistoryBean valueOf(String name, int itemType) {
        DappHistoryBean item = new DappHistoryBean();
        item.setName(name);
        item.setItemType(itemType);
        return item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
