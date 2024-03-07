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
import com.roo.dapp.R;
import com.roo.dapp.mvp.beans.TronTransaction;

import org.bitcoinj.core.Base58;
import org.bouncycastle.util.encoders.Hex;

import java.util.concurrent.TimeUnit;

public class GrantTronDialog extends FullScreenDialogFragment {


    private LinkTokenBean.TokensBean tokenBean;

    public static GrantTronDialog newInstance(TronTransaction tronTransaction, DappBean dappBean, LinkTokenBean.TokensBean tokenBean) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.KEY_DEFAULT, tronTransaction);
        args.putParcelable(Constants.KEY_FR, dappBean);
        args.putParcelable(Constants.LINK__TOKEN_BEAN, tokenBean);
        GrantTronDialog fragment = new GrantTronDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_grant_tron, container, false);
        TronTransaction transaction = (TronTransaction) getArguments().getSerializable(Constants.KEY_DEFAULT);
        DappBean dappBean = getArguments().getParcelable(Constants.KEY_FR);

        tokenBean = getArguments().getParcelable(Constants.LINK__TOKEN_BEAN);

        TextView tv_from = inflate.findViewById(R.id.tv_from);
        TextView tv_to = inflate.findViewById(R.id.tv_to);
        TextView tvName = inflate.findViewById(R.id.tv_dapp_name);
        tvName.setText(dappBean.getName());


        byte[] decode = Hex.decode(transaction.raw_data.contract.get(0).parameter.value.contract_address);
        String toAddress = Base58.encode(decode);

        byte[] ownerAddress = Hex.decode(transaction.raw_data.contract.get(0).parameter.value.owner_address);
        String fromAddress = Base58.encode(ownerAddress);

        tv_from.setText(fromAddress);
        tv_to.setText(toAddress);

        ImageView ivIconDapp = inflate.findViewById(R.id.ivIconDapp);
        if (dappBean == null) {
            ivIconDapp.setVisibility(View.GONE);
        } else {
            ivIconDapp.setVisibility(View.VISIBLE);
            tvName.setText(dappBean.getName());
            Glide.with(ivIconDapp.getContext()).load(dappBean.getIcon())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .into(ivIconDapp);
        }


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
                        onClickedListener.onDetail();
                    }
                });
        return inflate;
    }


    private OnClickedListener onClickedListener;

    public GrantTronDialog setOnClickedListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }


    public interface OnClickedListener {
        void onConfirm();

        void onDetail();

    }

}
