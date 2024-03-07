package com.roo.home.mvp.utils;

import android.text.TextUtils;

import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class BalanceUtils {
    public static final String weiInEth = "1000000000000000000";

    public static BigDecimal weiToEth(BigInteger wei, int decimals) {
//        WEI("wei", 0),
//                KWEI("kwei", 3),
//                MWEI("mwei", 6),
//                GWEI("gwei", 9),
//                SZABO("szabo", 12),
//                FINNEY("finney", 15),
//                ETHER("ether", 18),
//                KETHER("kether", 21),
//                METHER("mether", 24),
//                GETHER("gether", 27);
        switch (decimals) {
            case 0:
                return Convert.fromWei(new BigDecimal(wei), Convert.Unit.WEI);
            case 3:
                return Convert.fromWei(new BigDecimal(wei), Convert.Unit.KWEI);
            case 6:
                return Convert.fromWei(new BigDecimal(wei), Convert.Unit.MWEI);
            case 9:
                return Convert.fromWei(new BigDecimal(wei), Convert.Unit.GWEI);
            case 12:
                return Convert.fromWei(new BigDecimal(wei), Convert.Unit.SZABO);
            case 18:
                return Convert.fromWei(new BigDecimal(wei), Convert.Unit.ETHER);
            case 21:
                return Convert.fromWei(new BigDecimal(wei), Convert.Unit.KETHER);
            case 24:
                return Convert.fromWei(new BigDecimal(wei), Convert.Unit.METHER);
            case 27:
                return Convert.fromWei(new BigDecimal(wei), Convert.Unit.GETHER);

        }

        return Convert.fromWei(new BigDecimal(wei), Convert.Unit.ETHER);
    }

    public static String weiToEth(BigInteger wei, int sigFig, int decimals) {
        return weiToEthBig(wei, sigFig, decimals).toString();
    }

    public static BigDecimal weiToEthBig(BigInteger wei, int sigFig, int decimals) {
        BigDecimal eth = weiToEth(wei, decimals);
        int scale = sigFig - eth.precision() + eth.scale();
        BigDecimal eth_scaled = eth.setScale(scale, RoundingMode.HALF_DOWN);
        return eth_scaled;
    }

    /**
     * 汇率转换计算
     *
     * @param priceUsd 单位价格
     * @param balance  转换数量
     * @return
     */
    public static String a2b(String priceUsd, String balance) {
        if (TextUtils.isEmpty(priceUsd) || TextUtils.isEmpty(balance)) return "0";
        BigDecimal usd = new BigDecimal(balance).multiply(new BigDecimal(priceUsd));
        usd = usd.setScale(2, RoundingMode.HALF_UP);
        return usd.toString();
    }

    public static BigDecimal a2bDecimal(String priceUsd, String balance) {
        if (TextUtils.isEmpty(priceUsd) || TextUtils.isEmpty(balance)) return BigDecimal.ZERO;

        BigDecimal usd = new BigDecimal(balance).multiply(new BigDecimal(priceUsd));
        usd = usd.setScale(2, RoundingMode.HALF_UP);//保留n位小数四舍五入
        return usd;
    }

    public static BigDecimal a2bDecimal(String priceUsd, String balance, int sig) {
        if (TextUtils.isEmpty(priceUsd) || TextUtils.isEmpty(balance))
            return BigDecimal.ZERO.setScale(sig, RoundingMode.DOWN);
        BigDecimal usd = new BigDecimal(balance).multiply(new BigDecimal(priceUsd));
        usd = usd.setScale(sig, RoundingMode.DOWN);
        return usd;
    }

    public static BigInteger eth2Wei(String eth) {
        return new BigDecimal(eth).multiply(new BigDecimal(weiInEth)).toBigInteger();
    }

    public static BigDecimal weiToGweiBI(BigInteger wei) {
        return Convert.fromWei(new BigDecimal(wei), Convert.Unit.GWEI);
    }

    public static String weiToGwei(BigInteger wei) {
        Long.valueOf("");
        return Convert.fromWei(new BigDecimal(wei), Convert.Unit.GWEI).toPlainString();
    }

    public static BigInteger gweiToWei(BigDecimal gwei) {
        return Convert.toWei(gwei, Convert.Unit.GWEI).toBigInteger();
    }

    public static BigDecimal tokenToWei(BigDecimal number, int decimals) {
        BigDecimal weiFactor = BigDecimal.TEN.pow(decimals);
        return number.multiply(weiFactor);
    }

    /**
     * Base - taken to mean default unit for a currency e.g. ETH, DOLLARS
     * Subunit - taken to mean subdivision of base e.g. WEI, CENTS
     *
     * @param baseAmountStr - decimal amonut in base unit of a given currency
     * @param decimals      - decimal places used to convert to subunits
     * @return amount in subunits
     */
    public static BigInteger baseToSubunit(String baseAmountStr, int decimals) {
        assert (decimals >= 0);
        BigDecimal baseAmount = new BigDecimal(baseAmountStr);
        BigDecimal subunitAmount = baseAmount.multiply(BigDecimal.valueOf(10).pow(decimals));
        try {
            return subunitAmount.toBigIntegerExact();
        } catch (ArithmeticException ex) {
            assert (false);
            return subunitAmount.toBigInteger();
        }
    }

    /**
     * @param subunitAmount - amouunt in subunits
     * @param decimals      - decimal places used to convert subunits to base
     * @return amount in base units
     */
    public static BigDecimal subunitToBase(BigInteger subunitAmount, int decimals) {
        assert (decimals >= 0);
        return new BigDecimal(subunitAmount).divide(BigDecimal.valueOf(10).pow(decimals));
    }
}
