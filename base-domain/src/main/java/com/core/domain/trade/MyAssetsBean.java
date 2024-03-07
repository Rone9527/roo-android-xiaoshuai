package com.core.domain.trade;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MyAssetsBean implements Serializable {
    static final long serialVersionUID = 42L;

    public MyAssetsBean() {
    }
    @Generated(hash = 1312723298)
    public MyAssetsBean(Long id, String walletRemark, String allAssets) {
        this.id = id;
        this.walletRemark = walletRemark;
        this.allAssets = allAssets;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWalletRemark() {
        return this.walletRemark;
    }
    public void setWalletRemark(String walletRemark) {
        this.walletRemark = walletRemark;
    }
    public String getAllAssets() {
        return this.allAssets;
    }
    public void setAllAssets(String allAssets) {
        this.allAssets = allAssets;
    }

    @Id
    private Long id;
    private String walletRemark;
    private String allAssets;
}
