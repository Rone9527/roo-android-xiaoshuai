package com.roo.core.ui.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.R;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;

import java.util.concurrent.TimeUnit;

/**
 * 转账信息确认弹窗
 */
public class TronFreezeConfirmDialog extends FullScreenDialogFragment {
    public static final String KEY_RESOURCE_TYPE = "KEY_RESOURCE_TYPE";
    public static final String KEY_TO_ADDRESS = "KEY_TO_ADDRESS";
    public static final String KEY_AMOUNT = "KEY_AMOUNT";
    public static final String KEY_TOKEN_BEAN = "KEY_TOKEN_BEAN";
    private static final String KEY_LENGTH = "KEY_LENGTH";
    private OnEnsureClickedListener onEnsureClickedListener;

    public static TronFreezeConfirmDialog newInstance(String resourceType, String recipientAccount, String amount, int length) {
        TronFreezeConfirmDialog fragment = new TronFreezeConfirmDialog();
        Bundle args = new Bundle();
        args.putString(KEY_RESOURCE_TYPE, resourceType);
        args.putString(KEY_TO_ADDRESS, recipientAccount);
        args.putString(KEY_AMOUNT, amount);
        args.putInt(KEY_LENGTH, length);
//        args.putParcelable(KEY_TOKEN_BEAN, tokensBean);
//        args.putString(KEY_GAS, gas.toPlainString());
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_treeze_confirm, container, false);

        RxView.clicks(inflate.findViewById(R.id.btn_ensure)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onEnsureClickedListener != null) {
                        onEnsureClickedListener.onClick();
                    }
                    dismiss();
                });
        RxView.clicks(inflate.findViewById(R.id.ivClose)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    dismiss();
                });
        TextView tvResourceType = inflate.findViewById(R.id.tvResourceType);
        TextView tvAddress = inflate.findViewById(R.id.tv_address);
        TextView tvAmount = inflate.findViewById(R.id.amount);
        TextView tv_total_amount = inflate.findViewById(R.id.tv_total_amount);
        TextView tv_length = inflate.findViewById(R.id.tv_length);


        tvResourceType.setText(getArguments().getString(KEY_RESOURCE_TYPE));
        tvAddress.setText(getArguments().getString(KEY_TO_ADDRESS));
        tvAmount.setText(getArguments().getString(KEY_AMOUNT) + "TRX");
        tv_total_amount.setText(getArguments().getString(KEY_AMOUNT) + "TRX");
        tv_length.setText(getArguments().getInt(KEY_LENGTH) + "带宽");

//        tvTransferCount.setText(getArguments().getString(KEY_AMOUNT));
//        LinkTokenBean.TokensBean tokensBean = getArguments().getParcelable(KEY_TOKEN_BEAN);
//        tvTransferCountUnit.setText(tokensBean.getSymbol());
//
//        BigDecimal gasFeeNum = new BigDecimal(getArguments().getString(KEY_GAS));
//        String gasFeeUnit = TokenManager.getInstance().getMainSymbolByChainCode(tokensBean.getChainCode());
//        tvGasFee.setText(MessageFormat.format("{0} {1} ≈ {2}",
//                gasFeeNum.setScale(6, RoundingMode.DOWN).toPlainString(),
//                gasFeeUnit,
//                TickerManager.getInstance().getLegalValueWithSymbol(gasFeeUnit, gasFeeNum, 4)));

        return inflate;
    }

    public TronFreezeConfirmDialog setOnEnsureClickedListener(OnEnsureClickedListener onEnsureClickedListener) {
        this.onEnsureClickedListener = onEnsureClickedListener;
        return this;
    }

    public interface OnEnsureClickedListener {
        void onClick();
    }

}
