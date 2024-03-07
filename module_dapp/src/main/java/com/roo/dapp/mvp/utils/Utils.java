package com.roo.dapp.mvp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Patterns;
import android.util.TypedValue;
import android.webkit.URLUtil;

import androidx.annotation.RawRes;

import com.roo.core.utils.LogManage;
import com.roo.dapp.mvp.beans.ProviderTypedData;
import com.roo.dapp.mvp.ui.fragment.DappBrowserFragmentNew;


import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {

    private static final String ISOLATE_NUMERIC = "(0?x?[0-9a-fA-F]+)";
    private static final String ICON_REPO_ADDRESS_TOKEN = "[TOKEN]";
    private static final String CHAIN_REPO_ADDRESS_TOKEN = "[CHAIN]";
    public static final String ROOWALLET_REPO_NAME = "roowallet/iconassets";
    private static final String TRUST_ICON_REPO = "https://raw.githubusercontent.com/trustwallet/assets/master/blockchains/" + CHAIN_REPO_ADDRESS_TOKEN + "/assets/" + ICON_REPO_ADDRESS_TOKEN + "/logo.png";
    private static final String ROOWALLET_ICON_REPO = "https://raw.githubusercontent.com/" + ROOWALLET_REPO_NAME + "/master/" + ICON_REPO_ADDRESS_TOKEN + "/logo.png";

    public static int dp2px(Context context, int dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }

    public static String formatUrl(String url) {
        if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
            return url;
        } else {
            if (isValidUrl(url)) {
                return "https://" + url;
            } else {
                return url;
            }
        }
    }

    public static boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }

    public static boolean isAlNum(String testStr) {
        boolean result = false;
        if (testStr != null && testStr.length() > 0) {
            result = true;
            for (int i = 0; i < testStr.length(); i++) {
                char c = testStr.charAt(i);
                if (!Character.isIdeographic(c) && !Character.isLetterOrDigit(c) && !Character.isWhitespace(c) && (c < 32 || c > 126)) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    public static boolean isValidValue(String testStr) {
        boolean result = false;
        if (testStr != null && testStr.length() > 0) {
            result = true;
            for (int i = 0; i < testStr.length(); i++) {
                char c = testStr.charAt(i);
                if (!Character.isDigit(c) && !(c == '.' || c == ',')) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    private static String getFirstWord(String text) {
        if (TextUtils.isEmpty(text)) return "";
        text = text.trim();
        int index;
        for (index = 0; index < text.length(); index++) {
            if (!Character.isLetterOrDigit(text.charAt(index))) break;
        }

        return text.substring(0, index).trim();
    }

    public static String getIconisedText(String text) {
        if (TextUtils.isEmpty(text)) return "";
        String firstWord = getFirstWord(text);
        if (!TextUtils.isEmpty(firstWord)) {
            return firstWord.substring(0, Math.min(firstWord.length(), 4)).toUpperCase();
        } else {
            return "";
        }
    }

    public static String getShortSymbol(String text) {
        if (TextUtils.isEmpty(text)) return "";
        String firstWord = getFirstWord(text);
        if (!TextUtils.isEmpty(firstWord)) {
            return firstWord.substring(0, Math.min(firstWord.length(), 5)).toUpperCase();
        } else {
            return "";
        }
    }


    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static boolean copyFile(String source, String dest) {
        try {
            FileChannel s = new FileInputStream(source).getChannel();
            FileChannel d = new FileOutputStream(dest).getChannel();
            d.transferFrom(s, 0, s.size());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isAddressValid(String address) {
        return address != null && address.length() > 0 && WalletUtils.isValidAddress(address);
    }

    public static String intArrayToString(Integer[] values) {
        StringBuilder store = new StringBuilder();
        boolean firstValue = true;
        for (int network : values) {
            if (!firstValue) store.append(",");
            store.append(network);
            firstValue = false;
        }

        return store.toString();
    }

    public static List<Integer> intListToArray(String list) {
        List<Integer> idList = new ArrayList<>();
        //convert to array
        String[] split = list.split(",");
        for (String s : split) {
            Integer value;
            try {
                value = Integer.valueOf(s);
                idList.add(value);
            } catch (NumberFormatException e) {
                //empty
            }
        }

        return idList;
    }

    public static int[] bigIntegerListToIntList(List<BigInteger> ticketSendIndexList) {
        int[] indexList = new int[ticketSendIndexList.size()];
        for (int i = 0; i < ticketSendIndexList.size(); i++)
            indexList[i] = ticketSendIndexList.get(i).intValue();
        return indexList;
    }


    /**
     * Produce a string CSV of integer IDs given an input list of values
     *
     * @param idList
     * @param keepZeros
     * @return
     */
    public static String bigIntListToString(List<BigInteger> idList, boolean keepZeros) {
        if (idList == null) return "";
        String displayIDs = "";
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (BigInteger id : idList) {
            if (!keepZeros && id.compareTo(BigInteger.ZERO) == 0) continue;
            if (!first) {
                sb.append(",");
            }
            first = false;

            sb.append(Numeric.toHexStringNoPrefix(id));
            displayIDs = sb.toString();
        }

        return displayIDs;
    }

    public static List<Integer> stringIntsToIntegerList(String userList) {
        List<Integer> idList = new ArrayList<>();

        try {
            String[] ids = userList.split(",");

            for (String id : ids) {
                //remove whitespace
                String trim = id.trim();
                Integer intId = Integer.parseInt(trim);
                idList.add(intId);
            }
        } catch (Exception e) {
            idList = new ArrayList<>();
        }

        return idList;
    }

    public static String integerListToString(List<Integer> intList, boolean keepZeros) {
        if (intList == null) return "";
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (Integer id : intList) {
            if (!keepZeros && id == 0) continue;
            if (!first) sb.append(",");
            sb.append(String.valueOf(id));
            first = false;
        }

        return sb.toString();
    }

    public static boolean isNumeric(String numString) {
        if (numString == null || numString.length() == 0) return false;

        for (int i = 0; i < numString.length(); i++) {
            if (Character.digit(numString.charAt(i), 10) == -1) {
                return false;
            }
        }

        return true;
    }

    public static boolean isHex(String hexStr) {
        if (hexStr == null || hexStr.length() == 0) return false;
        hexStr = Numeric.cleanHexPrefix(hexStr);

        for (int i = 0; i < hexStr.length(); i++) {
            if (Character.digit(hexStr.charAt(i), 16) == -1) {
                return false;
            }
        }

        return true;
    }

    public static String isolateNumeric(String valueFromInput) {
        try {
            Matcher regexResult = Pattern.compile(ISOLATE_NUMERIC).matcher(valueFromInput);
            if (regexResult.find()) {
                if (regexResult.groupCount() >= 1) {
                    valueFromInput = regexResult.group(0);
                }
            }
        } catch (Exception e) {
            // Silent fail - no action; just return input; this function is only to clean junk from a number
        }

        return valueFromInput;
    }

    public static String formatAddress(String address) {
        address = Keys.toChecksumAddress(address);
        String result = "";
        String firstSix = address.substring(0, 6);
        String lastSix = address.substring(address.length() - 4);
        StringBuilder formatted = new StringBuilder(result);
        return formatted.append(firstSix).append("...").append(lastSix).toString().toLowerCase();
    }

    /**
     * Just enough for diagnosis of most errors
     *
     * @param s String to be HTML escaped
     * @return escaped string
     */
    public static String escapeHTML(String s) {
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"':
                    out.append("&quot;");
                    break;
                case '&':
                    out.append("&amp;");
                    break;
                case '<':
                    out.append("&lt;");
                    break;
                case '>':
                    out.append("&gt;");
                    break;
                default:
                    out.append(c);
            }
        }
        return out.toString();
    }


    public static long randomId() {
        return new Date().getTime();
    }

    public static String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (Exception e) {
            return url != null ? url : "";
        }
    }


    public static String getAWIconRepo(String address) {
        return ROOWALLET_ICON_REPO.replace(ICON_REPO_ADDRESS_TOKEN, Keys.toChecksumAddress(address));
    }

    public static String parseIPFS(String URL) {
        if (TextUtils.isEmpty(URL)) return URL;
        String parsed = URL;
        int ipfsIndex = URL.lastIndexOf("/ipfs/");
        if (ipfsIndex >= 0) {
            parsed = "https://ipfs.io" + URL.substring(ipfsIndex);
        }

        return parsed;
    }

    public static String loadFile(Context context, @RawRes int rawRes) {
        byte[] buffer = new byte[0];
        try {
            InputStream in = context.getResources().openRawResource(rawRes);
            buffer = new byte[in.available()];
            int len = in.read(buffer);
            if (len < 1) {
                throw new IOException("Nothing is read.");
            }
        } catch (Exception ex) {
            LogManage.e(DappBrowserFragmentNew.TAG, ex.getMessage());
        }
        return new String(buffer);
    }

    public static CharSequence formatTypedMessage(ProviderTypedData[] rawData) {
        //produce readable text to display in the signing prompt
        StyledStringBuilder sb = new StyledStringBuilder();
        boolean firstVal = true;
        for (ProviderTypedData data : rawData) {
            if (!firstVal) sb.append("\n");
            sb.startStyleGroup().append(data.name).append(":");
            sb.setStyle(new StyleSpan(Typeface.BOLD));
            sb.append("\n  ").append(data.value.toString());
            firstVal = false;
        }

        sb.applyStyles();

        return sb;
    }

    public static CharSequence formatEIP721Message(StructuredDataEncoder messageData) {
        HashMap<String, Object> messageMap = (HashMap<String, Object>) messageData.jsonMessageObject.getMessage();
        StyledStringBuilder sb = new StyledStringBuilder();
        for (String entry : messageMap.keySet()) {
            sb.startStyleGroup().append(entry).append(":").append("\n");
            sb.setStyle(new StyleSpan(Typeface.BOLD));
            Object v = messageMap.get(entry);
            if (v instanceof LinkedHashMap) {
                HashMap<String, Object> valueMap = (HashMap<String, Object>) messageMap.get(entry);
                for (String paramName : valueMap.keySet()) {
                    String value = valueMap.get(paramName).toString();
                    sb.startStyleGroup().append(" ").append(paramName).append(": ");
                    sb.setStyle(new StyleSpan(Typeface.BOLD));
                    sb.append(value).append("\n");
                }
            } else {
                sb.append(" ").append(v.toString()).append("\n");
            }
        }

        sb.applyStyles();

        return sb;
    }
}
