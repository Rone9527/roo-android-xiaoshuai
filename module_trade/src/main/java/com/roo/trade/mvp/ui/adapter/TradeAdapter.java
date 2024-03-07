package com.roo.trade.mvp.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.CheckBox;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.mine.LegalCurrencyBean;
import com.core.domain.trade.TickerBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.ColorWalletUtil;
import com.roo.core.utils.utils.TickerManager;
import com.roo.trade.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/5/31 18:24
 *     desc        : 描述--//TradeAdapter 行情页面Adapter
 * </pre>
 */

public class TradeAdapter extends BaseQuickAdapter<TickerBean, BaseViewHolderImpl> {


    @Inject
    public TradeAdapter() {
        super(R.layout.item_quote);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, TickerBean item) {
        String baseAsset = item.getBaseAsset();
        String format;
        if (item.getPriceChangePercent().floatValue() > 0) {
            format = MessageFormat.format("+{0}%", item.getPriceChangePercent());
        } else {
            format = MessageFormat.format("{0}%", item.getPriceChangePercent());
        }

        LegalCurrencyBean legal = TickerManager.getInstance().getLegal();
        TickerBean tickerBean = TickerManager.getInstance().getTickerByBaseAsset(baseAsset);

        //USDT价格小数保留规则：若超过1则保留两位小数，若小于1则保留6位小数，尾数为0则隐藏0
        BigDecimal price = tickerBean.getPrice();
        String textPrice;
        if (price.floatValue() > 1) {
            textPrice = price.setScale(2, RoundingMode.DOWN).toPlainString();
        } else {
            textPrice = price.setScale(6, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
        }

        // 法币小数保留规则：保留2位小数
        BigDecimal legalPrice = TickerManager.getInstance().getLegalPrice(baseAsset, 2);
        String textPriceLegal = MessageFormat.format("{0} {1}", legal.getIcon(), legalPrice);

        helper.setText(R.id.cbPercent, format)
                .setText(R.id.tvPrice, textPrice)
                .setText(R.id.tvPriceLegal, textPriceLegal)
                .setText(R.id.tvVolume, TickerManager.getInstance()
                        .getMarketCapUsd(baseAsset, TickerManager.SCALE_DEFAULT))

                .setText(R.id.tvCoinName, baseAsset)
                .setText(R.id.tvIndex, String.valueOf(helper.getAdapterPosition() + 1));
        CheckBox cbPercent = helper.getView(R.id.cbPercent);
        cbPercent.setBackground(getMarketColor(mContext,
                item.getPriceChangePercent().floatValue()));
    }

    public Drawable getMarketColor(Context mContext, float value) {
        if (ColorWalletUtil.getColorType() == 0) {
            if (value > 0) {
                return ContextCompat.getDrawable(mContext, R.drawable.shape_green);
            } else if (value < 0) {
                return ContextCompat.getDrawable(mContext, R.drawable.shape_red);
            } else {
                return ContextCompat.getDrawable(mContext, R.drawable.shape_gray);
            }
        } else {

            if (value > 0) {
                return ContextCompat.getDrawable(mContext, R.drawable.shape_red);
            } else if (value < 0) {
                return ContextCompat.getDrawable(mContext, R.drawable.shape_green);
            } else {
                return ContextCompat.getDrawable(mContext, R.drawable.shape_gray);
            }
        }
    }

}
