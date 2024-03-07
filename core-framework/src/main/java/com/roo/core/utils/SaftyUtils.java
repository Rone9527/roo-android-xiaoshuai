package com.roo.core.utils;

import android.text.TextUtils;

/**
 * 号码处理工具类
 * Created by Hilox on 2018/12/29 0029.
 */
public class SaftyUtils {

    private SaftyUtils() {
    }

    /**
     * 身份证号脱敏筛选正则
     */
    public static final String ID_CARD_BLUR_REGEX = "(\\d{4})\\d{10}(\\w{4})";

    /**
     * 手机号脱敏筛选正则
     */
    public static final String PHONE_BLUR_REGEX = "(\\d{3})\\d{4}(\\d{4})";

    /**
     * 替换正则
     */
    public static final String BLUR_REPLACE_REGEX = "$1****$2";

    /**
     * 手机号脱敏处理
     */
    public static String blurPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return "";
        }
        if (Validator.isMobile(phone)) {
            return phone.replaceAll(PHONE_BLUR_REGEX, BLUR_REPLACE_REGEX);
        } else {
            return phone;
        }
    }

    /**
     * 身份证号脱敏处理
     */
    public static String blurIdCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return "";
        }
        if (Validator.isIDCard(idCard)) {
            return idCard.replaceAll(ID_CARD_BLUR_REGEX, BLUR_REPLACE_REGEX);
        } else {
            return idCard;
        }
    }


    private static final String OVERLAY = "****";
    private static final int START = 3;
    private static final int END = 7;

    /**
     * 过滤邮箱账号,eg:
     * 邮箱 "845885222@qq.com" - 845****22@qq.com
     * 邮箱不够四位 "22@qq.com" - 22****@qq.com
     * 邮箱错误 "22qq.com" - 22qq.com
     */
    public static String blurEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return "";
        }
        String at = "@";
        if (email.contains(at)) {
            int length = email.indexOf(at);
            String content = email.substring(0, length);
            String mask = overlay(content, OVERLAY, START, END);
            return mask + email.substring(length);
        } else {
            return email;
        }
    }

    public static String overlay(String str, String overlay, int start, int end) {
        int len = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > len) {
            start = len;
        }
        if (end < 0) {
            end = 0;
        }
        if (end > len) {
            end = len;
        }
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }
        return str.substring(0, start) + overlay + str.substring(end);
    }

}