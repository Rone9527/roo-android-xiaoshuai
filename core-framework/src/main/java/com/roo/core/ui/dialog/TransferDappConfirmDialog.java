package com.roo.core.ui.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.core.domain.link.LinkTokenBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.R;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.utils.TickerManager;
import com.roo.core.utils.utils.TokenManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * 转账信息确认弹窗
 */
public class TransferDappConfirmDialog extends FullScreenDialogFragment {
    public static final String KEY_FROM_ADDRESS = "KEY_FROM_ADDRESS";
    public static final String KEY_TO_ADDRESS = "KEY_TO_ADDRESS";
    public static final String KEY_AMOUNT = "KEY_AMOUNT";
    public static final String KEY_TOKEN_BEAN = "KEY_TOKEN_BEAN";
    private static final String KEY_GAS = "KEY_GAS";
    private OnEnsureClickedListener onEnsureClickedListener;
    private LinkTokenBean.TokensBean tokensBean;
    private TextView tvGasFee;

    public static TransferDappConfirmDialog newInstance(String fromAddress,
                                                        String toAddress,
                                                        String amount,
                                                        LinkTokenBean.TokensBean tokensBean,
                                                        BigDecimal gas) {
        TransferDappConfirmDialog fragment = new TransferDappConfirmDialog();
        Bundle args = new Bundle();
        args.putString(KEY_FROM_ADDRESS, fromAddress);
        args.putString(KEY_TO_ADDRESS, toAddress);
        args.putString(KEY_AMOUNT, amount);
        args.putParcelable(KEY_TOKEN_BEAN, tokensBean);
        args.putString(KEY_GAS, gas.toPlainString());
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_transfer_dapp_confirm, container, false);

        RxView.clicks(inflate.findViewById(R.id.btn_ensure)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onEnsureClickedListener != null) {
                        onEnsureClickedListener.onClick();
                    }
                });
        RxView.clicks(inflate.findViewById(R.id.ivClose)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onCancelClickedListener != null) {
                        onCancelClickedListener.onClick();
                    }
                    dismiss();
                });
        RxView.clicks(inflate.findViewById(R.id.tvGasFee)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onEnsureClickedListener != null) {
                        onEnsureClickedListener.setGas();
                    }
                });
        TextView tvTransferCount = inflate.findViewById(R.id.tvTransferCount);
        TextView tvTransferCountUnit = inflate.findViewById(R.id.tvTransferCountUnit);
        TextView tvPayAddress = inflate.findViewById(R.id.tvPayAddress);
        TextView tvCashierAddress = inflate.findViewById(R.id.tvCashierAddress);
        tvGasFee = inflate.findViewById(R.id.tvGasFee);

        tvPayAddress.setText(getArguments().getString(KEY_FROM_ADDRESS));
        tvCashierAddress.setText(getArguments().getString(KEY_TO_ADDRESS));
        tvTransferCount.setText(getArguments().getString(KEY_AMOUNT));
        tokensBean = getArguments().getParcelable(KEY_TOKEN_BEAN);
        tvTransferCountUnit.setText(tokensBean.getSymbol());

        BigDecimal gasFeeNum = new BigDecimal(getArguments().getString(KEY_GAS));
        String gasFeeUnit = TokenManager.getInstance().getMainSymbolByChainCode(tokensBean.getChainCode());
        tvGasFee.setText(MessageFormat.format("{0} {1} ≈ {2}",
                gasFeeNum.setScale(6, RoundingMode.DOWN).toPlainString(), gasFeeUnit,
                TickerManager.getInstance().getLegalValueWithSymbol(gasFeeUnit, gasFeeNum, 4)));

        return inflate;
    }

    public void setNewGasFee(BigDecimal newGasFee) {
        String gasFeeUnit = TokenManager.getInstance().getMainSymbolByChainCode(tokensBean.getChainCode());
        tvGasFee.setText(MessageFormat.format("{0} {1} ≈ {2}",
                newGasFee.setScale(6, RoundingMode.DOWN).toPlainString(),
                gasFeeUnit,
                TickerManager.getInstance().getLegalValueWithSymbol(gasFeeUnit, newGasFee, 4)));
    }

    @Override
    protected boolean setCancelableAttr() {
        return false;
    }

    public TransferDappConfirmDialog setOnEnsureClickedListener(OnEnsureClickedListener onEnsureClickedListener) {
        this.onEnsureClickedListener = onEnsureClickedListener;
        return this;
    }

    public interface OnEnsureClickedListener {

        void onClick();

        void setGas();
    }

}
