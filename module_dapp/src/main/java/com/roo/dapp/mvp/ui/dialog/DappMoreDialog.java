package com.roo.dapp.mvp.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.core.domain.dapp.DappBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.daoManagers.DappDaoManager;
import com.roo.dapp.R;

import java.util.concurrent.TimeUnit;

public class DappMoreDialog extends FullScreenDialogFragment {
    private OnClickedListener onClickedListener;

    public static DappMoreDialog newInstance(DappBean dappBean) {
        DappMoreDialog fragment = new DappMoreDialog();
        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY_DEFAULT, dappBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_dapp_more, container, false);
        DappBean dappBean = getArguments().getParcelable(Constants.KEY_DEFAULT);
        if (dappBean == null || dappBean.getMultis() == 0) {
            inflate.findViewById(R.id.changeChain).setVisibility(View.GONE);
        }

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
        RxView.clicks(inflate.findViewById(R.id.changeChain)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onChangeChain();
                    }
                    dismiss();
                });
        TextView tvFavorite = inflate.findViewById(R.id.tvFavorite);
        tvFavorite.setVisibility(dappBean == null ? View.GONE : View.VISIBLE);
        if (dappBean != null) {
            if (DappDaoManager.isExist(dappBean.getName(),  Constants.FAVORITE)) {
                tvFavorite.setText(R.string.cancel_favorite);
            } else {
                tvFavorite.setText(getString(R.string.favorites));
            }
        }
        RxView.clicks(tvFavorite).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onSave();
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

    public DappMoreDialog setOnClickedListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {

        void onRefresh();

        void onCopyLink();

        void onShare();

        void onSave();

        void onChangeChain();

        void onDismiss();
    }

}
