package com.roo.trade.mvp.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.trade.DeFiDataBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.ColorWalletUtil;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.ImageOptions;
import com.roo.trade.R;
import com.roo.trade.mvp.ui.widget.AutoFitTextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : wty
 *     create time : 2021/8/02 11:24
 *     desc        : 描述--//DeFiAdapter DeFiAdapter
 * </pre>
 */

public class DeFiAdapter extends BaseQuickAdapter<DeFiDataBean, BaseViewHolderImpl> {


    @Inject
    public DeFiAdapter() {
        super(R.layout.item_defi);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, DeFiDataBean item) {

        String proportion = new BigDecimal(item.getPrice())
                .setScale(9, RoundingMode.DOWN)
                .stripTrailingZeros().toPlainString();
        if (proportion.length() > 9) {
            proportion = proportion.substring(0, 9) + "…";
        }

        String format;
        if (Float.parseFloat(item.getRateOfPrice()) > 0) {
            format = MessageFormat.format("+{0}%", BigDecimal.valueOf(Double.parseDouble(item.getRateOfPrice()) * 100)
                    .setScale(2, RoundingMode.DOWN)
                    .stripTrailingZeros().toPlainString());
        } else {
            format = MessageFormat.format("{0}%", BigDecimal.valueOf(Double.parseDouble(item.getRateOfPrice()) * 100)
                    .setScale(2, RoundingMode.DOWN)
                    .stripTrailingZeros().toPlainString());
        }

        String pairName = item.getPairName().length() <= 10 ? item.getPairName() : item.getPairName().substring(0, 10) + "…";
        helper.setText(R.id.tvCouple, pairName)
                .setText(R.id.tvProvider, item.getAscription())
                .setText(R.id.tvTVL, MessageFormat.format("TVL:${0}",
                        GlobalUtils.formatBigNumbers(new BigDecimal(item.getVolumeUSD()).setScale(2, RoundingMode.DOWN))))
                .setText(R.id.tvProportion, "1:" + proportion)
                .setText(R.id.tvPriceUSD, "≈$ " + new BigDecimal(item.getPriceUSD()).setScale(2, RoundingMode.DOWN).toPlainString())
                .setText(R.id.cbPercent, format);
        AutoFitTextView cbPercent = helper.getView(R.id.cbPercent);
        cbPercent.setBackground(getMarketColor(mContext,
                new BigDecimal(item.getRateOfPrice()).floatValue()));
//        helper.setCircleImageUrl(R.id.ivLogo, item.getLogo(), ImageOptions.defaultOptionsCircle());
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

