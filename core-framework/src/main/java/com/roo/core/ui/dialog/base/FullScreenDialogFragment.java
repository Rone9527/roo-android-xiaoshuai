package com.roo.core.ui.dialog.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;

import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.R;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * description:全屏且背景透明的dialog
 */
public abstract class FullScreenDialogFragment extends DialogFragment {


    public int setAnim() {
        return R.style.DialogBottom;
    }

    protected boolean setCancelableAttr() {
        return true;
    }

    protected CancellickedListener onCancelClickedListener;

    public FullScreenDialogFragment setOnCancelClickListener(CancellickedListener OnCancellickedListener) {
        this.onCancelClickedListener = OnCancellickedListener;
        return this;
    }

    public interface CancellickedListener {

        void onClick();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        super.onStart();
        ImmersionBar.with(this).fitsSystemWindows(false)
                .statusBarColor(android.R.color.transparent).init();

        //点击外部消失
        getDialog().setCanceledOnTouchOutside(setCancelableAttr());
        getDialog().setCancelable(setCancelableAttr());
        setCancelable(setCancelableAttr());
        View view = Objects.requireNonNull(getView());
        View touchOutside = view.findViewById(R.id.touchOutside);
        if (touchOutside != null && setCancelableAttr()) {
            RxView.clicks(touchOutside).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
                        if (onCancelClickedListener != null) {
                            onCancelClickedListener.onClick();
                        }
                        dismiss();
                    }
            );
        }
        //设置Dialog的动画
        Window window = Objects.requireNonNull(getDialog().getWindow());
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.windowAnimations = setAnim();
        window.setAttributes(lp);
    }

}
