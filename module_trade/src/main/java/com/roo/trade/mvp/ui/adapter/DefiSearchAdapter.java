package com.roo.trade.mvp.ui.adapter;

import com.aries.ui.view.radius.RadiusTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.trade.DeFiDataBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.daoManagers.DeFiDaoManager;
import com.roo.core.utils.ImageOptions;
import com.roo.trade.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/13 10:26
 *     desc        : 描述--//BackupWordsAdapter 备份助记词
 * </pre>
 */

public class DefiSearchAdapter extends BaseQuickAdapter<DeFiDataBean, BaseViewHolderImpl> {

    @Inject
    public DefiSearchAdapter() {
        super(R.layout.item_defi_search_result);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, DeFiDataBean item) {
        String proportion = new BigDecimal(item.getPrice()).setScale(9, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
        if (proportion.length() > 9) {
            proportion = proportion.substring(0, 9) + "…";
        }

        String symbolTokenO = item.getPairName().split("-")[1];
        String pairName = item.getPairName().length() <= 10 ? item.getPairName() : item.getPairName().substring(0, 10) + "…";
        helper.setText(R.id.tvCouple, pairName)
                .setText(R.id.tvProvider, item.getAscription())
                .setText(R.id.tvTVL, symbolTokenO + ":" + item.getContractId())
                .addOnClickListener(R.id.ivCopy, R.id.layoutRoot, R.id.tvAdd)
                .setText(R.id.tvProportion, "1:" + proportion);

        RadiusTextView tvAdd = helper.getView(R.id.tvAdd);
        tvAdd.setSelected(DeFiDaoManager.isExist(item.getIdentity()));
        tvAdd.setText(mContext.getString(DeFiDaoManager.isExist(item.getIdentity()) ?
                R.string.t_added : R.string.t_add));

        helper.setCircleImageUrl(R.id.ivLogo, item.getLogo(), ImageOptions.defaultOptionsCircle());

    }
}
