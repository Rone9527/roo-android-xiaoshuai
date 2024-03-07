package com.core.domain.mine;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.logging.LogManager;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/7 14:33
 *     desc        : 描述--//GasBean Gas费用
 * </pre>
 */

public class GasBean implements Parcelable {

    /**
     * chain : 链
     * fastGasPrice : 0
     * gasLimit : 0
     * lastBlock : 0
     * proposeGasPrice : 0
     * safeGasPrice : 0
     */
    private String chain;
    private BigDecimal fastGasPrice;//极快
    private BigDecimal gasLimit;
    private BigDecimal lastBlock;
    private BigDecimal proposeGasPrice;//快
    private BigDecimal safeGasPrice;//一般

    @Override
    public String toString() {
        return "GasBean{" +
                "chain='" + chain + '\'' +
                ", fastGasPrice=" + fastGasPrice +
                ", gasLimit=" + gasLimit +
                ", lastBlock=" + lastBlock +
                ", proposeGasPrice=" + proposeGasPrice +
                ", safeGasPrice=" + safeGasPrice +
                '}';
    }

    public GasBean() {
    }

    protected GasBean(Parcel in) {
        chain = in.readString();
    }

    public static final Creator<GasBean> CREATOR = new Creator<GasBean>() {
        @Override
        public GasBean createFromParcel(Parcel in) {
            return new GasBean(in);
        }

        @Override
        public GasBean[] newArray(int size) {
            return new GasBean[size];
        }
    };

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public BigDecimal getFastGasPrice() {
        return fastGasPrice;
    }

    public void setFastGasPrice(BigDecimal fastGasPrice) {
        this.fastGasPrice = fastGasPrice;
    }

    public BigDecimal getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(BigDecimal gasLimit) {

        this.gasLimit = gasLimit;
    }

    public BigDecimal getLastBlock() {
        return lastBlock;
    }

    public void setLastBlock(BigDecimal lastBlock) {
        this.lastBlock = lastBlock;
    }

    public BigDecimal getProposeGasPrice() {
        return proposeGasPrice;
    }

    public void setProposeGasPrice(BigDecimal proposeGasPrice) {
        this.proposeGasPrice = proposeGasPrice;
    }

    public BigDecimal getSafeGasPrice() {
        return safeGasPrice;
    }

    public void setSafeGasPrice(BigDecimal safeGasPrice) {
        this.safeGasPrice = safeGasPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chain);
    }
}
