package com.roo.home.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.link.LinkTokenBean;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.utils.BalanceManager;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TickerManager;
import com.roo.home.R;
import com.roo.home.mvp.ui.fragment.WalletFragment;
import com.roo.view.swipe.EasySwipeMenuLayout;
import com.roo.view.swipe.SwipeState;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/5/31 21:51
 *     desc        : 描述--//WalletAdapter
 * </pre>
 */

public class WalletAdapter extends BaseQuickAdapter<LinkTokenBean.TokensBean, BaseViewHolderImpl> {


    private boolean assetVisible = true;

    @Inject
    public WalletAdapter() {
        super(R.layout.item_wallet);
    }



    public void setAssetVisible(boolean assetVisible) {
        this.assetVisible = assetVisible;
        notifyDataSetChanged();
    }



    @Override
    protected void convert(BaseViewHolderImpl helper, LinkTokenBean.TokensBean item) {


        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(mContext);
        BigDecimal symbolCount = BalanceManager.getInstance().get(userWallet, item);

        String legalValue = TickerManager.getInstance().getLegalValueWithSymbolNOKB(
                item.getSymbol(), symbolCount, 4
        );

        helper.setText(R.id.tvCoinName, item.getNameEn())
                .setText(R.id.tvCoinAsset, assetVisible ?
                        symbolCount.setScale(6, RoundingMode.DOWN).stripTrailingZeros().toPlainString() : WalletFragment.DEFAULT_SHORT)//资产数量

                .setText(R.id.tvCoinAssetCnyValue, assetVisible ? legalValue : WalletFragment.DEFAULT_SHORT)//相当于法币价值
                .setText(R.id.tvCoinPrice, TickerManager.getInstance()//单价
                        .getLegalPriceWithSymbol(item.getSymbol(), TickerManager.SCALE_NOT))
        ;

        helper.setCircleImageUrl(R.id.ivCoin,
                GlobalUtils.getIconCoin(item.getChainCode(), item.getContractId()));

    }
}
