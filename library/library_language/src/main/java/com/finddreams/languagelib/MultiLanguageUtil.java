package com.finddreams.languagelib;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * 多语言切换的帮助类
 * http://blog.csdn.net/finddreams
 */
public class MultiLanguageUtil {

    public static final String SAVE_LANGUAGE = "language_save_25512_asds";
    private static MultiLanguageUtil instance;
    private Context mContext;

    private MultiLanguageUtil(Context context) {
        this.mContext = context;
    }

    public static void init(Context mContext) {
        if (instance == null) {
            synchronized (MultiLanguageUtil.class) {
                if (instance == null) {
                    instance = new MultiLanguageUtil(mContext);
                }
            }
        }
    }

    public static MultiLanguageUtil getInstance() {
        if (instance == null) {
            throw new IllegalStateException("You must be init MultiLanguageUtil first");
        }
        return instance;
    }

    public static Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context);
        } else {
            MultiLanguageUtil.getInstance().setConfiguration();
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getInstance().getLocale();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    /**
     * 设置语言
     */
    public void setConfiguration() {
        Locale targetLocale = getLocale();
        Configuration configuration = mContext.getResources().getConfiguration();
        configuration.setLocale(targetLocale);
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);//语言更换生效的代码!
    }

    /**
     * 如果不是英文、简体中文、繁体中文，默认返回简体中文
     */
    private Locale getLocale() {
        String languageType = getLanguage();
        if (languageType.equals(LanguageType.LANGUAGE_EN)) {
            return Locale.ENGLISH;
        } else if (languageType.equals(LanguageType.LANGUAGE_CHINESE)) {
            return Locale.SIMPLIFIED_CHINESE;
        } else if (languageType.equals(LanguageType.LANGUAGE_KO)) {
            return Locale.KOREAN;
        } else {
            return getSysLocale();
        }
    }

    /**
     * 获取到用户保存的语言类型
     */
    public String getLanguage() {
        return CommSharedUtil.getInstance(mContext).getString(MultiLanguageUtil.SAVE_LANGUAGE, getSysLocale().getLanguage());
    }

    public Locale getSysLocale() {
        Locale temp;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            temp = mContext.getResources().getConfiguration().getLocales().get(0);
        } else {
            temp = Locale.getDefault();
        }
        if (temp.getLanguage().equals(LanguageType.LANGUAGE_CHINESE)) {
            return Locale.SIMPLIFIED_CHINESE;
        } else if (temp.getCountry().equals(LanguageType.LANGUAGE_KO)) {
            return Locale.KOREAN;
        } else if (temp.getCountry().equals(LanguageType.LANGUAGE_EN)) {
            return Locale.ENGLISH;
        } else {
            return Locale.ENGLISH;
        }
    }

    /**
     * 更新语言
     */
    public void updateLanguage(String languageType) {
        CommSharedUtil.getInstance(mContext).putString(MultiLanguageUtil.SAVE_LANGUAGE, languageType);
        MultiLanguageUtil.getInstance().setConfiguration();
    }

}
