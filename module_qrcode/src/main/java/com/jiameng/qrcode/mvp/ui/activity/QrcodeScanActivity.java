package com.jiameng.qrcode.mvp.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.google.zxing.ResultPoint;
import com.gyf.immersionbar.ImmersionBar;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jiameng.qrcode.R;
import com.jiameng.qrcode.di.component.DaggerQrcodeScanComponent;
import com.jiameng.qrcode.di.module.QrcodeScanModule;
import com.jiameng.qrcode.mvp.contract.QrcodeScanContract;
import com.jiameng.qrcode.mvp.presenter.QrcodeScanPresenter;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;

import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.roo.core.app.component.Qrcode;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.ui.widget.DialogLoading;
import com.roo.core.utils.GlideEngine;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.ViewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 扫一扫
 */
public class QrcodeScanActivity extends I18nActivityArms<QrcodeScanPresenter> implements QrcodeScanContract.View {

    private DecoratedBarcodeView mZxingBarcodeScanner;
    private CaptureManager capture;
    public static final String KEY_CALL_ID = "KEY_CALL_ID";
    private boolean isFlashlight = false;
    private String callId;
    private String result;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        TitleBarView mTitleBarView = ViewHelper.initTitleBar(getString(R.string.title_scan), this, false);
        ImmersionBar.with(this).statusBarDarkFont(false).init();

        mTitleBarView.setTitleMainTextColor(ContextCompat.getColor(this, R.color.white));
        /*从相册中选择*/
        mTitleBarView.setRightTextColor(ContextCompat.getColor(this, R.color.white));
        mTitleBarView.setRightTextSize(16);
        mTitleBarView.setRightText(getString(R.string.album));
        mTitleBarView.setOnRightTextClickListener(v -> onSelectPhoto());

        mZxingBarcodeScanner = findViewById(R.id.zxing_barcode_scanner);
        callId = getIntent().getStringExtra(KEY_CALL_ID);

        ImageView mFlashlight = findViewById(R.id.flashlight);
        RxView.clicks(mFlashlight).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (isFlashlight) {
                        //处于[亮]的状态
                        mZxingBarcodeScanner.setTorchOff();
                    } else {
                        //处于[暗]的状态
                        mZxingBarcodeScanner.setTorchOn();
                    }
                    int drawableId = isFlashlight ? R.drawable.ic_qrcode_torch_on :
                            R.drawable.ic_qrcode_torch_off;
                    mFlashlight.setImageResource(drawableId);
                    isFlashlight = !isFlashlight;
                });

        mZxingBarcodeScanner.getStatusView().setText("");
        capture = new CaptureManager(this, mZxingBarcodeScanner);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
        mZxingBarcodeScanner.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                onDecodeSuccess(result.getResult().getText());
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
    }

    private void onSelectPhoto() {
        EasyPhotos.createAlbum(getActivity(), true, new GlideEngine())
                .setFileProviderAuthority(getActivity().getPackageName().concat(".fileprovider"))
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, ArrayList<String> paths, boolean isOriginal) {
                        if (paths.isEmpty()) {
                            return;
                        }
                        DialogLoading.getInstance().showDialog(getActivity(), true);
                        mPresenter.decodeImage(paths.get(0));
                    }
                });
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_qrcode_scan;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerQrcodeScanComponent
                .builder()
                .appComponent(appComponent)
                .qrcodeScanModule(new QrcodeScanModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public QrcodeScanActivity getActivity() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        capture.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mZxingBarcodeScanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    public void onDecodeSuccess(String result) {
        if (TextUtils.isEmpty(this.result) && !TextUtils.isEmpty(result)) {
            this.result = result;
            mZxingBarcodeScanner.pause();
            LogManage.e("result:" + result);
            GlobalUtils.vibrator(this);
            if (result.startsWith("http")) {
                CC.sendCCResult(callId, CCResult.error(Qrcode.Result.RESULT, result));
            } else {
                CC.sendCCResult(callId, CCResult.success(Qrcode.Result.RESULT, result));
            }
            finish();
        }
    }
}
