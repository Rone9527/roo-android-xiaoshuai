package com.roo.dapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.dapp.DappBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.dapp.R;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/7/29 13:57
 *     desc        : 描述--//DappLinkAdapter TODO
 * </pre>
 */

public class DappGridAdapter extends BaseQuickAdapter<DappBean, BaseViewHolderImpl> {

    public DappGridAdapter() {
        super(R.layout.item_dapp_grid_item);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, DappBean item) {
        helper.setText(R.id.tvDappName, item.getName());
        helper.setCircleImageUrl(R.id.ivDapp, item.getIcon());
    }
}