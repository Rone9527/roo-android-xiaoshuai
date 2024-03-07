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

public class MessageSystem implements MultiItemEntity, Parcelable {

    private int itemType;
    /**
     * activity : 10002
     * msgId : 1
     * publishTime : 2021-08-19 17:13:56
     * type : sys
     */

    private String activity;
    private String msgId;
    private long publishTime;
    private String type;
    private boolean readStatus;

    private String msgTitle;
    private String msgContent;

    private MessageSystem.DataBean dataBean;

    public MessageSystem() {
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }


    public String getActivity() {
        return activity;
    }

    public DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public static class DataBean implements Parcelable {

        /**
         * id : 1
         * publishStatus : 2
         * msgType : sys
         * msgTitle : Roo最新内容发布
         * msgContent : Roo今日发布重大功能，即将兼容NFT钱包，实现NFT资产管理和发送，支持直接通过该钱包看视频，看图片，听音乐。
         * publishType : 1
         * platform : ios
         * deleted : 2
         */

        private String id;
        private int publishStatus;
        private String msgType;
        private String msgTitle;
        private String msgContent;
        private int publishType;
        private String platform;
        private int deleted;
        private long publishTime;

        public String getId() {
            return id;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPublishStatus() {
            return publishStatus;
        }

        public void setPublishStatus(int publishStatus) {
            this.publishStatus = publishStatus;
        }

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public String getMsgTitle() {
            return msgTitle;
        }

        public void setMsgTitle(String msgTitle) {
            this.msgTitle = msgTitle;
        }

        public String getMsgContent() {
            return msgContent;
        }

        public void setMsgContent(String msgContent) {
            this.msgContent = msgContent;
        }

        public int getPublishType() {
            return publishType;
        }

        public void setPublishType(int publishType) {
            this.publishType = publishType;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeInt(this.publishStatus);
            dest.writeString(this.msgType);
            dest.writeString(this.msgTitle);
            dest.writeString(this.msgContent);
            dest.writeInt(this.publishType);
            dest.writeString(this.platform);
            dest.writeInt(this.deleted);
            dest.writeLong(this.publishTime);
        }

        public void readFromParcel(Parcel source) {
            this.id = source.readString();
            this.publishStatus = source.readInt();
            this.msgType = source.readString();
            this.msgTitle = source.readString();
            this.msgContent = source.readString();
            this.publishType = source.readInt();
            this.platform = source.readString();
            this.deleted = source.readInt();
            this.publishTime = source.readLong();
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.publishStatus = in.readInt();
            this.msgType = in.readString();
            this.msgTitle = in.readString();
            this.msgContent = in.readString();
            this.publishType = in.readInt();
            this.platform = in.readString();
            this.deleted = in.readInt();
            this.publishTime = in.readLong();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemType);
        dest.writeString(this.activity);
        dest.writeString(this.msgId);
        dest.writeLong(this.publishTime);
        dest.writeString(this.type);
        dest.writeByte(this.readStatus ? (byte) 1 : (byte) 0);
        dest.writeString(this.msgTitle);
        dest.writeString(this.msgContent);
        dest.writeParcelable(this.dataBean, flags);
    }

    public void readFromParcel(Parcel source) {
        this.itemType = source.readInt();
        this.activity = source.readString();
        this.msgId = source.readString();
        this.publishTime = source.readLong();
        this.type = source.readString();
        this.readStatus = source.readByte() != 0;
        this.msgTitle = source.readString();
        this.msgContent = source.readString();
        this.dataBean = source.readParcelable(DataBean.class.getClassLoader());
    }

    protected MessageSystem(Parcel in) {
        this.itemType = in.readInt();
        this.activity = in.readString();
        this.msgId = in.readString();
        this.publishTime = in.readLong();
        this.type = in.readString();
        this.readStatus = in.readByte() != 0;
        this.msgTitle = in.readString();
        this.msgContent = in.readString();
        this.dataBean = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Creator<MessageSystem> CREATOR = new Creator<MessageSystem>() {
        @Override
        public MessageSystem createFromParcel(Parcel source) {
            return new MessageSystem(source);
        }

        @Override
        public MessageSystem[] newArray(int size) {
            return new MessageSystem[size];
        }
    };
}
