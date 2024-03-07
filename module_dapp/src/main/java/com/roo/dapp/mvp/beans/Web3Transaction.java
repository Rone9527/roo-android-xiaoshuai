package com.roo.dapp.mvp.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigInteger;


public class Web3Transaction implements Parcelable {
    public Address recipient;
    public Address contract;
    public BigInteger value;
    public BigInteger gasPrice;
    public BigInteger gasLimit;
    public String payload;
    public long leafPosition;

    public boolean isConstructor() {
        return (recipient.equals(Address.EMPTY) && payload != null);
    }

    public Web3Transaction(
            Address recipient,
            Address contract,
            BigInteger value,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String payload,
            long leafPosition) {
        this.recipient = recipient;
        this.contract = contract;
        this.value = value;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.payload = payload;
        this.leafPosition = leafPosition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.recipient, flags);
        dest.writeParcelable(this.contract, flags);
        dest.writeSerializable(this.value);
        dest.writeSerializable(this.gasPrice);
        dest.writeSerializable(this.gasLimit);
        dest.writeString(this.payload);
        dest.writeLong(this.leafPosition);
    }

    public void readFromParcel(Parcel source) {
        this.recipient = source.readParcelable(Address.class.getClassLoader());
        this.contract = source.readParcelable(Address.class.getClassLoader());
        this.value = (BigInteger) source.readSerializable();
        this.gasPrice = (BigInteger) source.readSerializable();
        this.gasLimit = (BigInteger) source.readSerializable();
        this.payload = source.readString();
        this.leafPosition = source.readLong();
    }

    protected Web3Transaction(Parcel in) {
        this.recipient = in.readParcelable(Address.class.getClassLoader());
        this.contract = in.readParcelable(Address.class.getClassLoader());
        this.value = (BigInteger) in.readSerializable();
        this.gasPrice = (BigInteger) in.readSerializable();
        this.gasLimit = (BigInteger) in.readSerializable();
        this.payload = in.readString();
        this.leafPosition = in.readLong();
    }

    public static final Creator<Web3Transaction> CREATOR = new Creator<Web3Transaction>() {
        @Override
        public Web3Transaction createFromParcel(Parcel source) {
            return new Web3Transaction(source);
        }

        @Override
        public Web3Transaction[] newArray(int size) {
            return new Web3Transaction[size];
        }
    };
}
