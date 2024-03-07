package com.finddreams.languagelib;


import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lx on 17-11-6.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        LanguageType.LANGUAGE_EN, //英文
        LanguageType.LANGUAGE_CHINESE,//简体
        LanguageType.LANGUAGE_KO//韩文
})
public @interface LanguageType {
    //跟随系统
    //英文
    String LANGUAGE_EN = "en";
    //简体
    String LANGUAGE_CHINESE = "zh";
    //韩文
    String LANGUAGE_KO = "ko";

}
