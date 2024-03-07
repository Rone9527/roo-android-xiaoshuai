package com.roo.home.mvp.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.home.R;

import java.util.concurrent.TimeUnit;

public class AssetOperateDialog extends FullScreenDialogFragment {
    private OnClickedListener onClickedListener;

    public static AssetOperateDialog newInstance() {
        AssetOperateDialog fragment = new AssetOperateDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_asset_operate, container, false);


        RxView.clicks(inflate.findViewById(R.id.tvCancel)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());

        RxView.clicks(inflate.findViewById(R.id.tvHide)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onClickHide();
                    }
                    dismiss();
                });

        RxView.clicks(inflate.findViewById(R.id.tvTop)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onClickTop();
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

    public AssetOperateDialog setOnClickedListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {

        void onClickTop();

        void onClickHide();

        void onDismiss();
    }

}
