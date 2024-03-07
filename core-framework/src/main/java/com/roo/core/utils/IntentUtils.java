package com.roo.core.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 打开第三方App
 */

public class IntentUtils {
    public static final String WECHAT_PACKAGE = "com.tencent.mm";
    public static final String QQ_PACKAGE = "com.tencent.mobileqq";
    public static final String QQ_TIME_PACKAGE = "com.tencent.tim";
    public static final String WEIBO_PACKAGE = "com.sina.weibo";

    private static final HashMap<String, ArrayList<String>> APP_COMPONENT_NAME = new HashMap<>();

    static {
        APP_COMPONENT_NAME.put(ComponentPair.WeChat, new ArrayList<String>() {{
            add(WECHAT_PACKAGE);
            add("com.tencent.mm.ui.LauncherUI");
        }});
        APP_COMPONENT_NAME.put(ComponentPair.WeiBo, new ArrayList<String>() {{
            add(WEIBO_PACKAGE);
            add("com.sina.weibo.SplashActivity");
        }});
        APP_COMPONENT_NAME.put(ComponentPair.QQ, new ArrayList<String>() {{
            add(QQ_PACKAGE);
            add("com.tencent.mobileqq.activity.SplashActivity");
        }});
        APP_COMPONENT_NAME.put(ComponentPair.TIM, new ArrayList<String>() {{
            add(QQ_TIME_PACKAGE);
            add("com.tencent.mobileqq.activity.SplashActivity");
        }});
    }

    /**
     * 根据协议进行跳转
     *
     * @param context
     * @param action
     */
    public static void openUrl(Context context, String action) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(action));
        if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                context.startActivity(intent);
            } catch (RuntimeException e) {
                LogManage.w("activity not found for action = " + action);
            }
        }
    }

    /**
     * 使用包名打开第三方App
     */
    public static boolean openApp(Context context, @ComponentPair String key) {
        return openApp(context, key, true);
    }

    public static boolean openApp(Context context, @ComponentPair String key, boolean showToast) {
        ArrayList<String> componentPair = APP_COMPONENT_NAME.get(key);
        return openApp(context, componentPair.get(0), componentPair.get(1), showToast);
    }

    public static boolean openApp(String packageName, Context context) {
        return openApp(context, packageName, null, true);
    }

    public static boolean openApp(Context context, String packageName, String launchActivity) {
        return openApp(context, packageName, launchActivity, true);
    }

    public static boolean openApp(Context context, String packageName, String launchActivity, boolean showToast) {
        Intent intent = new Intent();
        if (launchActivity == null) {
            PackageManager packageManager = context.getPackageManager();
            intent = packageManager.getLaunchIntentForPackage(packageName);
        } else {
            ComponentName cmp = new ComponentName(packageName, launchActivity);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
        }
        try {
            context.startActivity(intent);
        } catch (RuntimeException e) {
            if (showToast) {
                ToastUtils.show("您可能没有安装该应用");
            }
            return false;
        }
        return true;
    }

    /**
     * 发送邮件
     *
     * @param context
     * @param subject
     * @param prefix
     * @param title
     */
    public static void sendEmail(Context context, String email, String subject, String prefix, String title) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setDataAndType(Uri.parse("mailto:"), "*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);//主题
        intent.putExtra(Intent.EXTRA_TEXT, prefix);//内容
        try {
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(Intent.createChooser(intent, title));//标题
            }
        } catch (RuntimeException ex) {
            ToastUtils.show("您可能没有安装邮箱客户端");
        }
    }

    /**
     * 开启定位
     */
    public static void locationSetting(Activity activity) {
        Intent i = new Intent();
        i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivity(i);
    }

    /**
     * 打开应用市场
     *
     * @param context
     * @param packageName
     */
    public static void openMarket(Context context, String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (RuntimeException e) {
            ToastUtils.show("无法打开应用市场");
        }
    }

    /**
     * 打开网络设置
     *
     * @param activity
     */
    public static void openNetworkSetting(Activity activity) {
        activity.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    }

    /**
     * 辅助功能设置界面
     */
    public static void openAccessibitySettings(Context context) {
        try {
            context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        } catch (RuntimeException e) {
            e.printStackTrace();
            ToastUtils.show("没有找到辅助功能设置界面，请手动开启！");
        }
    }

    /**
     * 通过包名查看应用的详情
     *
     * @param packageName
     */
    public static void openAppDetail(Context context, String packageName) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Uri uri = Uri.fromParts("package", packageName, null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 跳转到授权管理页面:用户如果拒绝授权
     */
    public static void gotoPermissionDetail(Context context, String applicationId) {
        if (RomUtils.isMiuiRom()) {
            gotoMiuiPermission(context);//小米
        } else if (RomUtils.isFlymeRom()) {
            gotoMeizuPermission(context, applicationId);
        } else if (RomUtils.isHuaweiRom()) {
            gotoHuaweiPermission(context);
        } else {
            context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    /**
     * 跳转到miui的权限管理页面
     *
     * @param context
     */
    private static void gotoMiuiPermission(Context context) {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(localIntent);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(localIntent);
            } catch (Exception e1) { // 否则跳转到应用详情
                context.startActivity(getAppDetailSettingIntent(context));
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     *
     * @param context
     */
    private static void gotoMeizuPermission(Context context, String applicationId) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", applicationId);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    /**
     * 华为的权限管理页面
     *
     * @param context
     */
    private static void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }

    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     *
     * @param context
     * @return
     */
    private static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return localIntent;
    }

    @StringDef({
            ComponentPair.WeChat,
            ComponentPair.WeiBo,
            ComponentPair.QQ,
            ComponentPair.TIM
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ComponentPair {
        String WeChat = "微信";
        String WeiBo = "微博";
        String QQ = "QQ";
        String TIM = "TIM";
    }
}
