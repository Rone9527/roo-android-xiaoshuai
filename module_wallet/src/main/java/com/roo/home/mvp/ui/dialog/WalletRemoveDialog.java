package com.roo.home.mvp.ui.dialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.home.R;

import java.util.concurrent.TimeUnit;

public class WalletRemoveDialog extends FullScreenDialogFragment {

    private OnClickedListener onClickedListener;

    public static WalletRemoveDialog newInstance() {
        WalletRemoveDialog fragment = new WalletRemoveDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_wallet_remove, container, false);

        RxView.clicks(inflate.findViewById(R.id.tvCancel)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());

        RxView.clicks(inflate.findViewById(R.id.tvConfirm)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onClick();
                    }
                    dismiss();
                });

        return inflate;
    }

    public WalletRemoveDialog setOnClickedListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClick();
    }

}
