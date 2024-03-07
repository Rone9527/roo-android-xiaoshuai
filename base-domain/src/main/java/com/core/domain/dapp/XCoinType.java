package com.core.domain.dapp;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
 * Created on : 2021/9/4
 * PackageName : com.core.domain.dapp
 * Description : 实际上就是币种对应的Chain_id;
 */
public interface XCoinType {
    int ETHEREUM = 60;//ETH

    int BITCOIN = 0;//BTC

    int FILECOIN = 461;//FIL

    int TRON = 195;//TRX

    int HT = 1010;//HT

    int BNB = 42840;//BNB

    @IntDef({BITCOIN, ETHEREUM, FILECOIN, TRON, HT, BNB})
    @Retention(RetentionPolicy.SOURCE)
    @interface XoneWalltCoinType {
    }
}
