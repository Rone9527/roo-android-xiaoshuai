package com.roo.dapp.mvp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.aries.ui.view.radius.RadiusTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.core.domain.dapp.DappBean;
import com.core.domain.link.LinkTokenBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.utils.TokenManager;
import com.roo.dapp.R;
import com.roo.dapp.mvp.beans.Web3Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public class GrantDialog extends FullScreenDialogFragment {

    private TextView tvGasFee;
    private TextView tvGasFeeUnit;
    private LinkTokenBean.TokensBean tokenBean;

    public static GrantDialog newInstance(Web3Transaction web3Transaction, DappBean dappBean, LinkTokenBean.TokensBean tokenBean, String fee) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY_DEFAULT, web3Transaction);
        args.putParcelable(Constants.KEY_FR, dappBean);
        args.putString(Constants.KEY_MSG, fee);
        args.putParcelable(Constants.LINK__TOKEN_BEAN, tokenBean);
        GrantDialog fragment = new GrantDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_grant, container, false);
        Web3Transaction transaction = getArguments().getParcelable(Constants.KEY_DEFAULT);
        DappBean dappBean = getArguments().getParcelable(Constants.KEY_FR);
        String fee = getArguments().getString(Constants.KEY_MSG);
        tokenBean = getArguments().getParcelable(Constants.LINK__TOKEN_BEAN);
        String unit = TokenManager.getInstance().getMainSymbolByChainCode(tokenBean.getChainCode());
        TextView tvAnnounce = inflate.findViewById(R.id.tvAnnounce);
        ImageView ivIconDapp = inflate.findViewById(R.id.ivIconDapp);
        if (dappBean == null) {
            tvAnnounce.setText(R.string.request_transfer_authorization);
            ivIconDapp.setVisibility(View.GONE);
        } else {
            ivIconDapp.setVisibility(View.VISIBLE);
            tvAnnounce.setText(MessageFormat.format("{0}" + getString(R.string.request_transfer_authorization), dappBean.getName()));
            Glide.with(ivIconDapp.getContext()).load(dappBean.getIcon())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .into(ivIconDapp);
        }
        tvGasFee = inflate.findViewById(R.id.tvGasFee);
        tvGasFeeUnit = inflate.findViewById(R.id.tvGasFeeUnit);
        tvGasFee.setText(fee);
        tvGasFeeUnit.setText(unit);

        RxView.clicks(inflate.findViewById(R.id.ivClose)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onCancelClickedListener != null) {
                        onCancelClickedListener.onClick();
                    }
                    dismiss();
                });

        RadiusTextView tvConfirm = inflate.findViewById(R.id.tvConfirm);
        RxView.clicks(tvConfirm).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onConfirm();
                    }
                    dismiss();
                });

        RxView.clicks(inflate.findViewById(R.id.layoutGrantDetail)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onDetail(transaction);
                    }
                });

        RxView.clicks(inflate.findViewById(R.id.layoutGasFee)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onGasFee();
                    }
                });
        return inflate;
    }

    public void setGasFee(BigInteger gasFee, String unit) {
        this.tvGasFee.setText(String.valueOf(gasFee.intValue()));
        this.tvGasFeeUnit.setText(unit);
    }

    private OnClickedListener onClickedListener;

    public GrantDialog setOnClickedListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public void setNewGasFee(BigDecimal newGasFee) {
        String gasFeeUnit = TokenManager.getInstance().getMainSymbolByChainCode(tokenBean.getChainCode());
        tvGasFee.setText(MessageFormat.format("{0} {1}", newGasFee.toPlainString(), gasFeeUnit));
    }

    public interface OnClickedListener {
        void onConfirm();

        void onDetail(Web3Transaction transaction);

        void onGasFee();
    }

}
