package com.roo.core.app.annotation;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({
        TransactionStatus.FAIL,
        TransactionStatus.CONFIRMED,
        TransactionStatus.PENDING,
        TransactionStatus.IN_BLOCK
})
public @interface TransactionStatus {

    String FAIL = "FAIL";           //交易失败

    String CONFIRMED = "CONFIRMED"; //已确认

    String PENDING = "PENDING";     //待处理

    String IN_BLOCK = "IN_BLOCK";   //打包中


}