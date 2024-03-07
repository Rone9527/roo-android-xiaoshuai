package com.core.domain.wallet;


import java.io.Serializable;

/**
 * //  ┏┓　　　┏┓
 * //┏┛┻━━━┛┻┓
 * //┃　　　　　　　┃
 * //┃　　　━　　　┃
 * //┃　┳┛　┗┳　┃
 * //┃　　　　　　　┃
 * //┃　　　┻　　　┃
 * //┃　　　　　　　┃
 * //┗━┓　　　┏━┛
 * //    ┃　　　┃   神兽保佑
 * //    ┃　　　┃   代码无BUG！
 * //    ┃　　　┗━━━┓
 * //    ┃　　　　　　　┣┓
 * //    ┃　　　　　　　┏┛
 * //    ┗┓┓┏━┳┓┏┛
 * //      ┃┫┫　┃┫┫
 * //      ┗┻┛　┗┻┛
 * Created by : Arvin
 * Created on : 2021/8/23
 * PackageName : com.roo.home.mvp.model.bean
 * Description :
 */
public class TronAccountResource implements Serializable {

    @Override
    public String toString() {
        return "TronAccountResource{" +
                "freeNetLimit=" + freeNetLimit +
                ", TotalNetLimit=" + TotalNetLimit +
                ", TotalNetWeight=" + TotalNetWeight +
                ", TotalEnergyLimit=" + TotalEnergyLimit +
                ", TotalEnergyWeight=" + TotalEnergyWeight +
                '}';
    }

    public float freeNetUsed;//已使用的免费带宽
    public float freeNetLimit;//免费带宽总量
    public float TotalNetLimit;//全网通过质押获取的带宽总量
    public float TotalNetWeight;//全网用于获取带宽的质押TRX总量
    public float TotalEnergyLimit;//全网通过质押获取的能量总量
    public float TotalEnergyWeight;//全网用于获取能量的质押TRX总量
    public float NetLimit;//质押获得的带宽总量
    public float tronPowerLimit;//拥有的投票权
    public float EnergyLimit;//质押获取的总能量
    public float EnergyUsed;//质押获取的总能量的消耗
}
