package com.roo.home.mvp.ui.adapter;

import android.text.TextUtils;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.link.LinkTokenBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.utils.TokenManager;
import com.roo.home.R;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/5 11:02
 *     desc        : 描述--//AssetSearchAdapter 添加资产币种搜索
 * </pre>
 */

public class AssetSelectCoinAdapter extends BaseQuickAdapter<LinkTokenBean.TokensBean, BaseViewHolderImpl> {

    private ArrayList<String> tokenMap;

    @Inject
    public AssetSelectCoinAdapter() {
        super(R.layout.item_asset_select_coin);
        tokenMap = TokenManager.getInstance().tokenList();
    }

    public void setTokenMap() {
        tokenMap = TokenManager.getInstance().tokenList();
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, LinkTokenBean.TokensBean item) {
        boolean added = tokenMap.contains(LinkTokenBean.TokensBean.key(item));

        helper.setImageResource(R.id.ivAssetAdd, added ? R.drawable.ic_home_asset_search_add_ed :
                R.drawable.ic_home_asset_search_add);
        helper.setText(R.id.tvCoinName, item.getSymbol())
                .setText(R.id.tvAddress, TextUtils.isEmpty(item.getContractId()) ? (mContext.getString(R.string.main_chain) + item.getNameEn()) : item.getContractId())
                .addOnClickListener(R.id.ivAssetAdd);


        helper.setCircleImageUrl(R.id.ivCoin,
                GlobalUtils.getIconCoin(item.getChainCode(), item.getContractId()));

    }
}
