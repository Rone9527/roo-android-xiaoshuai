package com.roo.home.mvp.ui.dialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aries.ui.view.radius.RadiusEditText;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.link.LinkTokenBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.Kits;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.BalanceManager;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TickerManager;
import com.roo.home.R;
import com.roo.view.swipe.EasySwipeMenuLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ChooseAssetDialog extends FullScreenDialogFragment {
    private OnClickedListener onClickedListener;
    private UserWallet userWallet;
    private ChooseAssetAdapter mAdapter;

    public static ChooseAssetDialog newInstance() {
        ChooseAssetDialog fragment = new ChooseAssetDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_choose_asset, container, false);
        userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(getActivity());

        View content = inflate.findViewById(R.id.content);
        ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
        layoutParams.height = ArmsUtils.getScreenHeidth(requireActivity()) * 7 / 8;
        content.setLayoutParams(layoutParams);

        RadiusEditText edSearch = inflate.findViewById(R.id.edSearch);
        mAdapter = new ChooseAssetAdapter(userWallet);
        ViewHelper.initRecyclerView(inflate, mAdapter);
        RxView.clicks(inflate.findViewById(R.id.ivClose)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());
        if (null != userWallet) {
            mAdapter.setNewData(userWallet.getTokenList());
            RxTextView.textChanges(edSearch).skipInitialValue()
                    .debounce(200, TimeUnit.MILLISECONDS)
                    .compose(RxUtils.applySchedulers())
                    .map(CharSequence::toString)
                    .subscribe(t -> filter(t.trim().toUpperCase()));

        }
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (onClickedListener != null) {
                onClickedListener.onClick(mAdapter.getItem(position));
            }
            dismiss();
        });


        return inflate;
    }

    private void filter(String inputStr) {
        if (Kits.Empty.check(inputStr)) {
            mAdapter.setNewData(userWallet.getTokenList());
        } else {
            ArrayList<LinkTokenBean.TokensBean> dataSet = new ArrayList<>();
            ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
            for (LinkTokenBean.TokensBean tokensBean : tokenList) {
                if (tokensBean.getSymbol().toUpperCase().contains(inputStr)) {
                    dataSet.add(tokensBean);
                }
            }
            mAdapter.setNewData(dataSet);
        }
    }

    public ChooseAssetDialog setOnClickedListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClick(LinkTokenBean.TokensBean linkBean);
    }

    private static class ChooseAssetAdapter extends BaseQuickAdapter<LinkTokenBean.TokensBean, BaseViewHolderImpl> {
        private final UserWallet userWallet;

        public ChooseAssetAdapter(UserWallet userWallet) {
            super(R.layout.item_wallet_select);
            this.userWallet = userWallet;
        }

        @Override
        protected void convert(BaseViewHolderImpl helper, LinkTokenBean.TokensBean item) {
            EasySwipeMenuLayout swipeMenuLayout = helper.getView(R.id.swipeMenuLayout);
            swipeMenuLayout.setCanLeftSwipe(false);
            swipeMenuLayout.setCanRightSwipe(false);
            helper.setText(R.id.tvCoinName, item.getNameEn())
                    .setText(R.id.tvCoinNameCn, item.getName())
                    .addOnClickListener(R.id.content);

            BigDecimal symbolCount = BalanceManager.getInstance().get(userWallet, item);
            String legalValue = TickerManager.getInstance().getLegalValueWithSymbolNOKB(
                    item.getSymbol(), symbolCount, 4
            );
            BigDecimal count = TickerManager.getInstance()
                    .getDecimalSymbolCount(item.getSymbol(), symbolCount);

            helper.setText(R.id.tvCoinAsset, count.setScale(6, RoundingMode.DOWN).stripTrailingZeros().toPlainString())
                    .setText(R.id.tvCoinAssetCnyValue, legalValue);

            helper.setCircleImageUrl(R.id.ivCoin,
                    GlobalUtils.getIconCoin(item.getChainCode(), item.getContractId()));

        }
    }
}
