package com.roo.dapp.mvp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.dapp.R;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public class AddLinkHintDialog extends FullScreenDialogFragment {

    private OnClickedListener onClickedListener;

    public static AddLinkHintDialog newInstance(String chainCode) {
        AddLinkHintDialog fragment = new AddLinkHintDialog();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_DEFAULT, chainCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_add_link_hint, container, false);
        RxView.clicks(inflate.findViewById(R.id.tvCancel)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());
        TextView tvConfirm = inflate.findViewById(R.id.tvConfirm);
        TextView tvMessage = inflate.findViewById(R.id.tvMessage);

        String chainCode = getArguments().getString(Constants.KEY_DEFAULT);
        tvMessage.setText(MessageFormat.format(getString(R.string.tip_no_assets), chainCode));
        RxView.clicks(tvConfirm).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onClick();
                        dismiss();
                    }
                });
        return inflate;
    }

    public AddLinkHintDialog setOnClickListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClick();
    }
}
