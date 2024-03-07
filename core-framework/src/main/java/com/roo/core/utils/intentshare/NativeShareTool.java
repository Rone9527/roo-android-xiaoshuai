package com.roo.core.utils.intentshare;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.intentshare.utils.PlatformUtil;

import java.io.File;
import java.util.List;

/**
 * Created by xiangrikui on 2018/3/23.
 * 使用原生Intent进行分享
 * <p>
 * "微信好友-文本":
 * NativeShareTool.getInstance(this).shareWechatFriend(String);
 * "微信好友-图片":
 * NativeShareTool.getInstance(this).shareWechatFriend(Bitmap, true);
 * "微信好友-文件":
 * NativeShareTool.getInstance(this).shareWechatFriend(File, false);
 * "微信朋友圈-单图":
 * NativeShareTool.getInstance(this).shareWechatMoment(File);
 * "QQ好友-文本":
 * NativeShareTool.getInstance(this).shareQQ(String);
 * "QQ好友-图片":
 * NativeShareTool.getInstance(this).shareImageToQQ(Bitmap);
 * "QQ好友-文件":
 * NativeShareTool.getInstance(this).shareImageToQQ(File);
 * "QQ空间":
 * NativeShareTool.getInstance(this).shareImageToQQZone(String);
 * "新浪好友":
 * NativeShareTool.getInstance(this).shareToSinaFriends(Context, true, String);
 * "新浪微博":
 * NativeShareTool.getInstance(this).shareToSinaFriends(Context, false, String);
 */

public class NativeShareTool {


    public static NativeShareTool getInstance() {
        return Holder.instantce;
    }

    private static class Holder {
        static NativeShareTool instantce = new NativeShareTool();
    }

    private NativeShareTool() {

    }

    /**
     * 直接分享文本到微信好友
     */
    public void shareWechatFriend(String content, Activity activity) {
        if (PlatformUtil.isWeChatAvailable(activity)) {
            Intent intent = new Intent();
            ComponentName cop = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
            intent.setComponent(cop);
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra("android.intent.extra.TEXT", content);
            intent.putExtra("Kdescription", !TextUtils.isEmpty(content) ? content : "");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } else {
            ToastUtils.show( "您需要安装微信客户端");
        }
    }

    /**
     * 直接分享图片或文件给微信好友
     *
     * @param file      文件
     * @param isPicFile 是否为图片文件
     * @param activity
     */
    public void shareWechatFriend(File file, boolean isPicFile, Activity activity) {
        if (PlatformUtil.isWeChatAvailable(activity)) {
            Intent intent = new Intent();
            ComponentName cop = new ComponentName(PlatformUtil.PACKAGE_WECHAT, PlatformUtil.ACTIVITY_SHARE_WECHAT_FRIEND);
            intent.setComponent(cop);
            intent.setAction(Intent.ACTION_SEND);
            if (isPicFile) {
                intent.setType("image/*");
            } else {
                intent.setType("*/*");
            }
            if (file != null) {
                if (file.isFile() && file.exists()) {
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        String authority = activity.getPackageName().concat(".fileprovider");
                        uri = FileProvider.getUriForFile(activity, authority, file);
                    } else {
                        uri = Uri.fromFile(file);
                    }
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                }
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } else {
            ToastUtils.show("您需要安装微信客户端");
        }
    }

