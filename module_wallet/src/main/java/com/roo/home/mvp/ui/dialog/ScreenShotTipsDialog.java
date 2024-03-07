package com.roo.home.mvp.ui.dialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.home.R;

import java.util.concurrent.TimeUnit;

public class ScreenShotTipsDialog extends FullScreenDialogFragment {

    public static ScreenShotTipsDialog newInstance() {
        ScreenShotTipsDialog fragment = new ScreenShotTipsDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_screen_shot, container, false);
        RxView.clicks(inflate.findViewById(R.id.btn_ensure)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());
        return inflate;
    }

    @Override
    protected boolean setCancelableAttr() {
        return true;
    }

}
