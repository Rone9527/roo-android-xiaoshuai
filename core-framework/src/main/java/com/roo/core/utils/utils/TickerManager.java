package com.roo.core.utils.utils;

import android.text.TextUtils;

import com.core.domain.manager.ChainCode;
import com.core.domain.mine.LegalCurrencyBean;
import com.core.domain.mine.RateBean;
import com.core.domain.trade.TickerBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.constants.Constants;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.LogManage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/13 18:12
 *     desc        : 描述--//TickerManager
 * </pre>
 */

public class TickerManager {
    public static final String KEY_TICKER = "KEY_TICKER";

    public static final int SCALE_NOT = -1;
    public static final int SCALE_DEFAULT = -2;
    private LegalCurrencyBean currentLegal;

    private TickerManager() {

    }

    public static TickerManager getInstance() {
        return Holder.instantce;
    }

    /*对法币的值进行小数符号处理*/
    public String getDecimals(BigDecimal legalValue) {
        return GlobalUtils.formatBigNumbers(legalValue, getLegal().getDecimals());
    }

    /*获取{amount}个资产的法币价值：有单位  价格x数量  */
    public String getLegalValueWithSymbol(String baseAsset, BigDecimal amount) {
        return getLegalValueWithSymbol(baseAsset, amount, SCALE_NOT);
    }

    public String getLegalValueWithSymbol(String baseAsset, BigDecimal amount, int scale) {
        BigDecimal legalValue = getLegalValue(baseAsset, amount, scale);
        LegalCurrencyBean legalCurrency = getLegal();

        if (scale == SCALE_NOT) {
            return MessageFormat.format("{0} {1}", legalCurrency.getIcon(),
                    GlobalUtils.formatBigNumbers(legalValue));
        } else {
            int unformatScale = scale == SCALE_DEFAULT ? legalCurrency.getDecimals() : scale;
            return MessageFormat.format("{0} {1}", legalCurrency.getIcon(),
                    GlobalUtils.formatBigNumbers(legalValue, unformatScale)
            );
        }

    }

    public String getLegalValueWithSymbolNOKB(String baseAsset, BigDecimal amount, int scale) {
        BigDecimal legalValue = getLegalValue(baseAsset, amount, scale);
        LegalCurrencyBean legalCurrency = getLegal();

        return MessageFormat.format("{0} {1}", legalCurrency.getIcon(),
                GlobalUtils.formatBigNumbersNOKB(legalValue,scale));

    }


    /*获取amount个资产的法币价值：不带单位  价格x数量  */
    public BigDecimal getLegalValue(String baseAsset, BigDecimal amount) {
        return getLegalValue(baseAsset, amount, SCALE_NOT);
    }

    public BigDecimal getLegalValue(String baseAsset, BigDecimal amount, int scale) {
        TickerBean tickerBean = getTickerByBaseAsset(baseAsset);
        if (tickerBean != null) {
            LegalCurrencyBean legalCurrency = getLegal();
            BigDecimal rateNumber = getRate(legalCurrency);

            if (scale == SCALE_NOT) {
                return tickerBean.getPrice().multiply(amount).multiply(rateNumber);
            } else {
                return tickerBean.getPrice().multiply(amount).multiply(rateNumber)
                        .setScale(scale == SCALE_DEFAULT ? legalCurrency.getDecimals() : scale, RoundingMode.DOWN);
            }
        }
        return BigDecimal.ZERO;
    }


    /*获取交易对的法币价格*/
    public String getLegalPriceWithSymbol(String baseAsset, int scale) {
        BigDecimal legalValue = getLegalPrice(baseAsset, scale);
        LegalCurrencyBean legalCurrency = getLegal();

        if (scale == SCALE_NOT) {

            return MessageFormat.format("{0} {1}", legalCurrency.getIcon(),
                    legalValue.stripTrailingZeros());
        } else {
            int newScale = (scale == SCALE_DEFAULT) ? legalCurrency.getDecimals() : scale;
            String price = legalValue.setScale(newScale, RoundingMode.DOWN)
                    .stripTrailingZeros().toPlainString();
            return MessageFormat.format("{0} {1}", legalCurrency.getIcon(), price);
        }
    }


    /*获取交易对的法币价格*/
    public BigDecimal getLegalPrice(String baseAsset, int scale) {
        TickerBean tickerBean = getTickerByBaseAsset(baseAsset);
        if (tickerBean != null) {
            LegalCurrencyBean legalCurrency = getLegal();
            BigDecimal rateNumber = getRate(legalCurrency);

            if (scale == SCALE_NOT) {
                return tickerBean.getPrice().multiply(rateNumber);
            } else {
                return tickerBean.getPrice().multiply(rateNumber)
                        .setScale(scale == SCALE_DEFAULT ? legalCurrency.getDecimals() : scale, RoundingMode.DOWN);
            }
        }
        return BigDecimal.ZERO;
    }

