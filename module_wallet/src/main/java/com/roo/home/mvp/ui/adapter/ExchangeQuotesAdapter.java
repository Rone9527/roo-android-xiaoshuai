package com.roo.home.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.trade.QuoteBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.ImageOptions;
import com.roo.core.utils.Kits;
import com.roo.core.utils.utils.TickerManager;
import com.roo.home.R;

import java.math.BigDecimal;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : wty
 *     create time : 2021/7/22 14:07
 *     desc        : 描述--//ExchangeQuotesAdapter 交易所Adapter
 * </pre>
 */

public class ExchangeQuotesAdapter extends BaseQuickAdapter<QuoteBean, BaseViewHolderImpl> {
    String symbol;

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Inject
    public ExchangeQuotesAdapter() {
        super(R.layout.item_exchange_quotes);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, QuoteBean item) {
        helper.setText(R.id.tvName, Kits.Empty.check(item.getNameZh()) ? item.getName() : item.getNameZh())
                .setText(R.id.tvCouple, item.getPair1() + "/" + item.getPair2())
                .setText(R.id.tvPrice, TickerManager.getInstance()//单价
                        .getPriceOnlyByPrice(item.getPrice(), TickerManager.SCALE_DEFAULT).toPlainString())
                .setText(R.id.tvAmount, TickerManager.getInstance().getDecimals(new BigDecimal(item.getVol())));
//        helper.setImageUrl(R.id.ivLogo, item.getLogo());
        try {
            helper.setImageUrl(R.id.ivLogo, item.getLogo());
        } catch (Exception exception) {
            exception.printStackTrace();
            helper.setImageUrl(R.id.ivLogo, R.drawable.ic_common_error_pic_circle);
        }


    }
}
