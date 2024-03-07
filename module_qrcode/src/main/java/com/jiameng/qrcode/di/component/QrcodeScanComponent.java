package com.jiameng.qrcode.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jiameng.qrcode.di.module.QrcodeScanModule;
import com.jiameng.qrcode.mvp.ui.activity.QrcodeScanActivity;

import dagger.Component;

@ActivityScope
@Component(modules = QrcodeScanModule.class, dependencies = AppComponent.class)
public interface QrcodeScanComponent {
    void inject(QrcodeScanActivity activity);
}