package com.roo.dapp.mvp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.core.domain.manager.ChainCode;
import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.Kits;
import com.roo.dapp.R;

import java.util.concurrent.TimeUnit;

/**
 * 授权详情
 */
public class DialogAuthorizationDetail extends FullScreenDialogFragment {

    private TextView tvAuth_amount;

    public static DialogAuthorizationDetail newInstance(String amount, String toAddress, String chainCode) {
        Bundle args = new Bundle();
        args.putString(Constants.KEY_DEFAULT, amount);
        args.putString(Constants.KEY_ADDRESS, toAddress);
        args.putString(Constants.KEY_CHAIN_CODE, chainCode);
        DialogAuthorizationDetail fragment = new DialogAuthorizationDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_authorition_detail, container, false);
        String amount = getArguments().getString(Constants.KEY_DEFAULT);
        String toAddress = getArguments().getString(Constants.KEY_ADDRESS);
        String chainCode = getArguments().getString(Constants.KEY_CHAIN_CODE);

        tvAuth_amount = inflate.findViewById(R.id.auth_amount);
        TextView auth_address = inflate.findViewById(R.id.auth_address);
        View viewById = inflate.findViewById(R.id.iv_edit);
        if (chainCode.equalsIgnoreCase(ChainCode.TRON)) {
            viewById.setVisibility(View.GONE);
        }
//        ImageView iv_edit = inflate.findViewById(R.id.iv_edit);
//        ImageView ivBack = inflate.findViewById(R.id.ivBack);

        tvAuth_amount.setText(amount);
        auth_address.setText(toAddress);

        RxView.clicks(inflate.findViewById(R.id.ivBack)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onCancel(Kits.Text.getText(tvAuth_amount));
                    }
                    dismiss();
                });
        RxView.clicks(inflate.findViewById(R.id.auth_amount)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onChangeAmount(tvAuth_amount.getText().toString());
                    }
                });
        RxView.clicks(inflate.findViewById(R.id.iv_edit)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onChangeAmount(tvAuth_amount.getText().toString());
                    }
                });


        return inflate;
    }

    private OnClickedListener onClickedListener;

    public DialogAuthorizationDetail setOnClickedListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public void setAuthAmount(String newAmount) {
        if (null != tvAuth_amount) {
            tvAuth_amount.setText(newAmount);
        }
    }

    public interface OnClickedListener {
        void onCancel(String amount);

        void onChangeAmount(String amount);

    }

}
