package com.roo.home.mvp.ui.dialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.home.R;
import com.roo.home.mvp.ui.activity.WalletListActivity;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ChooseWalletDialog extends FullScreenDialogFragment {

    private OnClickedListener onClickedListener;

    public static ChooseWalletDialog newInstance() {
        ChooseWalletDialog fragment = new ChooseWalletDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_choose_wallet, container, false);
        ChooseWalletAdapter mAdapter = new ChooseWalletAdapter();

        View content = inflate.findViewById(R.id.content);
        ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
        layoutParams.height = ArmsUtils.getScreenHeidth(requireActivity()) * 7 / 8;
        content.setLayoutParams(layoutParams);

        mAdapter.setNewData(EthereumWalletUtils.getInstance().getWallet(requireActivity()));

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (ClickUtil.clickInFronzen()) {
                return;
            }
            for (UserWallet userWallet : mAdapter.getData()) {
                userWallet.setSelected(false);
            }
            UserWallet item = Objects.requireNonNull(mAdapter.getItem(position));
            item.setSelected(true);
            mAdapter.notifyDataSetChanged();

            dismiss();

            if (onClickedListener != null) {
                onClickedListener.onClick(item);
            }
        });
        ViewHelper.initRecyclerView(inflate, mAdapter);
        RxView.clicks(inflate.findViewById(R.id.ivClose)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());
        RxView.clicks(inflate.findViewById(R.id.tvManager)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    WalletListActivity.start(getActivity());
                    dismiss();
                });
        return inflate;
    }

    public ChooseWalletDialog setOnClickedListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClick(UserWallet userWallet);
    }

    private static class ChooseWalletAdapter extends BaseQuickAdapter<UserWallet, BaseViewHolderImpl> {
        public ChooseWalletAdapter() {
            super(R.layout.item_choose_wallet);
        }

        @Override
        protected void convert(BaseViewHolderImpl helper, UserWallet item) {
            helper.getView(R.id.layoutRoot).setSelected(item.isSelected());
            helper.setText(R.id.tvLink, item.getRemark());
            helper.setGone(R.id.ivCheck, item.isSelected());
            helper.setImageResource(R.id.ivLink, item.isSelected() ?
                    R.drawable.ic_home_choose_wallet_sel : R.drawable.ic_home_choose_wallet);
        }
    }
}
