package com.roo.core.utils;


import androidx.annotation.StringDef;

import com.core.domain.mine.LegalCurrencyBean;
import com.finddreams.languagelib.MultiLanguageUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class LanguageUtil {
    public static final ArrayList<String> LAN_LIST = new ArrayList<>();

    public static final String ZH = "zh";
    public static final String EN = "en";
    public static final String KO = "ko";

    static {
        LAN_LIST.add(ZH);
        LAN_LIST.add(EN);
        LAN_LIST.add(KO);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @StringDef({
            ZH,
            EN,
            KO
    })
    public @interface Language {
    }

    public static String getCurrency() {
        String i18n;
        if (LanguageUtil.isZh()) {
            i18n = LegalCurrencyBean.CNY;
        } else if (LanguageUtil.isEn()) {
            i18n = LegalCurrencyBean.USD;
        } else if (LanguageUtil.isKo()) {
            i18n = LegalCurrencyBean.KRW;
        } else {
            i18n = LegalCurrencyBean.CNY;
        }
        return i18n;
    }

    public static boolean isKo() {
        return MultiLanguageUtil.getInstance().getLanguage().equals(KO);
    }

    public static boolean isEn() {
        return MultiLanguageUtil.getInstance().getLanguage().equals(EN);
    }

    public static boolean isZh() {
        return MultiLanguageUtil.getInstance().getLanguage().equals(ZH);
    }

    public static boolean isNotZh() {
        return !MultiLanguageUtil.getInstance().getLanguage().equals(ZH);
    }

    public static String getLang() {
        return getLang(null);
    }

    public static String getLang(String defalut) {
        String i18n;
        if (LanguageUtil.isZh()) {
            i18n = ZH;
        } else if (LanguageUtil.isEn()) {
            i18n = EN;
        } else if (LanguageUtil.isKo()) {
            i18n = KO;
        } else {
            i18n = defalut == null ? EN : defalut;
        }
        return i18n;
    }

}
