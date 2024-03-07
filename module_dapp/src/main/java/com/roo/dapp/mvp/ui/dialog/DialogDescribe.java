package com.roo.dapp.mvp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.core.domain.manager.ChainCode;
import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.Kits;
import com.roo.dapp.R;

import java.util.concurrent.TimeUnit;

/**
 * Tron 网络描述
 */
public class DialogDescribe extends FullScreenDialogFragment {



    public static DialogDescribe newInstance() {
        DialogDescribe fragment = new DialogDescribe();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_tron_describle, container, false);

        RxView.clicks(inflate.findViewById(R.id.ivBack)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    dismiss();
                });


        return inflate;
    }


}
