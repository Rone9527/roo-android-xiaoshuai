package com.core.domain.dapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

@Entity
public class DappBean implements Parcelable, MultiItemEntity {
    @org.greenrobot.greendao.annotation.Id
    private Long Id;
    /*数据库保存的类型： 最近、收藏*/
    private int dataBaseType;
    private long dataBaseCreateTime;


    private String name;
    private String icon;
    private String type;

    @Override
    public String toString() {
        return "DappBean{" +
                "Id=" + Id +
                ", dataBaseType=" + dataBaseType +
                ", dataBaseCreateTime=" + dataBaseCreateTime +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", type='" + type + '\'' +
                ", links='" + links + '\'' +
                ", hots=" + hots +
                ", chain='" + chain + '\'' +
                ", display='" + display + '\'' +
                ", sort=" + sort +
                ", discription='" + discription + '\'' +
                ", createTime='" + createTime + '\'' +
                ", multis=" + multis +
                ", telegram='" + telegram + '\'' +
                ", twitter='" + twitter + '\'' +
                ", officialEmail='" + officialEmail + '\'' +
                ", itemType=" + itemType +
                '}';
    }

    private String links;
    private int hots;
    private String chain;
    private String display;
    private int sort;
    private String discription;
    private String createTime;
    private int multis;

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    private String telegram;
    private String twitter;

    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    public static Creator<DappBean> getCREATOR() {
        return CREATOR;
    }

    private String officialEmail;

    public int getMultis() {
        return multis;
    }

    public void setMultis(int multis) {
        this.multis = multis;
    }

    @Transient
    private int itemType;

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getName() {
        return this.name;
    }

    public int getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(int dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return this.type;
    }

    public long getDataBaseCreateTime() {
        return dataBaseCreateTime;
    }

    public void setDataBaseCreateTime(long dataBaseCreateTime) {
        this.dataBaseCreateTime = dataBaseCreateTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLinks() {
        return this.links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public int getHots() {
        return this.hots;
    }

    public void setHots(int hots) {
        this.hots = hots;
    }

    public String getChain() {
        return this.chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getDisplay() {
        return this.display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public DappBean() {
    }

    @Generated(hash = 710948595)
    public DappBean(Long Id, int dataBaseType, long dataBaseCreateTime, String name, String icon,
                    String type, String links, int hots, String chain, String display, int sort,
                    String discription, String createTime, int multis, String telegram, String twitter,
                    String officialEmail) {
        this.Id = Id;
        this.dataBaseType = dataBaseType;
        this.dataBaseCreateTime = dataBaseCreateTime;
        this.name = name;
        this.icon = icon;
        this.type = type;
        this.links = links;
        this.hots = hots;
        this.chain = chain;
        this.display = display;
        this.sort = sort;
        this.discription = discription;
        this.createTime = createTime;
        this.multis = multis;
        this.telegram = telegram;
        this.twitter = twitter;
        this.officialEmail = officialEmail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.Id);
        dest.writeInt(this.dataBaseType);
        dest.writeLong(this.dataBaseCreateTime);
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeString(this.type);
        dest.writeString(this.links);
        dest.writeInt(this.hots);
        dest.writeString(this.chain);
        dest.writeString(this.display);
        dest.writeInt(this.sort);
        dest.writeString(this.discription);
        dest.writeString(this.createTime);
        dest.writeInt(this.multis);
        dest.writeInt(this.itemType);
        dest.writeString(twitter);
        dest.writeString(officialEmail);
        dest.writeString(telegram);
    }

    public void readFromParcel(Parcel source) {
        this.Id = (Long) source.readValue(Long.class.getClassLoader());
        this.dataBaseType = source.readInt();
        this.dataBaseCreateTime = source.readLong();
        this.name = source.readString();
        this.icon = source.readString();
        this.type = source.readString();
        this.links = source.readString();
        this.hots = source.readInt();
        this.chain = source.readString();
        this.display = source.readString();
        this.sort = source.readInt();
        this.discription = source.readString();
        this.createTime = source.readString();
        this.multis = source.readInt();
        this.itemType = source.readInt();
        this.twitter = source.readString();
        this.twitter = source.readString();
        this.officialEmail = source.readString();
        this.telegram = source.readString();
    }

    protected DappBean(Parcel in) {
        this.Id = (Long) in.readValue(Long.class.getClassLoader());
        this.dataBaseType = in.readInt();
        this.dataBaseCreateTime = in.readLong();
        this.name = in.readString();
        this.icon = in.readString();
        this.type = in.readString();
        this.links = in.readString();
        this.hots = in.readInt();
        this.chain = in.readString();
        this.display = in.readString();
        this.sort = in.readInt();
        this.discription = in.readString();
        this.createTime = in.readString();
        this.multis = in.readInt();
        this.itemType = in.readInt();
        this.twitter = in.readString();
        this.officialEmail = in.readString();
        this.telegram = in.readString();
    }

    public static final Creator<DappBean> CREATOR = new Creator<DappBean>() {
        @Override
        public DappBean createFromParcel(Parcel source) {
            return new DappBean(source);
        }

        @Override
        public DappBean[] newArray(int size) {
            return new DappBean[size];
        }
    };
}
