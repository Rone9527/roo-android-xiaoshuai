package com.roo.webview.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.webview.R;

import java.util.concurrent.TimeUnit;

public class DialogMore extends FullScreenDialogFragment {
    private OnClickedListener onClickedListener;

    public static DialogMore newInstance() {
        DialogMore fragment = new DialogMore();
        Bundle args = new Bundle();
//        args.putParcelable(Constants.KEY_DEFAULT, dappBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_more, container, false);
        RxView.clicks(inflate.findViewById(R.id.tvCancel)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());

        RxView.clicks(inflate.findViewById(R.id.tvRefresh)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onRefresh();
                    }
                    dismiss();
                });

        RxView.clicks(inflate.findViewById(R.id.tvCopyAddress)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onCopyLink();
                    }
                    dismiss();
                });
        RxView.clicks(inflate.findViewById(R.id.tvShare)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onShare();
                    }
                    dismiss();
                });
        return inflate;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (onClickedListener != null) {
            onClickedListener.onDismiss();
        }
        super.onDismiss(dialog);
    }

    public DialogMore setOnClickedListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {

        void onRefresh();

        void onCopyLink();

        void onShare();

        void onDismiss();
    }

}
