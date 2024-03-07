package com.roo.core.ui.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.R;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;

import java.util.concurrent.TimeUnit;

public class AddLinkAssetDialog extends FullScreenDialogFragment {

    private OnClickedListener onClickedListener;

    public static AddLinkAssetDialog newInstance() {
        AddLinkAssetDialog fragment = new AddLinkAssetDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static AddLinkAssetDialog newInstance(String coinName) {
        AddLinkAssetDialog fragment = new AddLinkAssetDialog();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_DEFAULT, coinName);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_add_asset_hint, container, false);
        String coinName = getArguments().getString(Constants.KEY_DEFAULT);
        TextView tvCancel = inflate.findViewById(R.id.tvCancel);
        TextView tvConfirm = inflate.findViewById(R.id.tvConfirm);
        TextView tvTitle = inflate.findViewById(R.id.tvTitle);
        TextView tvMessage = inflate.findViewById(R.id.tvMessage);
        TextView tvMessageSecondary = inflate.findViewById(R.id.tvMessageSecondary);

        if (TextUtils.isEmpty(coinName)) {
            tvTitle.setText("提示");
            tvMessage.setText("没有找到合约地址");
            tvCancel.setText("好的");
            tvCancel.getPaint().setFakeBoldText(false);

            tvMessageSecondary.setVisibility(View.GONE);
            tvConfirm.setVisibility(View.GONE);
            inflate.findViewById(R.id.divider).setVisibility(View.GONE);
        } else {
            tvMessage.setText(coinName);
            tvMessageSecondary.setText(coinName);

        }

        RxView.clicks(tvConfirm).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onClick();
                        dismiss();
                    }
                });

        RxView.clicks(tvCancel).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());

        return inflate;
    }

    public AddLinkAssetDialog setOnClickListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClick();
    }
}
