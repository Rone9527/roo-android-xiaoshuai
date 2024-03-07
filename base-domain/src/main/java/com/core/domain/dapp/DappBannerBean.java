package com.core.domain.dapp;

import java.io.Serializable;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : wty
 *     create time : 2021/7/28 13:55
 *     desc        : 描述--//BannerBean Dapp头部banner
 * </pre>
 */


public class DappBannerBean implements Serializable {
    public DappBannerBean() {
    }

    private String href;
    private String image;
    private int type;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
