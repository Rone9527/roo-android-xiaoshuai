package com.core.domain.mine;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/7/16 15:36
 *     desc        : 描述--//PushCustom
 * </pre>
 */

public class MessageTrade implements Parcelable , MultiItemEntity {

    private String id;
    /**
     * activity : 10001
     * alert : 到账通知
     * amount : 0.00000001
     * chainCode : HECO
     * index : 0
     * time : 1626429318
     * toAddr : 0xa5aa22e70459cfb45f9bfc2a6737f242d1c1dafb
     * token : HT
     * txId : 0xbe17631d58a7d1b1bde36c170038a6e6d248dcfa152929becdd2643250542762
     * type : account
     */

    private String activity;
    private String alert;
    private String amount;
    private String chainCode;
    private String index;
    private long time;
    private String toAddr;
    private String token;
    private String txId;
    private String type;
    private boolean readStatus;
    private TransferRecordBean.DataBean dataBean;
    private int itemType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransferRecordBean.DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(TransferRecordBean.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getToAddr() {
        return toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MessageTrade() {
    }


    @Override
    public int getItemType() {
        return itemType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.activity);
        dest.writeString(this.alert);
        dest.writeString(this.amount);
        dest.writeString(this.chainCode);
        dest.writeString(this.index);
        dest.writeLong(this.time);
        dest.writeString(this.toAddr);
        dest.writeString(this.token);
        dest.writeString(this.txId);
        dest.writeString(this.type);
        dest.writeByte(this.readStatus ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.dataBean, flags);
        dest.writeInt(this.itemType);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.activity = source.readString();
        this.alert = source.readString();
        this.amount = source.readString();
        this.chainCode = source.readString();
        this.index = source.readString();
        this.time = source.readLong();
        this.toAddr = source.readString();
        this.token = source.readString();
        this.txId = source.readString();
        this.type = source.readString();
        this.readStatus = source.readByte() != 0;
        this.dataBean = source.readParcelable(TransferRecordBean.DataBean.class.getClassLoader());
        this.itemType = source.readInt();
    }

    protected MessageTrade(Parcel in) {
        this.id = in.readString();
        this.activity = in.readString();
        this.alert = in.readString();
        this.amount = in.readString();
        this.chainCode = in.readString();
        this.index = in.readString();
        this.time = in.readLong();
        this.toAddr = in.readString();
        this.token = in.readString();
        this.txId = in.readString();
        this.type = in.readString();
        this.readStatus = in.readByte() != 0;
        this.dataBean = in.readParcelable(TransferRecordBean.DataBean.class.getClassLoader());
        this.itemType = in.readInt();
    }

    public static final Creator<MessageTrade> CREATOR = new Creator<MessageTrade>() {
        @Override
        public MessageTrade createFromParcel(Parcel source) {
            return new MessageTrade(source);
        }

        @Override
        public MessageTrade[] newArray(int size) {
            return new MessageTrade[size];
        }
    };
}
