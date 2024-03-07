package com.roo.trade.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.trade.DeFiTradeBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.Kits;
import com.roo.trade.R;

import java.math.BigDecimal;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : wty
 *     create time : 2021/8/03 15:09
 *     desc        : 描述--//DeFiTradeRecentAdapter
 * </pre>
 */

public class DeFiTradeRecentAdapter extends BaseQuickAdapter<DeFiTradeBean, BaseViewHolderImpl> {


    @Inject
    public DeFiTradeRecentAdapter() {
        super(R.layout.item_defi_trade);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, DeFiTradeBean item) {
        TextView tvType = helper.itemView.findViewById(R.id.tvType);
        ImageView ivType = helper.itemView.findViewById(R.id.ivType);

        switch (item.getType()) {
            case "buy":
                tvType.setText(helper.itemView.getContext().getString(R.string.buy_in));
                tvType.setTextColor(helper.itemView.getContext().getColor(R.color.green_color));
                ivType.setImageResource(R.drawable.ic_defi_buy);
                break;
            case "sell":
                tvType.setText(helper.itemView.getContext().getString(R.string.sell_out));
                tvType.setTextColor(helper.itemView.getContext().getColor(R.color.color_txt_red2));
                ivType.setImageResource(R.drawable.ic_defi_sell);
                break;
            case "mint":
                tvType.setText(helper.itemView.getContext().getString(R.string.add_liquid));
                tvType.setTextColor(helper.itemView.getContext().getColor(R.color.green_color));
                ivType.setImageResource(R.drawable.ic_defi_add_liquid);
                break;
            case "burn":
                tvType.setText(helper.itemView.getContext().getString(R.string.reduce_liquid));
                tvType.setTextColor(helper.itemView.getContext().getColor(R.color.color_txt_red2));
                ivType.setImageResource(R.drawable.ic_defi_reduce_liquid);
                break;
        }

        helper.setText(R.id.tvToken0, item.getToken0Symbol())
                .setText(R.id.tvToken1, item.getToken1Symbol())
                .setText(R.id.tvAmountToken0, GlobalUtils.formatBigNumbers(new BigDecimal(item.getToken0Amount()), 6))
                .setText(R.id.tvAmountToken1, GlobalUtils.formatBigNumbers(new BigDecimal(item.getToken1Amount()), 6))
                .setText(R.id.tvDate, Kits.Date.getMdhmLink3(item.getTimestamp() * 1000));
    }

}

