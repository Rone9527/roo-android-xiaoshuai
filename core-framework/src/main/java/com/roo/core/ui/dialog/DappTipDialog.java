package com.roo.core.ui.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.view.RxView;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.R;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public class DappTipDialog extends FullScreenDialogFragment {

    public static final String KEY_SHOW_DAPP_TIPS = "KEY_SHOW_DAPP_TIPS";
    private OnClickedListener onClickedListener;

    public static DappTipDialog newInstance(String dappName, String dappIcon) {
        DappTipDialog fragment = new DappTipDialog();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_TITLE, dappName);
        args.putString(Constants.KEY_DEFAULT, dappIcon);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_dapp_tip, container, false);
        CheckBox checkbox = inflate.findViewById(R.id.checkbox);
        TextView tvAnnounce = inflate.findViewById(R.id.tvAnnounce);
        ImageView ivIconDapp = inflate.findViewById(R.id.ivIconDapp);

        String dappName = getArguments().getString(Constants.KEY_TITLE);
        if (!TextUtils.isEmpty(dappName)) {
            tvAnnounce.setText(MessageFormat.format(getString(R.string.dapp_visit_tip), dappName));
        }
        String dappIcon = getArguments().getString(Constants.KEY_DEFAULT);
        if (!TextUtils.isEmpty(dappIcon)) {
            Glide.with(ivIconDapp.getContext()).load(dappIcon)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .into(ivIconDapp);
        }

        RxView.clicks(inflate.findViewById(R.id.tvExit)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    SharedPref.putBoolean(Constants.KEY_SHOW_DAPP_TIPS, false);
                    dismiss();
                });

        RxView.clicks(inflate.findViewById(R.id.tvConfirm)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (checkbox.isChecked()) {
                        SharedPref.putBoolean(Constants.KEY_SHOW_DAPP_TIPS, true);
                    }
                    if (onClickedListener != null) {
                        onClickedListener.onSure();
                    }
                    dismiss();
                });
        return inflate;
    }

    @Override
    protected boolean setCancelableAttr() {
        return true;
    }

    public DappTipDialog setOnClickListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onSure();
    }
}
