package com.jiameng.qrcode.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jiameng.qrcode.mvp.contract.QrcodeScanContract;

import javax.inject.Inject;


@ActivityScope
public class QrcodeScanModel extends BaseModel implements QrcodeScanContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public QrcodeScanModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        this.mApplication = null;
    }
}