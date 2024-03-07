package com.roo.wallet.mvp.model.bean;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

import com.roo.wallet.R;

import java.util.ArrayList;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/3 13:44
 *     desc        : 描述--//GuiderBean 引导页
 * </pre>
 */


public class GuiderBean {

    public static ArrayList<GuiderBean.DataBean> valueOf(Context context) {
        ArrayList<GuiderBean.DataBean> list = new ArrayList<>();
        list.add(new DataBean(0, R.drawable.ic_app_guide_1, context.getString(R.string.guide_one_title), context.getString(R.string.guide_one_tubtitle)));
        list.add(new DataBean(1, R.drawable.ic_app_guide_2, context.getString(R.string.guide_two_title), context.getString(R.string.guide_two_tubtitle)));
        list.add(new DataBean(2, R.drawable.ic_app_guide_3, context.getString(R.string.guide_three_title), context.getString(R.string.guide_three_tubtitle)));
        return list;
    }

    public static class DataBean implements Parcelable {

        private int index;

        private int drawable;

        private String title;

        private String subTitle;

        public DataBean(int index, @DrawableRes int drawable, String title, String subTitle) {
            this.index = index;
            this.drawable = drawable;
            this.title = title;
            this.subTitle = subTitle;
        }

        public int getDrawable() {
            return drawable;
        }

        public String getTitle() {
            return title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.index);
            dest.writeInt(this.drawable);
            dest.writeString(this.title);
            dest.writeString(this.subTitle);
        }

        public void readFromParcel(Parcel source) {
            this.index = source.readInt();
            this.drawable = source.readInt();
            this.title = source.readString();
            this.subTitle = source.readString();
        }

        protected DataBean(Parcel in) {
            this.index = in.readInt();
            this.drawable = in.readInt();
            this.title = in.readString();
            this.subTitle = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
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
}
