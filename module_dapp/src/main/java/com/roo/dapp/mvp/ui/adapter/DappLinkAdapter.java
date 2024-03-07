package com.roo.dapp.mvp.ui.adapter;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.aries.ui.view.radius.RadiusTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.dapp.DappTypeBean;
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

public class DappLinkAdapter extends BaseQuickAdapter<DappTypeBean, BaseViewHolderImpl> {

    public DappLinkAdapter() {
        super(R.layout.item_dapp_link_item);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, DappTypeBean item) {
        helper.setText(R.id.tvLink, item.getName());
        RadiusTextView tvLink = helper.getView(R.id.tvLink);
        Drawable drawable;
        if (item.isSelect()) {
            drawable = ContextCompat.getDrawable(mContext, R.drawable.shape_dapp_link_item);
        } else {
            drawable = null;
        }
        tvLink.getDelegate().setBottomDrawable(drawable).init();

        tvLink.setSelected(item.isSelect());
    }
}