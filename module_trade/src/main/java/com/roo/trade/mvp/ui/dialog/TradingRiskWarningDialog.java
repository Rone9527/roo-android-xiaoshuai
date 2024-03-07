package com.roo.trade.mvp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.R;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;

import java.util.concurrent.TimeUnit;

public class TradingRiskWarningDialog extends FullScreenDialogFragment {


    public static TradingRiskWarningDialog newInstance() {
        TradingRiskWarningDialog fragment = new TradingRiskWarningDialog();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_trading_risk_warning, container, false);
        RxView.clicks(inflate.findViewById(R.id.tvCancel)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());
        return inflate;
    }
}
