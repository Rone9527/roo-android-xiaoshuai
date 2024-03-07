package com.roo.home.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.trade.NewAssetsBean;
import com.roo.core.daoManagers.NewAssetsDaoManager;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.Kits;
import com.roo.home.R;

import java.math.BigDecimal;
import java.text.MessageFormat;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/5 11:02
 *     desc        : 描述--//AssetSearchAdapter 添加资产币种搜索
 * </pre>
 */

public class MyAssetsAdapter extends BaseQuickAdapter<NewAssetsBean, BaseViewHolderImpl> {

    @Inject
    public MyAssetsAdapter() {
        super(R.layout.item_my_assets);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, NewAssetsBean item) {
        boolean isExist = NewAssetsDaoManager.isAssetsBeanExistInWallet(item);
        helper.setImageResource(R.id.ivAssetAdd, isExist ? R.drawable.ic_home_asset_search_add_ed :
                R.drawable.ic_home_asset_search_add);

        if (Kits.Empty.check(item.getTokenVO())) {
            helper.setText(R.id.tvCoinName, item.getSymbol());
            helper.itemView.findViewById(R.id.tvAddress).setVisibility(View.GONE);
            helper.itemView.findViewById(R.id.tvChainCode).setVisibility(View.GONE);
            helper.itemView.findViewById(R.id.ivAssetAdd).setVisibility(View.GONE);
        } else {
            helper.itemView.findViewById(R.id.tvAddress).setVisibility(View.VISIBLE);
            helper.itemView.findViewById(R.id.tvChainCode).setVisibility(View.VISIBLE);
            helper.itemView.findViewById(R.id.ivAssetAdd).setVisibility(View.VISIBLE);
            helper.setText(R.id.tvAddress, item.getContractId());
            helper.setText(R.id.tvChainCode, "(" + item.getChainCode() + ")");
            helper.setText(R.id.tvCoinName, item.getTokenVO().getSymbol());
        }
        helper.addOnClickListener(R.id.ivAssetAdd);
        helper.setText(R.id.tvBalance, MessageFormat.format(helper.itemView.getContext().getString(R.string.assets_balance), new BigDecimal(item.getAvailableBalance()).setScale(4, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString()));
        helper.setCircleImageUrl(R.id.ivCoin,
                GlobalUtils.getIconCoin(item.getChainCode(), item.getContractId()));
    }
}