    /*获取市值*/

    public String getMarketCapUsd(String baseAsset, int scale) {
        TickerBean tickerBean = getTickerByBaseAsset(baseAsset);
        BigDecimal legalValue = BigDecimal.ZERO;
        LegalCurrencyBean legalCurrency = getLegal();
        if (tickerBean != null) {

            BigDecimal rateNumber = getRate(legalCurrency);
            legalValue = tickerBean.getMarketCapUsd().multiply(rateNumber);
        }
        if (scale == SCALE_NOT) {

            if (legalValue.floatValue() > 1000) {
                return MessageFormat.format("{0} {1}", legalCurrency.getIcon(),
                        GlobalUtils.formatBigNumbers(legalValue));
            } else {
                return MessageFormat.format("{0} {1}", legalCurrency.getIcon(),
                        legalValue.toPlainString());
            }

        } else {
            int newScale = (scale == SCALE_DEFAULT) ? legalCurrency.getDecimals() : scale;
            if (legalValue.floatValue() > 1000) {
                return MessageFormat.format("{0} {1}", legalCurrency.getIcon(),
                        GlobalUtils.formatBigNumbers(legalValue, newScale));
            } else {
                return MessageFormat.format("{0} {1}", legalCurrency.getIcon(),
                        legalValue.setScale(newScale, RoundingMode.DOWN).toPlainString());
            }

        }
    }

    public BigDecimal getDecimalSymbolCount(String baseAsset, BigDecimal symbolCount) {
        TickerBean ticker = TickerManager.getInstance().getTickerByBaseAsset(baseAsset);

        if (baseAsset.equals(ChainCode.TRON)) {
            return symbolCount.setScale(6, RoundingMode.DOWN);
        }else {
            if (ticker == null) {
                return symbolCount.stripTrailingZeros();
            }
        }
        return symbolCount.setScale(ticker.getDecimals(), RoundingMode.DOWN);
    }

    /*获取交易对的法币价格*/
    public BigDecimal getPriceOnlyByPrice(String price, int scale) {
        LegalCurrencyBean legalCurrency = getLegal();
        BigDecimal rateNumber = getRate(legalCurrency);

        if (scale == SCALE_NOT) {
            return new BigDecimal(price).multiply(rateNumber);
        } else {
            return new BigDecimal(price).multiply(rateNumber)
                    .setScale(scale == SCALE_DEFAULT ? legalCurrency.getDecimals() : scale, RoundingMode.DOWN);
        }
    }

    private static class Holder {
        static TickerManager instantce = new TickerManager();
    }

    public TickerBean getTickerByBaseAsset(String baseAsset) {
        List<TickerBean> list = getTickerList();
        for (TickerBean bean : list) {
            if (bean.getBaseAsset().equals(baseAsset)) {
                return bean;
            }
        }
        return null;
    }

    public void setTickerList(List<TickerBean> data) {
        TickerBean tickerBean = new TickerBean();
        tickerBean.setSymbol("USDT_USDT");
        tickerBean.setBaseAsset("USDT");
        tickerBean.setQuoteAsset("USDT");
        tickerBean.setDecimals(2);
        RateBean rate = SharedPref.getObject(Constants.KEY_OBJ_RATE);
        tickerBean.setPrice(rate == null ? BigDecimal.ONE : rate.getUSD());
        tickerBean.setShow(false);
        data.add(tickerBean);
        SharedPref.putString(KEY_TICKER, new Gson().toJson(data));
    }

    public List<TickerBean> getTickerList() {
        String json = SharedPref.getString(KEY_TICKER);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(json, new TypeToken<List<TickerBean>>() {
        }.getType());
    }

    public LegalCurrencyBean getLegal() {
        return getLegal(false);
    }

    public LegalCurrencyBean getLegal(boolean forceRefresh) {
        if (currentLegal != null && !forceRefresh) {
            return currentLegal;
        }
        currentLegal = SharedPref.getObject(
                Constants.KEY_OBJ_LEGAL_CURRENT, LegalCurrencyBean.valueOfUSD()
        );
        return currentLegal;
    }


    private BigDecimal getRate(LegalCurrencyBean legalCurrency) {
        RateBean rate = SharedPref.getObject(Constants.KEY_OBJ_RATE, RateBean.valueOf());
        switch (legalCurrency.getSymbol()) {
            case LegalCurrencyBean.CNY:
                return rate.getCNY();
            case LegalCurrencyBean.KRW:
                return rate.getKRW();
            case LegalCurrencyBean.USD:
                return rate.getUSD();
            default:
                return rate.getUSD();
        }
    }

}
