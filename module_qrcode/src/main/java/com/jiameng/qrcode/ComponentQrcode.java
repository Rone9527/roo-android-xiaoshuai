package com.jiameng.qrcode;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.zxing.integration.android.IntentIntegrator;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.jiameng.qrcode.mvp.ui.activity.QrcodeScanActivity;
import com.roo.core.app.GlobalContext;
import com.roo.core.app.component.Qrcode;
import com.roo.core.utils.ImageUtils;
import com.roo.core.utils.IntentUtils;
import com.roo.core.utils.handler.HandlerUtil;
import com.roo.zxing.QRCodeDecoder;
import com.roo.zxing.QRCodeUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;

public class ComponentQrcode implements IComponent {
    @Override
    public String getName() {
        return Qrcode.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case Qrcode.Action.QrcodeScanActivity:
                HandlerUtil.runOnUiThread(() -> openQrcodeScanActivity(cc));
                return true;
            case Qrcode.Action.ContentEncode:
                return contentEncode(cc);
            case Qrcode.Action.BitmapDecode:
                return bitmapDecode(cc);
            default:
                return false;
        }
    }

    private boolean bitmapDecode(CC cc) {
        String picUrl = cc.getParamItem(Qrcode.Param.Text);
        Glide.with(cc.getContext()).asBitmap().load(picUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                String result = QRCodeDecoder.syncDecodeQRCode(resource);
                String value = TextUtils.isEmpty(result) ? "" : result;

                if (value.startsWith("http")) {
                    CC.sendCCResult(cc.getCallId(), CCResult.error(Qrcode.Result.RESULT, value));
                } else {
                    CC.sendCCResult(cc.getCallId(), CCResult.success(Qrcode.Result.RESULT, value));
                }
            }
        });
        return true;
    }

    private boolean contentEncode(CC cc) {
        int screenWidth = ArmsUtils.getScreenWidth(GlobalContext.getAppContext());
        String content = cc.getParamItem(Qrcode.Param.Text);
        Bitmap qrCodeBitmap = ImageUtils.compressImage(QRCodeUtil.createQRCodeBitmap(content, screenWidth));
        CC.sendCCResult(cc.getCallId(), CCResult.success(Qrcode.Result.RESULT, qrCodeBitmap));
        return true;
    }

    private void openQrcodeScanActivity(CC cc) {
        if (cc.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) cc.getContext();

            RxPermissions rxPermissions = new RxPermissions(activity);
            PermissionUtil.RequestPermission requestPermission = new PermissionUtil.RequestPermission() {
                @Override
                public void onRequestPermissionSuccess() {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                    intentIntegrator.setCaptureActivity(QrcodeScanActivity.class);
                    Intent scanIntent = intentIntegrator.createScanIntent();
                    scanIntent.putExtra(QrcodeScanActivity.KEY_CALL_ID, cc.getCallId());
                    activity.startActivityForResult(scanIntent, IntentIntegrator.REQUEST_CODE);

                }

                @Override
                public void onRequestPermissionFailure(List<String> permissions) {
                }

                @Override
                public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                    IntentUtils.gotoPermissionDetail(cc.getContext(), cc.getContext().getPackageName());
                }
            };
            RxErrorHandler rxErrorHandler = RxErrorHandler.builder()
                    .with(activity)
                    .responseErrorListener(ResponseErrorListener.EMPTY)
                    .build();
            PermissionUtil.requestPermission(requestPermission, rxPermissions, rxErrorHandler,
                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


}
