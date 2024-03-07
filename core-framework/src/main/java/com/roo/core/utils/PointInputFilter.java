package com.roo.core.utils;

import android.text.InputFilter;
import android.text.Spanned;

//https://juejin.im/post/59eed974f265da430b7a6c35
public class PointInputFilter implements InputFilter {

    private static final int DECIMAL_DIGITS = 2;//小数的位数
    private int decimalDigits = DECIMAL_DIGITS;

    private static final int INTEGER_DIGITS = 8;//整数位的位数
    private int integerDigits = INTEGER_DIGITS;

    private boolean forbidCopy = false;

    public PointInputFilter() {
    }

    public void setForbidCopy(boolean forbidCopy) {
        this.forbidCopy = forbidCopy;
    }

    public PointInputFilter(int integerDigits, int decimalDigits) {
        this.integerDigits = integerDigits;
        this.decimalDigits = decimalDigits;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        if ("".equals(source.toString())) {
            // 删除等特殊字符，直接返回
            return null;
        }

        if (source.length() > 1 && forbidCopy) {
            //如果想要直接禁止复制粘贴多个数字，直接这边限制。
            return "";
        }

        String dValue = dest.toString();
        String[] splitArray = dValue.split("\\.");
        switch (splitArray.length) {
            case 1:
                int currentLimitDigits = dValue.contains(".") ? (integerDigits + decimalDigits + 1) : integerDigits;
                if (source.length() > 1) {
                    String sValue = source.toString();
                    String[] subSplitArray = sValue.split("\\.");
                    switch (subSplitArray.length) {
                        case 1:
                            if (source.length() + dest.length() > currentLimitDigits) {
                                return source.subSequence(0, currentLimitDigits - dest.length());
                            }
                            break;
                        case 2:
                            String content = "";

                            if (dstart == dest.length()) {
                                if (subSplitArray[0].length() + dest.length() > integerDigits) {
                                    content += subSplitArray[0].subSequence(0, integerDigits - dest.length());
                                } else {
                                    content += subSplitArray[0];
                                }

                                if (subSplitArray[1].length() > decimalDigits) {
                                    content += "." + subSplitArray[1].substring(0, decimalDigits);

                                } else {
                                    content += "." + subSplitArray[1];
                                }
                                return content;

                            } else {
                                if (subSplitArray[0].length() + dest.length() > integerDigits) {
                                    content += subSplitArray[0].subSequence(0, integerDigits - dest.length());
                                } else {
                                    content += subSplitArray[0];
                                }
                            }
                            return content;

                        default:
                            break;
                    }
                }
                if (splitArray[0].length() >= currentLimitDigits && !".".equals(source.toString())) {
                    return "";
                }
                break;
            case 2:
                String integerValue = splitArray[0];
                String dotValue = splitArray[1];
                int dotIndex = dValue.indexOf(".");
                if (dstart <= dotIndex) {
                    if (integerValue.length() >= integerDigits) {
                        return "";
                    } else if (source.length() + integerValue.length() >= integerDigits) {
                        return source.subSequence(0, integerDigits - integerValue.length());
                    }
                } else {
                    if (dotValue.length() >= decimalDigits) {
                        return "";
                    } else if (source.length() + dotValue.length() >= decimalDigits) {
                        return source.subSequence(0, decimalDigits - dotValue.length());
                    }
                }
                break;
            default:
                break;
        }

        return null;
    }
}

