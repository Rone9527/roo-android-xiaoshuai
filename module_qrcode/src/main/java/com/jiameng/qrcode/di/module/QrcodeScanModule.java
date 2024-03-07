package com.jiameng.qrcode.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jiameng.qrcode.mvp.contract.QrcodeScanContract;
import com.jiameng.qrcode.mvp.model.QrcodeScanModel;

import dagger.Module;
import dagger.Provides;


@Module
public class QrcodeScanModule {
    private QrcodeScanContract.View view;

    /**
     * @param view
     */
    public QrcodeScanModule(QrcodeScanContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    QrcodeScanContract.View provideQrcodeScanView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    QrcodeScanContract.Model provideQrcodeScanModel(QrcodeScanModel model) {
        return model;
    }
}