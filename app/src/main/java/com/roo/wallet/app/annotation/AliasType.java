package com.roo.wallet.app.annotation;


import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        AliasType.TYPE_DEFAULT,
        AliasType.TYPE_YEAR
})
@Retention(RetentionPolicy.SOURCE)
public @interface AliasType {
    String KEY_ALIAS_TYPE = "KEY_ALIAS_TYPE";

    String TYPE_DEFAULT = "DEFAULT";//默认
    String TYPE_YEAR = "YEAR";//周年庆
}
