package com.roo.wallet;

import android.app.Application;

import com.billy.cc.core.component.CC;
import com.facebook.stetho.Stetho;
import com.roo.core.ComponentApplication;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.utils.ProcessUtils;
import com.tencent.mmkv.MMKV;

import java.io.File;

import cachewebviewlib.CacheType;
import cachewebviewlib.WebViewCacheInterceptor;
import cachewebviewlib.WebViewCacheInterceptorInst;
import cn.jpush.android.api.JPushInterface;

public class WalletApplication extends ComponentApplication {
    private static Application mainApplication;

    public static Application getMainApplication() {
        return mainApplication;
    }

    /**
     * CacheWebView通过拦截资源实现自定义缓存静态资源。突破WebView缓存空间限制，让缓存更简单。让网站离线也能正常访问。
     *
     * @see <a href="https://github.com/yale8848/CacheWebView"></a>
     */
    @Override
    public void onCreate() {
        super.onCreate();
        System.loadLibrary("TrustWalletCore");
        mainApplication = this;


        WebViewCacheInterceptor.Builder builder = new WebViewCacheInterceptor.Builder(this);

        builder.setCachePath(new File(getExternalCacheDir().getAbsolutePath(), "web"))//设置缓存路径，默认getCacheDir，名称CacheWebViewCache
                .setCacheSize(1024 * 1024 * 100)//设置缓存大小，默认100M
                .setConnectTimeoutSecond(20)//设置http请求链接超时，默认20秒
                .setReadTimeoutSecond(20)//设置http请求链接读取超时，默认20秒
                .setCacheType(CacheType.FORCE);//设置缓存为正常模式，默认模式为强制缓存静态资源
        WebViewCacheInterceptorInst.getInstance().init(builder);


        MMKV.initialize(this);
        Stetho.initializeWithDefaults(this);
        initPush();

        if (ProcessUtils.isMainProcess(this)) {
            CC.init(this);
            CC.enableVerboseLog(GlobalConstant.DEBUG_MODEL);
            CC.enableDebug(GlobalConstant.DEBUG_MODEL);
            CC.enableRemoteCC(GlobalConstant.DEBUG_MODEL);

            //InjectSplashGlobally.inject(this, AdActivity.class, new SimpleScreensListener() {
            //    @Override
            //    public void onReceiverActionUserPresent(Context context, Intent intent, Class<?> splashClass) {
            //        String currActivity = GlobalContext.getCurrActivity().getClass().getSimpleName();
            //        ToastUtils.showDebug(currActivity);
            //        if (!TextUtils.isEmpty(ACache.get(WalletApplication.this).getAsString(BootInitIntentService.KEY_AD))
            //                && !ACache.get(context).getAsBool(Constants.KEY_SPLASH_MAX_PER_SHOW, false)
            //                && !currActivity.contains(SplashActivity.class.getSimpleName())
            //                && !currActivity.contains(AdActivity.class.getSimpleName())) {
            //            ACache.get(context).put(Constants.KEY_SPLASH_MAX_PER_SHOW, true, 4);
            //            SimpleScreensListener.startSplashActivity(context, splashClass);
            //        }
            //    }
            //});
        }
    }

    //初始化推送
    private void initPush() {
        JPushInterface.init(this);
        JPushInterface.setDebugMode(GlobalConstant.DEBUG_MODEL);
    }

}
