package com.roo.core.ui.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.aries.ui.view.radius.RadiusEditText;
import com.aries.ui.view.radius.RadiusRelativeLayout;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.mine.GasBean;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.roo.core.ComponentApplication;
import com.roo.core.R;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RetrofitFactory;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.utils.TokenManager;

import org.jetbrains.annotations.NotNull;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * 以太链的转账
 */
public class ChooseGasDialog extends FullScreenDialogFragment {
    public static final String FR_RECORD = "FR_RECORD";
    public static final String FR_PRICE = "FR_PRICE";
    public static final String FR_LIMIT = "FR_LIMIT";
    public static final String GAS_BEAN = "GAS_BEAN";
    private RadiusRelativeLayout layoutSpeedNormal;
    private RadiusRelativeLayout layoutSpeedFast;
    private RadiusRelativeLayout layoutSpeedBest;
    private LinearLayout layoutGasSetting;
    private ImageView ivSpeedBest;
    private ImageView ivSpeedFast;
    private ImageView ivSpeedNormal;
    private RadiusEditText edGasPrice, edGasLimit;
    private TextView tvSpeedBestValue, tvSpeedFastValue, tvSpeedNormalValue;
    private LinkTokenBean.TokensBean tokensBean;
    private TextView tvGasFee;
    private String fee;
    private GasBean gasBean;

    public static ChooseGasDialog newInstance(LinkTokenBean.TokensBean tokensBean, GasBean gasBean, String gasLimit, String address, String fee) {
        Bundle args = new Bundle();
        ChooseGasDialog fragment = new ChooseGasDialog();
        args.putParcelable(Constants.KEY_DEFAULT, tokensBean);
        args.putParcelable(GAS_BEAN, gasBean);
        args.putString(FR_LIMIT, gasLimit);
        args.putString(Constants.KEY_ADDRESS, address);
        args.putString(Constants.KEY_FEE, fee);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_choose_gas, container, false);
        tokensBean = getArguments().getParcelable(Constants.KEY_DEFAULT);
        gasBean = getArguments().getParcelable(GAS_BEAN);
        LogManage.e("gasBean--->"+gasBean.toString());
        String gasLimit = getArguments().getString(FR_LIMIT);
        fee = getArguments().getString(Constants.KEY_FEE);

        layoutGasSetting = inflate.findViewById(R.id.layoutGasSetting);

        TextView tvConfirm = inflate.findViewById(R.id.tvConfirm);
        edGasPrice = inflate.findViewById(R.id.edGasPrice);
        tvGasFee = inflate.findViewById(R.id.tvGasFee);

        layoutSpeedBest = inflate.findViewById(R.id.layoutSpeedBest);
        RxView.clicks(layoutSpeedBest).throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    resetSelect(layoutSpeedBest, ivSpeedBest);
                });

        RxView.clicks(inflate.findViewById(R.id.ivBack)).throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    dismiss();
                });

        tvSpeedBestValue = inflate.findViewById(R.id.tvSpeedBestValue);
        ivSpeedBest = inflate.findViewById(R.id.ivSpeedBest);

        layoutSpeedFast = inflate.findViewById(R.id.layoutSpeedFast);
        RxView.clicks(layoutSpeedFast).throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    resetSelect(layoutSpeedFast, ivSpeedFast);
                });
        tvSpeedFastValue = inflate.findViewById(R.id.tvSpeedFastValue);
        ivSpeedFast = inflate.findViewById(R.id.ivSpeedFast);

        layoutSpeedNormal = inflate.findViewById(R.id.layoutSpeedNormal);
        RxView.clicks(layoutSpeedNormal).throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    resetSelect(layoutSpeedNormal, ivSpeedNormal);
                });

        tvSpeedNormalValue = inflate.findViewById(R.id.tvSpeedNormalValue);
        ivSpeedNormal = inflate.findViewById(R.id.ivSpeedNormal);
        //初始化默认选中“快的”
        resetSelect(layoutSpeedFast, ivSpeedFast);

        ImageView ivGasFeeDetail = inflate.findViewById(R.id.ivGasFeeDetail);
        TextView tvGasFeeSetting = inflate.findViewById(R.id.tvGasFeeSetting);
        RxView.clicks(tvGasFeeSetting).throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    if (layoutGasSetting.getVisibility() == View.GONE) {
                        ivGasFeeDetail.setRotation(0);
                        edGasLimit.setText(gasLimit);
                        if (layoutSpeedBest.isSelected()) {
                            edGasPrice.setText(gasBean.getFastGasPrice().toPlainString());
                        } else if (layoutSpeedFast.isSelected()) {
                            edGasPrice.setText(gasBean.getProposeGasPrice().toPlainString());
                        } else if (layoutSpeedNormal.isSelected()) {
                            edGasPrice.setText(gasBean.getSafeGasPrice().toPlainString());
                        }
                        resetSelect(null, null);
                    } else {
                        ivGasFeeDetail.setRotation(-90);
                        edGasPrice.setText("");
                        edGasLimit.setText("");
                        resetSelect(layoutSpeedFast, ivSpeedFast);
                    }
                });
        edGasLimit = inflate.findViewById(R.id.edGasLimit);
        RxView.clicks(tvConfirm).throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onClick(getGasPrice(), getGasLimit());
                    }
                    dismiss();
                });
        tvGasFee.setText(MessageFormat.format("{0} {1}", fee,
                TokenManager.getInstance().getMainSymbolByChainCode(tokensBean.getChainCode())));

        InitialValueObservable<CharSequence> observableGasPrice = RxTextView.textChanges(edGasPrice);
        InitialValueObservable<CharSequence> observableGasLimit = RxTextView.textChanges(edGasLimit);


        Observable.combineLatest(observableGasPrice, observableGasLimit, (s1, s2) -> {
            if (layoutGasSetting.getVisibility() == View.GONE) {
                return true;
            } else {
                return !Kits.Empty.check(s1) && !Kits.Empty.check(s2);
            }
        }).subscribe(new ErrorHandleSubscriber<Boolean>(ComponentApplication.getRxErrorHandler()) {
            @Override
            public void onNext(@NotNull Boolean verify) {
                tvConfirm.setEnabled(verify);
            }
        });
