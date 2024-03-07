package com.roo.core.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TZT on 2017/3/8.
 */

public class MoblieNoValidateUtil {

    public static Pattern pattern = Pattern.compile("^[1][3-9]+\\d{9}");

    public static boolean isMobileNO(String mobiles) {
        if (null == mobiles || "".equals(mobiles))
            return false;
        Matcher m = pattern.matcher(mobiles);
        return m.matches();
    }


    public static boolean isEmail(String email) {
        if (null == email || "".equals(email))
            return false;
        Matcher m = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(email);
        return m.matches();
    }

    public static boolean isChinaIdCard(String idCard) {
        String ID_CARD_REGEX = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        return idCard != null && idCard.length() != 0 && idCard.matches(ID_CARD_REGEX);
    }

    public static boolean isCorrectSymbol(String symbol) {
        if (TextUtils.isEmpty(symbol)) {
            return false;
        }
        Matcher m = Pattern.compile("\\w{2,10}_\\w{2,10}").matcher(symbol);
        return m.matches();
    }

    public static boolean formatPassword(String password) {
        if (TextUtils.isEmpty(password)) return false;
        //return password.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?!.*[ ]).{8,20}$");
        return password.matches("^(?=.*\\D)(?!.*[ ]).{8,20}$");//密码不可以为纯数字，不能包含空格
    }

    public static boolean formatAssetPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return password.length() == 6;
    }

    public static boolean formatVerifyCode(String vCode) {
        if (TextUtils.isEmpty(vCode)) return false;
        return vCode.matches("^\\d{6}");
    }

    public static boolean formatInvitedCode(String vCode) {
        if (TextUtils.isEmpty(vCode)) {
            return false;
        }
        return vCode.length() == 8;
    }
}
