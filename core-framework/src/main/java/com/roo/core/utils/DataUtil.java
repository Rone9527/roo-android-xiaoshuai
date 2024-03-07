package com.roo.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DataUtil {

    /**
     * 大于1保留2位，小于1保留8位
     *
     * @param number
     * @return
     */
    public static String getStringAboutOne(String number) {
        if (new BigDecimal(number).compareTo(new BigDecimal("1")) == 1) {
            return new BigDecimal(number).setScale(2, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
        } else {
            return new BigDecimal(number).setScale(8, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
        }
    }

    /**
     * 查询第一个不是 0 的字符的位置
     *
     * @param str   检测的字符串
     * @param regex 检测的字符
     * @return
     */
    public int getFirst(String str, String regex) {
        int i = 0;
        for (int index = 0; index <= str.length() - 1; index++) {
            //将字符串拆开成单个的字符
            String w = str.substring(index, index + 1);
            if (!regex.equals(w)) {//
                i = index;
                break;
            }
        }
        return i;
    }
}
