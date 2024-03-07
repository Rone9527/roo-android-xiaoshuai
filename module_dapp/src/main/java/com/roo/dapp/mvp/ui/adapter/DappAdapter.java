package com.roo.dapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.dapp.DappBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.dapp.R;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/13 10:26
 *     desc        : 描述-- Dapp适配器
 * </pre> layout_head_dapp
 */

public class DappAdapter extends BaseQuickAdapter<DappBean, BaseViewHolderImpl> {

    @Inject
    public DappAdapter() {
        super(R.layout.item_dapp_line_item);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, DappBean item) {
        helper.setText(R.id.tvDappName, item.getName());
        helper.setText(R.id.tvDappInfo, item.getDiscription());
        helper.setCircleImageUrl(R.id.ivDapp, item.getIcon());
    }
}