    /**
     * 直接分享文本和图片到微信朋友圈
     * 微信6.6.7以后已经不支持分享标题了，详情查看：https://www.jianshu.com/p/57935d90bf75
     * 微信6.7.3以后已经不支持多图分享了，详情查看：https://www.jianshu.com/p/1158d7c20a8b
     */
    public void shareWechatMoment(File picFile, Activity activity) {
        if (PlatformUtil.isWeChatAvailable(activity)) {
            Intent intent = new Intent();
            //分享精确到微信的页面，朋友圈页面，或者选择好友分享页面
            ComponentName comp = new ComponentName(PlatformUtil.PACKAGE_WECHAT, PlatformUtil.ACTIVITY_SHARE_WECHAT_MOMENT);
            intent.setComponent(comp);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            if (picFile != null) {
                if (picFile.isFile() && picFile.exists()) {
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        String authority = activity.getPackageName().concat(".fileprovider");
                        uri = FileProvider.getUriForFile(activity, authority, picFile);
                    } else {
                        uri = Uri.fromFile(picFile);
                    }
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                }
            }
            activity.startActivityForResult(intent, 101);
        } else {
            ToastUtils.show("您需要安装微信客户端");
        }
    }

    /**
     * 直接分享纯文本内容至QQ好友
     *
     * @param content 分享文本
     * @param context
     */
    public void shareQQ(String content, Activity context) {
        if (PlatformUtil.isQQClientAvailable(context)) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, content);
            intent.setType("text/plain");
            intent.setComponent(new ComponentName(PlatformUtil.PACKAGE_QQ, PlatformUtil.ACTIVITY_SHARE_QQ_FRIEND));
            context.startActivity(intent);
        } else {
            ToastUtils.show("您需要安装QQ客户端");
        }
    }


    /**
     * 分享图片给QQ好友
     *
     * @param bitmap
     */
    public void shareImageToQQ(Bitmap bitmap, Activity activity) {
        if (PlatformUtil.isQQClientAvailable(activity)) {
            try {
                Uri uriToImage = Uri.parse(MediaStore.Images.Media.insertImage(
                        activity.getContentResolver(), bitmap, null, null));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("image/*");
                // 遍历所有支持发送图片的应用。找到需要的应用
                ComponentName componentName = new ComponentName(PlatformUtil.PACKAGE_QQ, PlatformUtil.ACTIVITY_SHARE_QQ_FRIEND);

                shareIntent.setComponent(componentName);
                activity.startActivity(shareIntent);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 分享文件给QQ好友
     *
     * @param file
     */
    public void shareImageToQQ(File file, Activity activity) {
        if (PlatformUtil.isQQClientAvailable(activity)) {
            try {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("*/*");
                // 遍历所有支持发送图片的应用。找到需要的应用
                ComponentName componentName = new ComponentName(PlatformUtil.PACKAGE_QQ, PlatformUtil.ACTIVITY_SHARE_QQ_FRIEND);

                shareIntent.setComponent(componentName);

                if (file != null) {
                    if (file.isFile() && file.exists()) {
                        Uri uri;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            String authority = activity.getPackageName().concat(".fileprovider");
                            uri = FileProvider.getUriForFile(activity, authority, file);
                        } else {
                            uri = Uri.fromFile(file);
                        }
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    }
                }
                activity.startActivity(shareIntent);
            } catch (Exception e) {
            }
        }
    }

    /**
     * description : 分享到QQ空间
     * created at 2018/7/9 17:04
     *
     * @param photoPath 照片路径
     */
    public void shareImageToQQZone(String photoPath, Activity activity) {
        try {
            if (PlatformUtil.isInstalledSpecifiedApp(activity, PlatformUtil.PACKAGE_QZONG)) {
                File file = new File(photoPath);
                if (!file.exists()) {
                    String tip = "文件不存在";
                    ToastUtils.show(tip + " path = " + photoPath);
                    return;
                }

                Intent intent = new Intent();
                ComponentName componentName = new ComponentName(PlatformUtil.PACKAGE_QZONG, PlatformUtil.ACTIVITY_SHARE_QQ_ZONE);
                intent.setComponent(componentName);
                intent.setAction("android.intent.action.SEND");
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_TEXT, "I'm so tired!!");
                if (file.isFile() && file.exists()) {
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        String authority = activity.getPackageName().concat(".fileprovider");
                        uri = FileProvider.getUriForFile(activity, authority, file);
                    } else {
                        uri = Uri.fromFile(file);
                    }
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                }
                activity.startActivity(intent);
            } else {
                ToastUtils.show("您需要安装QQ空间客户端");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * description : 微博分享
     * created at 2018/7/10 13:58
     */
    public void shareToSinaFriends(Activity activity, boolean isFriends, String photoPath) {
        if (PlatformUtil.isInstalledSpecifiedApp(activity, PlatformUtil.PACKAGE_SINA)) {

            File file = new File(photoPath);
            if (!file.exists()) {
                String tip = "文件不存在";
                ToastUtils.show(tip + " path = " + photoPath);
                return;
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            // 使用以下两种type有一定的区别，"text/plain"分享给指定的粉丝或好友 ；"image/*"分享到微博内容
            if (isFriends) {
                intent.setType("text/plain");
            } else {
                intent.setType("image/*");// 分享文本|文本+图片|图片 到微博内容时使用
            }
            PackageManager packageManager = activity.getPackageManager();
            List<ResolveInfo> matchs = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            ResolveInfo resolveInfo = null;
            for (ResolveInfo each : matchs) {
                String pkgName = each.activityInfo.applicationInfo.packageName;
                if (PlatformUtil.PACKAGE_SINA.equals(pkgName)) {
                    resolveInfo = each;
                    break;
                }
            }
            // type = "image/*"--->com.sina.weibo.composerinde.ComposerDispatchActivity
            // type = "text/plain"--->com.sina.weibo.weiyou.share.WeiyouShareDispatcher
            intent.setClassName(PlatformUtil.PACKAGE_SINA, resolveInfo.activityInfo.name);// 这里在使用resolveInfo的时候需要做判空处理防止crash
            intent.putExtra(Intent.EXTRA_TEXT, "Test Text String !!");
            if (file.isFile() && file.exists()) {
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String authority = activity.getPackageName().concat(".fileprovider");
                    uri = FileProvider.getUriForFile(activity, authority, file);
                } else {
                    uri = Uri.fromFile(file);
                }
                intent.putExtra(Intent.EXTRA_STREAM, uri);
            }
            activity.startActivity(intent);
        } else {
            ToastUtils.show("您需要安装sina客户端");
        }
    }

}