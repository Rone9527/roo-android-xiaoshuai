package com.roo.home.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.home.R;
import com.roo.core.model.UserWallet;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/5 16:25
 *     desc        : 描述--//WalletListAdapter 管理钱包
 * </pre>
 */

public class WalletListAdapter extends BaseQuickAdapter<UserWallet, BaseViewHolderImpl> {

    public WalletListAdapter() {
        super(R.layout.item_wallet_list);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, UserWallet item) {
        helper.setText(R.id.tvWalletName, item.getRemark());
        helper.setGone(R.id.ivIndicator, item.isSelected());
    }
}