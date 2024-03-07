package com.roo.core.ui.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.core.domain.dapp.DappBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.R;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

public class DappSafetyTipsDialog extends FullScreenDialogFragment {

    public static final String KEY_SHOW_DAPP_TIPS = "KEY_SHOW_DAPP_TIPS";
    private OnClickedListener onClickedListener;
    private DappBean bean;

    public static DappSafetyTipsDialog newInstance(DappBean bean) {
        DappSafetyTipsDialog fragment = new DappSafetyTipsDialog();
        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY_BEAN, bean);

        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_dapp_safety_tips, container, false);
        DappBean bean = getArguments().getParcelable(Constants.KEY_BEAN);
        TextView tv_app_name = inflate.findViewById(R.id.tv_app_name);
        TextView tv_description = inflate.findViewById(R.id.tv_description);
        TextView tv_links = inflate.findViewById(R.id.tv_links);
        TextView tv_email = inflate.findViewById(R.id.tv_email);
        TextView tv_telegram = inflate.findViewById(R.id.tv_telegram);
        TextView tv_twitter = inflate.findViewById(R.id.tv_twitter);

        LinearLayout ll_link = inflate.findViewById(R.id.ll_link);
        LinearLayout ll_email = inflate.findViewById(R.id.ll_email);
        LinearLayout ll_telegram = inflate.findViewById(R.id.ll_telegram);
        LinearLayout ll_twitter = inflate.findViewById(R.id.ll_twitter);

        tv_app_name.setText(bean.getName());
        tv_description.setText(bean.getDiscription());

        if (!TextUtils.isEmpty(bean.getLinks())) {
            ll_link.setVisibility(View.VISIBLE);
            tv_links.setText(bean.getLinks());
        }
        if (!TextUtils.isEmpty(bean.getOfficialEmail())) {
            ll_email.setVisibility(View.VISIBLE);
            tv_email.setText(bean.getOfficialEmail());
        }
        if (!TextUtils.isEmpty(bean.getTelegram())) {
            ll_telegram.setVisibility(View.VISIBLE);
            tv_telegram.setText(bean.getTelegram());
        }
        if (!TextUtils.isEmpty(bean.getTwitter())) {
            ll_twitter.setVisibility(View.VISIBLE);
            tv_twitter.setText(bean.getTwitter());

        }
        RxView.clicks(tv_links).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    GlobalUtils.copyToClipboard(bean.getLinks(), getContext());
                    ToastUtils.show(getString(R.string.copy_success));
                });

        RxView.clicks(tv_email).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    GlobalUtils.copyToClipboard(bean.getOfficialEmail(), getContext());
                    ToastUtils.show(getString(R.string.copy_success));
                });
        RxView.clicks(tv_telegram).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    GlobalUtils.copyToClipboard(bean.getTelegram(), getContext());
                    ToastUtils.show(getString(R.string.copy_success));
                });
        RxView.clicks(tv_twitter).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    GlobalUtils.copyToClipboard(bean.getTwitter(), getContext());
                    ToastUtils.show(getString(R.string.copy_success));
                });

        RxView.clicks(inflate.findViewById(R.id.tvExit)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    SharedPref.putBoolean(Constants.KEY_SHOW_DAPP_TIPS, false);
                    dismiss();
                });

        RxView.clicks(inflate.findViewById(R.id.tvConfirm)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    SharedPref.putBoolean(Constants.KEY_SHOW_DAPP_TIPS, true);
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

    public DappSafetyTipsDialog setOnClickListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onSure();
    }
}
