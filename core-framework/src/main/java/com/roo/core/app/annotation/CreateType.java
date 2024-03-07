package com.roo.core.app.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        CreateType.WORDS,
        CreateType.PRIVATE,
        CreateType.CREATE
})
@Retention(RetentionPolicy.SOURCE)
public @interface CreateType {
    int CREATE = 1; //创建
    int WORDS = 2;  //助记词
    int PRIVATE = 3;//私钥导入
}
