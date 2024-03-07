package com.roo.core.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.R;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;

import java.util.concurrent.TimeUnit;

//底部弹窗
public class BottomDialog extends FullScreenDialogFragment {
    private OnClickedListener onClickedListener;
    public static String TOP_TX_STRING = "TOP_TX_STRING";
    public static String CONTENT_TX_STRING = "CONTENT_TX_STRING";

    public static BottomDialog newInstance(String topTxString, String contentTxString) {
        BottomDialog fragment = new BottomDialog();
        Bundle args = new Bundle();
        args.putString(TOP_TX_STRING, topTxString);
        args.putString(CONTENT_TX_STRING, contentTxString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_bottom, container, false);
        String top = getArguments().getString(TOP_TX_STRING);
        String content = getArguments().getString(CONTENT_TX_STRING);

        TextView tvTop = inflate.findViewById(R.id.tvTop);
        TextView tvContent = inflate.findViewById(R.id.tvContent);
        tvTop.setText(top);
        tvContent.setText(content);

        RxView.clicks(inflate.findViewById(R.id.tvCancel)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());

        RxView.clicks(tvContent).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onClick();
                    }
                    dismiss();
                });
        return inflate;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public BottomDialog setOnClickedListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {

        void onClick();
    }

}
