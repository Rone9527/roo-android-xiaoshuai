package com.roo.wallet.app.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.link.MainToken;
import com.core.domain.manager.ChainCode;
import com.google.gson.Gson;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.BuglyInit;
import com.roo.core.utils.RetrofitFactory;
import com.roo.core.utils.RxUtils;
import com.roo.core.websocket.ClientProxy;
import com.roo.wallet.WalletApplication;
import com.ycbjie.webviewlib.utils.X5LogUtils;
import com.ycbjie.webviewlib.utils.X5WebUtils;

import java.util.List;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import timber.log.Timber;

public class BootInitIntentService extends IntentService {

    private static final String TAG = BootInitIntentService.class.getSimpleName();

    public static final String KEY_AD = "KEY_AD";

    public static final String NOTIFICATION_CHANNEL_ID = "com.roo.wallet.service.ScreenShootService";

    public static final String CHANNEL_NAME = BootInitIntentService.class.getSimpleName();

    /**
     * 构造函数
     */
    public BootInitIntentService() {
        //调用父类的构造函数-构造函数参数=工作线程的名字
        super("AdIntentService");
    }

    public static void start(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, BootInitIntentService.class));
        } else {
            context.startService(new Intent(context, BootInitIntentService.class));
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(chan);
            startForeground(1, new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).build());
        } else {
            startForeground(1, new Notification());
        }
    }

    /**
     * 实现耗时任务的操作
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        if (GlobalConstant.DEBUG_MODEL) {
            Timber.plant(new Timber.DebugTree());
        } else {
            BuglyInit.init(this.getApplication(), "2bbb2de62e");
        }

        ClientProxy.getInstance().init();
//        RxUtils.transform(RetrofitFactory.getRetrofit(ApiService.class).getAdv())
//                .retryWhen(new RetryWithDelay())
//                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<BannerBean>>>(WalletApplication.getRxErrorHandler()) {
//                    @Override
//                    public void onNext(BaseResponse<List<BannerBean>> bannerBeans) {
//                        List<BannerBean> data = bannerBeans.getData();
//                        if (!Kits.Empty.check(data)) {
//                            ACache.get(BootInitIntentService.this).put(KEY_AD, new Gson().toJson(data), Kits.Date.getRightSeconds());
//                            String adPicUrl = GlobalConstant.BASE_URL + data.get(0).getBannerPath();
//                            LogManage.e(TAG, "广告图片地址" + adPicUrl);
//                            Glide.with(BootInitIntentService.this).asBitmap().load(adPicUrl)
//                                    .apply(GlideManger.OPTIONS_IMAGE).into(new SimpleTarget<Bitmap>() {
//                                @Override
//                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                                    File file = Kits.File.savePhoto(Codec.MD5.getStringMD5(adPicUrl), ImageUtils.compressImage(resource), "cached");
//                                    SharedPref.putString(Codec.MD5.getStringMD5(adPicUrl), file.getAbsolutePath());
//                                    LogManage.e(TAG, "广告图片下载成功 file.getAbsolutePath()" + file.getAbsolutePath() + " file.getParent()" + file.getParent());
//                                    //避免某个目录被 MediaStore 扫描
//                                    Kits.File.createFile(file.getParent(), ".nomedia");
//                                }
//                            });
//                        } else {
//                            ACache.get(BootInitIntentService.this).remove(KEY_AD);
//                        }
//                    }
//                });
        RxUtils.transform(RetrofitFactory.getRetrofit(ApiService.class).getLinkListCoin())
                .retryWhen(new RetryWithDelay())
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<LinkTokenBean>>>(WalletApplication.getRxErrorHandler()) {
                    @Override
                    public void onNext(BaseResponse<List<LinkTokenBean>> t) {
                        List<LinkTokenBean> data = t.getData();
                        for (LinkTokenBean tokenBean : data) {
                            List<LinkTokenBean.TokensBean> tokens = tokenBean.getTokens();
                            if (tokenBean.getCode().equalsIgnoreCase(ChainCode.ETH)) {
                                tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.ETH));
                            } else if (tokenBean.getCode().equalsIgnoreCase(ChainCode.BSC)) {
                                tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.BSC));
                            } else if (tokenBean.getCode().equalsIgnoreCase(ChainCode.HECO)) {
                                tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.HECO));
                            } else if (tokenBean.getCode().equalsIgnoreCase(ChainCode.OEC)) {
                                tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.OEC));
                            } else if (tokenBean.getCode().equalsIgnoreCase(ChainCode.TRON)) {
                                tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.TRON));
                            } else if (tokenBean.getCode().equalsIgnoreCase(ChainCode.POLYGON)) {
                                tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.POLYGON));
                            }else if (tokenBean.getCode().equalsIgnoreCase(ChainCode.FANTOM)) {
                                tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.FANTOM));
                            }
                            SharedPref.putString(Constants.KEY_OBJ_LINK_TOKEN, new Gson().toJson(data));
                        }
                    }
                });

        X5WebUtils.init(this.getApplication());
        String cacheDirPath = getApplication().getExternalCacheDir().getAbsolutePath();

        X5WebUtils.initCache(getApplication(), cacheDirPath);
        X5LogUtils.setIsLog(GlobalConstant.DEBUG_MODEL);

    }

}