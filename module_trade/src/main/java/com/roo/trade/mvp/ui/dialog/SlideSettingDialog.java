package com.roo.trade.mvp.ui.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.Kits;
import com.roo.core.utils.RxUtils;
import com.roo.trade.R;

import java.util.concurrent.TimeUnit;

public class SlideSettingDialog extends FullScreenDialogFragment {

    private OnClickedListener onClickedListener;

    public static SlideSettingDialog newInstance(String dex) {
        SlideSettingDialog fragment = new SlideSettingDialog();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_DEFAULT, dex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_slide_setting, container, false);
        RxView.clicks(inflate.findViewById(R.id.tvCancel)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());
        TextView tvConfirm = inflate.findViewById(R.id.tvConfirm);
        ImageView ivCancel = inflate.findViewById(R.id.ivCancel);

        EditText editText = inflate.findViewById(R.id.editText);
        RxTextView.textChanges(editText).skipInitialValue()
                .debounce(200, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applySchedulers())
                .map(CharSequence::toString)
                .subscribe(t -> {
                    ivCancel.setVisibility(TextUtils.isEmpty(t) ? View.GONE : View.VISIBLE);
                    tvConfirm.setEnabled(!TextUtils.isEmpty(t));
                });

        RxView.clicks(ivCancel).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> editText.setText(""));

        RxView.clicks(tvConfirm).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    try {
                        if (onClickedListener != null) {
                            onClickedListener.onClick(Kits.Text.getTextFloat(editText));
                            dismiss();
                        }
                    } catch (Exception ignore) {

                    }

                });
        return inflate;
    }

    public SlideSettingDialog setOnClickListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClick(float slide);
    }
}
