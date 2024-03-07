package com.roo.mine.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.mine.AddressBookBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.mine.R;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/7 14:19
 *     desc        : 描述--//AddressBookAdapter 地址簿
 * </pre>
 */

public class AddressBookAdapter extends BaseQuickAdapter<AddressBookBean, BaseViewHolderImpl> {

    @Inject
    public AddressBookAdapter() {
        super(R.layout.item_address_book);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, AddressBookBean item) {
        helper.setText(R.id.tvBookName, item.getRemark())
                .setText(R.id.tvAddress, item.getAddress())
                .setText(R.id.tvBookDesc, item.getDescrible());

        helper.setCircleImageUrl(R.id.ivCoin, item.getIcon());
    }
}
