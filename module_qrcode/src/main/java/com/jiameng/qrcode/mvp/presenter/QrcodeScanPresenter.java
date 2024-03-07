package com.jiameng.qrcode.mvp.presenter;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jiameng.qrcode.mvp.contract.QrcodeScanContract;
import com.jiameng.qrcode.mvp.ui.activity.QrcodeScanActivity;
import com.roo.core.ui.widget.DialogLoading;
import com.roo.core.utils.ToastUtils;
import com.roo.zxing.QRCodeDecoder;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class QrcodeScanPresenter extends BasePresenter<QrcodeScanContract.Model, QrcodeScanContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public QrcodeScanPresenter(QrcodeScanContract.Model model, QrcodeScanContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    public void decodeImage(String resultPaths) {
        new DecodeImageAsyncTask(mRootView.getActivity()).execute(resultPaths);
    }

    public static class DecodeImageAsyncTask extends AsyncTask<String, Void, String> {
        private final WeakReference<QrcodeScanActivity> mActivity;

        private DecodeImageAsyncTask(QrcodeScanActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... params) {
            return QRCodeDecoder.syncDecodeQRCode(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            QrcodeScanActivity activity = mActivity.get();
            if (activity == null) {
                return;
            }
            DialogLoading.getInstance().closeDialog();
            if (TextUtils.isEmpty(result)) {
                ToastUtils.show("未发现二维码");
            } else {
                activity.onDecodeSuccess(result);
            }
        }
    }
}
