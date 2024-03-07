package com.roo.mine.mvp.ui.adapter;


import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.core.domain.manager.ChainCode;
import com.core.domain.mine.MessageSystem;
import com.core.domain.mine.MessageTrade;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.ImageOptions;
import com.roo.core.utils.Kits;
import com.roo.mine.R;
import com.roo.mine.mvp.ui.activity.NoticeActivity;

import java.text.MessageFormat;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/7 14:19
 *     desc        : 描述--//NoticeAdapter 交易通知
 * </pre>
 */

public class NoticeAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolderImpl> {

    @Inject
    public NoticeAdapter() {
        super(null);

        addItemType(NoticeActivity.TYPE_TRADE, R.layout.item_notice);
        addItemType(NoticeActivity.TYPE_SYSTEM, R.layout.item_notice_sys);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, MultiItemEntity data) {
        if (helper.getItemViewType() == NoticeActivity.TYPE_TRADE) {
            MessageTrade item = (MessageTrade) data;
            helper.setText(R.id.tvCoinDesc, MessageFormat.format("{0} {1} " + mContext.getString(R.string.state_succ_cashier),
                    item.getAmount(), item.getToken()))//273823.31 FNC 收款成功
                    .setText(R.id.tvAddress, MessageFormat.format(mContext.getString(R.string.receive_address_two), item.getToAddr()))//接收地址 xxx
                    .setText(R.id.tvTime, ((MessageTrade) data).getChainCode().equalsIgnoreCase(ChainCode.TRON) ? Kits.Date.getMdyhms(item.getTime()) : Kits.Date.getMdyhms(item.getTime() * 1000));//09/04/2020 14:32:52

            helper.setBackgroundColor(R.id.layoutRoot, ContextCompat.getColor(mContext,
                    item.isReadStatus() ? R.color.white : R.color.background_color_dark));

            helper.setCircleImageUrl(R.id.ivNotice, R.drawable.ic_mine_transfer_side_out,
                    ImageOptions.defaultOptionsCircle());
        } else if (helper.getItemViewType() == NoticeActivity.TYPE_SYSTEM) {

            MessageSystem item = (MessageSystem) data;
            helper.setText(R.id.tvTitle, item.getMsgTitle())
                    .setText(R.id.tvDesc, item.getMsgContent())
                    //09/04/2020 14:32:52
                    .setText(R.id.tvTime, Kits.Date.getMdhmLink(item.getPublishTime() * 1000));

            helper.setBackgroundColor(R.id.layoutRoot, ContextCompat.getColor(mContext,
                    item.isReadStatus() ? R.color.white : R.color.background_color_dark));
        }
    }

}
