package com.roo.trade.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.trade.FinanceBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.trade.R;

import java.math.BigDecimal;
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

public class FinanceAdapter extends BaseQuickAdapter<FinanceBean.DataDTO, BaseViewHolderImpl> {

    private static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;


    @Inject
    public FinanceAdapter() {
        super(R.layout.item_finance);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, FinanceBean.DataDTO item) {
        helper.setText(R.id.tvName, item.getName());

        helper.setText(R.id.tvProfitPercent, MessageFormat.format("{0}%",
                item.getRateOfReturn().multiply(new BigDecimal(100))
                        .stripTrailingZeros().toPlainString()));

        helper.setText(R.id.tvName, item.getName());
        helper.setText(R.id.tvSupport, item.getAscription().concat(mContext.getString(R.string.x_support)));
        LinearLayout layoutTag = helper.itemView.findViewById(R.id.layoutTag);
        if (TextUtils.isEmpty(item.getTag())) {
            layoutTag.setVisibility(View.GONE);
        } else {
            layoutTag.removeAllViews();
            layoutTag.setVisibility(View.VISIBLE);
            String[] tags = item.getTag().split(",");
            for (int i = 0; i < tags.length; i++) {
                TextView tvTag = new TextView(mContext);
                tvTag.setText(tags[i]);
                tvTag.setTextSize(12);
                tvTag.setGravity(Gravity.CENTER);
                tvTag.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_circle_3_blue));
                tvTag.setTextColor(ContextCompat.getColor(mContext, R.color.white_color_assist_2));
                tvTag.setPadding(15, 3, 15, 3);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                layoutParams.setMargins(i == 0 ? 0 : 20, 0, 0, 0);
                tvTag.setLayoutParams(layoutParams);
                layoutTag.addView(tvTag);
            }
        }
        helper.setCircleImageUrl(helper.itemView.findViewById(R.id.ivDapp), item.getLogo());
    }

}
