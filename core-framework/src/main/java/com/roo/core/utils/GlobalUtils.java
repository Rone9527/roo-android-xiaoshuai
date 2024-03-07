package com.roo.core.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.transition.Slide;

import com.core.domain.manager.ChainCode;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.R;
import com.roo.core.app.constants.GlobalConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * <pre>
 *     project name: MVPArms-master
 *     author      : 李琼
 *     create time : 2018/7/12 11:11
 *     desc        : 描述--//GlobalUtils
 * </pre>
 */

public class GlobalUtils {

    public static int getLinkImageUnselect(String chainCode) {
        if (ChainCode.BSC.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_bsc_grey;
        } else if (ChainCode.BTC.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_btc_grey;
        } else if (ChainCode.ETH.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_eth_grey;
        } else if (ChainCode.HECO.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_heco_grey;
        } else if (ChainCode.OEC.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_oec_grey;
        } else if (ChainCode.TRON.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_tron_grey;
        } else if (ChainCode.POLYGON.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_matic_grey;
        } else if (ChainCode.FANTOM.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_ftm_grey;
        }
        return 0;
    }

    public static int getLinkImage(String chainCode) {
        if (ChainCode.BSC.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_bsc;
        } else if (ChainCode.BTC.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_btc;
        } else if (ChainCode.ETH.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_eth;
        } else if (ChainCode.HECO.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_heco;
        } else if (ChainCode.OEC.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_oec;
        } else if (ChainCode.TRON.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_tron;
        } else if (ChainCode.POLYGON.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_matic;
        } else if (ChainCode.FANTOM.equals(chainCode)) {
            return R.drawable.ic_wallet_select_link_ftm;
        }
        return 0;
    }

    public static String getIconCoin(String chainCode, String contractId) {
        if (TextUtils.isEmpty(contractId)) {
            return MessageFormat.format(
                    "{0}token-icons/{1}/assets/0x0000000000000000000000000000000000000000/logo.png",
                    GlobalConstant.BASE_URL, chainCode.toLowerCase());
        }
        return MessageFormat.format(
                "{0}token-icons/{1}/assets/{2}/logo.png",
                GlobalConstant.BASE_URL, chainCode.toLowerCase(), contractId);
    }

    public static RequestBody generateJsonStr(Map<String, String> options) {
        JSONObject result = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                result.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json"), result.toString());
    }

