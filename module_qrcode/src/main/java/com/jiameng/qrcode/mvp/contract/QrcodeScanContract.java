package com.jiameng.qrcode.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.jiameng.qrcode.mvp.ui.activity.QrcodeScanActivity;


public interface QrcodeScanContract {

    interface View extends IView {

        QrcodeScanActivity getActivity();
    }

    interface Model extends IModel {

    }
}
