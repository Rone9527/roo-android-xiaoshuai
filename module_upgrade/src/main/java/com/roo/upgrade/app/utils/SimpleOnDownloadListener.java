package com.roo.upgrade.app.utils;

import com.azhon.appupdate.listener.OnDownloadListener;
import com.roo.core.utils.LogManage;

import java.io.File;

/**
 * <pre>
 *     project name: xuetu_android_new
 *     author      : 李琼
 *     create time : 2019-06-09 23:22
 *     desc        : 描述--//SimpleOnDownloadListener
 * </pre>
 */

public class SimpleOnDownloadListener implements OnDownloadListener {
    @Override
    public void start() {
        LogManage.i(">>start()>>");
    }

    @Override
    public void downloading(int max, int progress) {
        LogManage.i(">>downloading()>>" + "max = [" + max + "], progress = [" + progress + "]");
    }

    @Override
    public void done(File apk) {
        LogManage.i(">>done()>>" + "apk = [" + apk.getAbsolutePath() + "]");

    }

    @Override
    public void cancel() {
        LogManage.i(">>cancel()>>");
    }

    @Override
    public void error(Exception e) {
        LogManage.i(">>error()>>");
    }
}