    public static RequestBody generateJson(Map<String, Object> options) {
        JSONObject result = new JSONObject();
        try {
            for (Map.Entry<String, Object> entry : options.entrySet()) {
                result.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json"), result.toString());
    }

    public static void toggleVisible(EditText editText) {
        passwordVisible(editText, editText.getTransformationMethod() == PasswordTransformationMethod.getInstance());
    }

    public static void passwordVisible(EditText editText, boolean isHidden) {
        if (isHidden) {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //editText可见
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());//editText不可见
        }
        editText.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = editText.getText();
        if (charSequence != null) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    public static final Pattern PATTERN_NUMERIC = Pattern.compile("[0-9]*");

    public static boolean isNumeric(String str) {
        return PATTERN_NUMERIC.matcher(str).matches();
    }

    public static String getSubString(String data, int max) {
        if (data == null || data.length() <= max) {
            return data;
        }
        return data.substring(0, max - 1) + "···";
    }

    public static void setTextViewDrawable(TextView textView,
                                           @DrawableRes int drawableRes,
                                           @Slide.GravityFlag int gravity) {
        Drawable drawable;
        if (drawableRes == 0) {
            drawable = null;
        } else {
            drawable = ContextCompat.getDrawable(textView.getContext(), drawableRes);
        }
        setTextViewDrawable(textView, drawable, gravity);
    }

    public static void setTextViewDrawable(TextView textView,
                                           Drawable drawable,
                                           @Slide.GravityFlag int gravity) {
        if (gravity == Gravity.START || gravity == Gravity.LEFT) {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        } else if (gravity == Gravity.END || gravity == Gravity.RIGHT) {
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else if (gravity == Gravity.TOP) {
            textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
        }
    }

    public static void setTextViewDrawable(TextView textView,
                                           Drawable drawable,
                                           int sizeDp,
                                           @Slide.GravityFlag int gravity) {
        int sizePx = ArmsUtils.dip2px(textView.getContext(), sizeDp);
        drawable.setBounds(0, 0, sizePx, sizePx);
        if (gravity == Gravity.START || gravity == Gravity.LEFT) {
            textView.setCompoundDrawables(drawable, null, null, null);
        } else if (gravity == Gravity.END || gravity == Gravity.RIGHT) {
            textView.setCompoundDrawables(null, null, drawable, null);
        } else if (gravity == Gravity.TOP) {
            textView.setCompoundDrawables(null, drawable, null, null);
        } else {
            textView.setCompoundDrawables(null, null, null, drawable);
        }
    }

    /**
     * 把内容复制到剪切板
     *
     * @param text    复制到剪切板的内容
     * @param context Context
     */
    public static void copyToClipboard(final String text, @NonNull Context context) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                context.getApplicationContext().getSystemService(
                        Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText(null, text));
        }
    }

    /**
     * @param version1
     * @param version2
     * @return 比较版本号的大小, 前者大则返回一个正数, 后者大返回一个负数, 相等则返回0
     */
    public static int compareVersion(String version1, String version2) {
        if (version1 == null || version2 == null) {
            ToastUtils.showDebug("compareVersion error:illegal params.");
        }
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    public static Map<String, String> getUrlParams(String url) {
        Map<String, String> queryPairs = new ConcurrentHashMap<>();
        if (url.contains("?")) {
            for (String pair : url.substring(url.indexOf("?") + 1).split("&")) {
                int idx = pair.indexOf("=");
                try {
                    String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
                    String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
                    if (!key.isEmpty()) {
                        queryPairs.put(key, value);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return queryPairs;
    }

    /**
     * 修改某个Activity的亮度:使用场景->打开二维码
     *
     * @param activity
     * @param brightnessValue
     */
    public static void setBrightness(Activity activity, @FloatRange(from = 0.0f, to = 1.0f) float brightnessValue) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        if (brightnessValue > 1.0f) {
            lp.screenBrightness = 1.0f;
        } else if (brightnessValue <= 0.0f) {
            lp.screenBrightness = 0.0f;
        } else {
            lp.screenBrightness = brightnessValue;
        }
        activity.getWindow().setAttributes(lp);
    }

    public static void vibrator(Context mContext) {
        vibrator(mContext, 100);
    }

    public static void vibrator(Context mContext, @IntRange(from = 50, to = 200) int time) {
        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time);
    }

    public static String formatBigNumbers(BigDecimal bigDecimal) {
        return formatBigNumbers(bigDecimal, -1);
    }

    public static String formatBigNumbersNOKB(BigDecimal bigDecimal,int newScale) {
        return bigDecimal.setScale(newScale, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
    }

    /*成交额单位:K=千，M=百万，B=10亿*/
    public static String formatBigNumbers(BigDecimal bigDecimal, int unformatScale) {
        if (bigDecimal.floatValue() > 1000000000) {
            return bigDecimal.divide(new BigDecimal(1000000000), 2, RoundingMode.FLOOR).toPlainString() + "B";
        } else if (bigDecimal.floatValue() > 1000000) {
            return bigDecimal.divide(new BigDecimal(1000000), 2, RoundingMode.FLOOR).toPlainString() + "M";
        } else if (bigDecimal.floatValue() > 1000) {
            return bigDecimal.divide(new BigDecimal(1000), 2, RoundingMode.FLOOR).toPlainString() + "K";
        } else {
            if (unformatScale == -1) {
                return bigDecimal.toPlainString();
            }
            return bigDecimal.setScale(unformatScale, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
        }
    }

}
