package com.core.domain.dapp;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/7/9 11:22
 *     desc        : 描述--//DappTypeWrap
 * </pre>
 */

public class DappTypeWrap implements MultiItemEntity {

    private List<DappTypeBean> list;
    private int itemType;

    public DappTypeWrap(List<DappTypeBean> list, int itemType) {
        this.list = list;
        this.itemType = itemType;
    }

    public List<DappTypeBean> getList() {
        return list;
    }

    public void setList(List<DappTypeBean> list) {
        this.list = list;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
