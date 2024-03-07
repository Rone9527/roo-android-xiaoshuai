package com.roo.home.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.aries.ui.view.radius.RadiusTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.link.LinkTokenBean;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.GlobalUtils;
import com.roo.home.R;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/5 11:02
 *     desc        : 描述--//AssetSearchAdapter 添加资产币种搜索
 * </pre>
 */

public class AssetSelectLinkAdapter extends BaseQuickAdapter<LinkTokenBean, BaseViewHolderImpl> {

    @Inject
    public AssetSelectLinkAdapter() {
        super(R.layout.item_asset_select_link);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, LinkTokenBean item) {
        RadiusTextView tvCoinName = helper.getView(R.id.tvCoinName);
        tvCoinName.setText(item.getCode());
        tvCoinName.setSelected(item.isSelect());

        helper.setGone(R.id.ivIndicator, item.isSelect());

        ImageView imageView = helper.getView(R.id.ivCoin);

        if (item.isSelect()) {
            Glide.with(mContext).asBitmap().load(GlobalUtils.getLinkImage(item.getCode()))
                    .apply(RequestOptions.overrideOf(ArmsUtils.dip2px(mContext, 30)))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .into(imageView);
        } else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(GlobalUtils.getLinkImageUnselect(item.getCode()));
        }

    }


}
