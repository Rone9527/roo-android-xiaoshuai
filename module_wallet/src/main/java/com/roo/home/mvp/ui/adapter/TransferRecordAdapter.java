package com.roo.home.mvp.ui.adapter;

import android.widget.Filter;
import android.widget.Filterable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.mine.TransferRecordBean;
import com.roo.core.app.annotation.TransactionStatus;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.ImageOptions;
import com.roo.core.utils.Kits;
import com.roo.core.utils.utils.TickerManager;
import com.roo.home.R;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/5/31 21:51
 *     desc        : 描述--//WalletAdapter
 * </pre>
 */

public class TransferRecordAdapter extends BaseQuickAdapter<TransferRecordBean.DataBean, BaseViewHolderImpl>
        implements Filterable {

    private String address;

    @Inject
    public TransferRecordAdapter() {
        super(R.layout.item_transfer_record, new ArrayList<>());
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                String filterType = charSequence.toString();
                ArrayList<TransferRecordBean.DataBean> result = new ArrayList<>();
                if ("1".equals(filterType)) {
                    for (TransferRecordBean.DataBean item : mData) {
                        if (isSideIn(item)) {//转入状态 pendding 数据不加入显示
                            if (!item.isFromSelf()) {
                                result.add(item);
                            }

                        }
                    }
                } else if ("2".equals(filterType)) {
                    for (TransferRecordBean.DataBean item : mData) {
                        if (!isSideIn(item)) {
                            result.add(item);
                        }
                    }
                } else {
                    result.addAll(mData);
                }
                filterResults.values = result;
                return filterResults;
            }

            //把过滤后的值返回出来
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                Object values = filterResults.values;
                setNewData((List<TransferRecordBean.DataBean>) values);
            }
        };
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, TransferRecordBean.DataBean item) {

        BigDecimal amount = TickerManager.getInstance()
                .getDecimalSymbolCount(item.getToken(), item.getAmount());
        if (isSideIn(item)) {
            helper.setText(R.id.tvAddress, item.getFromAddr());
            helper.setText(R.id.tvCoinAsset, MessageFormat.format("+{0}", amount.toPlainString()));
        } else {
            helper.setText(R.id.tvAddress, item.getToAddr());
            helper.setText(R.id.tvCoinAsset, MessageFormat.format("-{0}", amount.toPlainString()));
        }

        helper.setText(R.id.tvTime, Kits.Date.getMdyhms(item.getTimeStamp() * 1000));

        if (item.getStatusType().equals(TransactionStatus.IN_BLOCK) ||
                item.getStatusType().equals(TransactionStatus.PENDING)) {
            //交易失败 已确认
            helper.setGone(R.id.animationView, true);
            helper.setVisible(R.id.ivCoin, false);
        } else {
            //待处理 打包中
            helper.setGone(R.id.animationView, false);
            helper.setVisible(R.id.ivCoin, true);
            helper.setCircleImageUrl(R.id.ivCoin, getDrawableByStatus(item), ImageOptions.defaultOptionsCircle());

        }
    }

    /*是否是转入*/
    private boolean isSideIn(TransferRecordBean.DataBean item) {
        return address.equalsIgnoreCase(item.getToAddr());
    }

    private int getDrawableByStatus(TransferRecordBean.DataBean item) {
        if (isSideIn(item)) {
            return R.drawable.ic_home_transfer_side_in;
        } else {
            if (item.getStatusType().equals(TransactionStatus.FAIL)) {//交易失败
                return R.drawable.ic_home_transfer_side_out_fail;
            } else {//已确认
                return R.drawable.ic_home_transfer_side_out;
            }
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
