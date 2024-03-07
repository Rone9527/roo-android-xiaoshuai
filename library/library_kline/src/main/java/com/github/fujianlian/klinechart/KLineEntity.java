package com.github.fujianlian.klinechart;



import androidx.annotation.NonNull;

import com.github.fujianlian.klinechart.entity.IKLine;

import java.util.Objects;

/**
 * K线实体
 * Created by tifezh on 2016/5/16.
 */
public class KLineEntity implements IKLine, Comparable<KLineEntity> {

    public String Date;
    public float Open;
    public float High;
    public float Low;
    public float Close;
    public float Volume;
    public float MA5Price;
    public float MA10Price;
    public float MA20Price;
    public float MA30Price;
    public float MA50Price;
    public float MA60Price;
    public float dea;
    public float dif;
    public float macd;
    public float k;
    public float d;
    public float j;
    public float r;
    public float rsi;
    public float up;
    public float mb;
    public float dn;
    public float MA5Volume;
    public float MA10Volume;
    public long time;

    public String getDate() {
        return Date;
    }

    @Override
    public float getOpenPrice() {
        return Open;
    }

    @Override
    public float getHighPrice() {
        return High;
    }

    @Override
    public float getLowPrice() {
        return Low;
    }

    @Override
    public float getClosePrice() {
        return Close;
    }

    @Override
    public float getMA5Price() {
        return MA5Price;
    }

    @Override
    public float getMA10Price() {
        return MA10Price;
    }

    @Override
    public float getMA20Price() {
        return MA20Price;
    }

    @Override
    public float getMA30Price() {
        return MA30Price;
    }

    @Override
    public float getMA50Price() {
        return MA50Price;
    }

    @Override
    public float getMA60Price() {
        return MA60Price;
    }

    @Override
    public float getDea() {
        return dea;
    }

    @Override
    public float getDif() {
        return dif;
    }

    @Override
    public float getMacd() {
        return macd;
    }

    @Override
    public float getK() {
        return k;
    }

    @Override
    public float getD() {
        return d;
    }

    @Override
    public float getJ() {
        return j;
    }

    @Override
    public float getR() {
        return r;
    }

    @Override
    public float getRsi() {
        return rsi;
    }

    @Override
    public float getUp() {
        return up;
    }

    @Override
    public float getMb() {
        return mb;
    }

    @Override
    public float getDn() {
        return dn;
    }

    @Override
    public float getVolume() {
        return Volume;
    }

    @Override
    public float getMA5Volume() {
        return MA5Volume;
    }

    @Override
    public float getMA10Volume() {
        return MA10Volume;
    }

    @Override
    public int compareTo(@NonNull KLineEntity kLineEntity) {
        long l = time - kLineEntity.time;
        if (l > 0)
            return 1;
        else if (l < 0)
            return -1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        KLineEntity that = (KLineEntity) o;
        return Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }
}