//        getGasDataSet(tokensBean.getChainCode());

        return inflate;
    }

    private void resetSelect(RadiusRelativeLayout selectLayout, ImageView selectImage) {
        layoutSpeedNormal.setSelected(false);
        layoutSpeedFast.setSelected(false);
        layoutSpeedBest.setSelected(false);

        ivSpeedNormal.setVisibility(View.GONE);
        ivSpeedFast.setVisibility(View.GONE);
        ivSpeedBest.setVisibility(View.GONE);

        if (selectLayout != null && selectImage != null) {
            LogManage.e("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            layoutGasSetting.setVisibility(View.GONE);
            //用户选择
            selectLayout.setSelected(true);
            selectImage.setVisibility(View.VISIBLE);
            //layoutSpeedNormal.setEnabled(true);
            //layoutSpeedFast.setEnabled(true);
            //layoutSpeedBest.setEnabled(true);

            if (gasBean != null) {
                BigDecimal gasFeeNum = calculateGasFee();
                LogManage.e("gasFeeNum----->默认" + gasFeeNum);
                String gasFeeUnit = TokenManager.getInstance().getMainSymbolByChainCode(tokensBean.getChainCode());
                tvGasFee.setText(MessageFormat.format("{0} {1}", gasFeeNum.toPlainString(), gasFeeUnit));
            } else {
                LogManage.e("gasBean===null");
            }

        } else {
            //用户手动输入Gas费用
            layoutGasSetting.setVisibility(View.VISIBLE);
            //layoutSpeedNormal.setEnabled(false);
            //layoutSpeedFast.setEnabled(false);
            //layoutSpeedBest.setEnabled(false);
        }
    }

//    public void getGasDataSet(String chainCode) {
//        RxUtils.transform(RetrofitFactory.getRetrofit(ApiService.class).getGas(chainCode))
//                .retryWhen(new RetryWithDelay())
//                .compose(RxUtils.applySchedulers())
//                .subscribe(new ErrorHandleSubscriber<BaseResponse<GasBean>>(ComponentApplication.getRxErrorHandler()) {
//                    @Override
//                    public void onNext(@NotNull BaseResponse<GasBean> t) {
//                        GasBean gasBean = t.getData();
//                        ChooseGasDialog.this.gasBean = gasBean;
//                        tvSpeedBestValue.setText(MessageFormat.format("{0}Gwei",
//                                gasBean.getFastGasPrice().toPlainString()));
//
//                        tvSpeedFastValue.setText(MessageFormat.format("{0}Gwei",
//                                gasBean.getProposeGasPrice().toPlainString()));
//
//                        tvSpeedNormalValue.setText(MessageFormat.format("{0}Gwei",
//                                gasBean.getSafeGasPrice().toPlainString()));
//
//                        BigDecimal gasFeeNum = calculateGasFee();
//                        String gasFeeUnit = TokenManager.getInstance().getMainSymbolByChainCode(tokensBean.getChainCode());
////                        tvGasFee.setText(MessageFormat.format("{0} {1}", gasFeeNum.toPlainString(), gasFeeUnit));
//                    }
//                });
//    }

    /**
     * 计算gasFee
     */
    private BigDecimal calculateGasFee() {
        BigDecimal gasLimit = getGasLimit();
        LogManage.e("gasLimit---->"+gasLimit.toPlainString());
        BigDecimal gasPriceWei = Convert.toWei(getGasPrice(), Convert.Unit.GWEI);
        String gasFee = gasPriceWei.multiply(getGasLimit()).toPlainString();
        return Convert.fromWei(gasFee, Convert.Unit.ETHER);
    }

    /**
     * 获取gasPrice
     */
    private String getGasPrice() {
        if (layoutGasSetting.getVisibility() == View.VISIBLE) {
            return Kits.Text.getText(edGasPrice);
        } else {
            if (layoutSpeedBest.isSelected()) {
                return gasBean.getFastGasPrice().toPlainString();
            } else if (layoutSpeedFast.isSelected()) {
                return gasBean.getProposeGasPrice().toPlainString();
            } else if (layoutSpeedNormal.isSelected()) {
                return gasBean.getSafeGasPrice().toPlainString();
            } else {//一般不会走这里
                return gasBean.getFastGasPrice().toPlainString();
            }
        }
    }

    /**
     * 获取gasLimit
     */
    private BigDecimal getGasLimit() {
        if (layoutGasSetting.getVisibility() == View.VISIBLE) {
            LogManage.e("获取gasLimit--->"+new BigDecimal(Kits.Text.getText(edGasLimit)));
            return new BigDecimal(Kits.Text.getText(edGasLimit));
        } else {
            LogManage.e("获取gasLimitxxxx--->"+new BigDecimal(gasBean.getGasLimit().intValue()));
            return new BigDecimal(gasBean.getGasLimit().intValue());
        }
    }


    private OnClickedListener onClickedListener;

    public ChooseGasDialog setOnClickedListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClick(String gasPrice, BigDecimal gasLimit);
    }
}