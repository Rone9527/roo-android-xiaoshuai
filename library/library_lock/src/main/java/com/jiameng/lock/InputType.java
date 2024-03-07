package com.jiameng.lock;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({InputType.TYPE_OPEN, InputType.TYPE_LOGIN})
@Retention(RetentionPolicy.SOURCE)
public @interface InputType {

    int TYPE_OPEN = 1;
    int TYPE_LOGIN = 3;
}