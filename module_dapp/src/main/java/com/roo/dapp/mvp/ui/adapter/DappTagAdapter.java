package com.roo.dapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.dapp.DappTypeBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.dapp.R;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/7/29 14:00
 *     desc        : 描述--//DappTagAdapter TODO
 * </pre>
 */

public class DappTagAdapter extends BaseQuickAdapter<DappTypeBean.ListBean, BaseViewHolderImpl> {

    public DappTagAdapter() {
        super(R.layout.item_dapp_tag_item);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, DappTypeBean.ListBean item) {
        helper.setText(R.id.tvTag, item.getName());
        helper.getView(R.id.tvTag).setSelected(item.getSeletct());
    }
}
