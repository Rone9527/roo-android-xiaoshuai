package com.core.domain.dapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

public class DappTypeBean implements  MultiItemEntity {

    private boolean select;
    private int itemType;
    /**
     * list : [{"list":[{"id":1413065554734014500,"name":"Alpaca","icon":"https://api.roo.top/token-icons/bsc/dapp/alpaca/logo.png","type":"衍生品","links":"https://www.alpacafinance.org/","hots":0,"discription":"第一个基于Binance Smart Chain的杠杆式收益挖矿协议。 ","chain":"BSC","display":"show","sort":12,"multis":0}],"name":"衍生品"}]
     * name : BSC
     */

    private String name;
    /**
     * list : [{"id":1413065554734014500,"name":"Alpaca","icon":"https://api.roo.top/token-icons/bsc/dapp/alpaca/logo.png","type":"衍生品","links":"https://www.alpacafinance.org/","hots":0,"discription":"第一个基于Binance Smart Chain的杠杆式收益挖矿协议。 ","chain":"BSC","display":"show","sort":12,"multis":0}]
     * name : 衍生品
     */

    private List<ListBean> list;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }


    public static class ListBean implements MultiItemEntity, Parcelable {
        private String name;

        private List<DappBean> list;
        private int itemType;
        private boolean seletct;

        protected ListBean(Parcel in) {
            name = in.readString();
            list = in.createTypedArrayList(DappBean.CREATOR);
            itemType = in.readInt();
            seletct = in.readByte() != 0;
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<DappBean> getList() {
            return list;
        }

        public void setList(List<DappBean> list) {
            this.list = list;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setSeletct(boolean seletct) {
            this.seletct = seletct;
        }

        public boolean getSeletct() {
            return seletct;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
            parcel.writeTypedList(list);
            parcel.writeInt(itemType);
            parcel.writeByte((byte) (seletct ? 1 : 0));
        }
    }
}
