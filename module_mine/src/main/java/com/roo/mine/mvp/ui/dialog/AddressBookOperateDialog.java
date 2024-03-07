package com.roo.mine.mvp.ui.dialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.mine.R;

import java.util.concurrent.TimeUnit;

public class AddressBookOperateDialog extends FullScreenDialogFragment {
    private OnClickedListener onClickedListener;

    public static AddressBookOperateDialog newInstance() {
        AddressBookOperateDialog fragment = new AddressBookOperateDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_address_book_operate, container, false);

        RxView.clicks(inflate.findViewById(R.id.tvCancel)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());

        RxView.clicks(inflate.findViewById(R.id.tvCopy)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onClickCopy();
                    }
                    dismiss();
                });
        RxView.clicks(inflate.findViewById(R.id.tvEdit)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onClickEdit();
                    }
                    dismiss();
                });

        RxView.clicks(inflate.findViewById(R.id.tvRemove)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onClickRemove();
                    }
                    dismiss();
                });

        return inflate;
    }

    public AddressBookOperateDialog setOnClickedListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClickRemove();

        void onClickCopy();

        void onClickEdit();
    }

}
