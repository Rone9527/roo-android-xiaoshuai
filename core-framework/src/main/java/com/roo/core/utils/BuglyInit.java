package com.roo.core.utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.roo.core.app.constants.GlobalConstant;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * <pre>
 *     project name: client-android
 *     author      : 李琼
 *     create time : 2019-09-09 17:06
 *     desc        : 描述--//BuglyInit bugly初始化工具
 * </pre>
 */

public class BuglyInit {
    /**
     * 初始化Bugly
     */
    public static void init(Application application, String appId) {
        Context context = application.getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppVersion(Kits.Package.getVersionName(application));//App的版本
        strategy.setAppPackageName(packageName);  //App的包名
        CrashReport.initCrashReport(context, appId, GlobalConstant.DEBUG_MODEL, strategy);
        CrashReport.setIsDevelopmentDevice(context, GlobalConstant.DEBUG_MODEL);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
