package com.roo.wallet.mvp.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.DeviceUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.ui.base.I18nActivityArms;
import com.roo.core.utils.ImageUtils;
import com.roo.core.utils.LanguageUtil;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.handler.HandlerUtil;
import com.roo.router.Router;
import com.roo.wallet.R;

import org.simple.eventbus.EventBus;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class AdActivity extends I18nActivityArms {

    private static final String TAG = AdActivity.class.getSimpleName();
    public static final String KEY_SOURCE = "KEY_SOURCE";
    public static final String KEY_SOURCE_SPLASH = "KEY_SOURCE_SPLASH";
    private ImageView mSpBgImage;
    private TextView mSpJumpBtn;
    private FrameLayout layoutJump;
    private int millisUntilFinished = 3_000;

    private final Runnable countDownTimer = new Runnable() {
        @Override
        public void run() {
            if (millisUntilFinished > 0) {
                mSpJumpBtn.setText(MessageFormat.format("{0}({1}s)", LanguageUtil.isZh() ? "跳过" : "Skip", millisUntilFinished / 1000));
                millisUntilFinished = millisUntilFinished - 1000;
                HandlerUtil.runOnUiThreadDelay(countDownTimer, 1000);
            } else {
                mSpJumpBtn.setText(MessageFormat.format("{0}(0s)", LanguageUtil.isZh() ? "跳过" : "Skip"));
                gotoMainActivity();
            }
        }
    };

    public static void start(Context context, String source) {
        Router.newIntent(context)
                .to(AdActivity.class)
                .putString(KEY_SOURCE, source)
                .launch();
    }

    private void gotoWebActivity(String url) {
        if (KEY_SOURCE_SPLASH.equals(getIntent().getStringExtra(KEY_SOURCE))) {
            MainActivity.start(this, url);
        } else {
            HashMap<String, Object> param = new HashMap<>();
            param.put(Constants.KEY_URL, url);
            EventBus.getDefault().post(param, EventBusTag.GOTO_WEBVIEW);
        }
        HandlerUtil.removeRunable(countDownTimer);
        finish();
    }

    private void gotoMainActivity() {
        if (KEY_SOURCE_SPLASH.equals(getIntent().getStringExtra(KEY_SOURCE))) {
            MainActivity.start(this, false);
        }
        HandlerUtil.removeRunable(countDownTimer);
        finish();
    }

    @Override
    public void onBackPressed() {
        HandlerUtil.removeRunable(countDownTimer);
        super.onBackPressed();
    }

    private void setImage(Bitmap drawable) {
        Bitmap bitmap = ImageUtils.copyBitmap(drawable);
        int screenWidth = DeviceUtils.getRealScreenSize(this)[0];
        int screenHeight = DeviceUtils.getRealScreenSize(this)[1];
        LogManage.e("setImage", " screenWidth " + screenWidth + " screenHeight " + screenHeight + " screenWidth/screenHeight" + (screenWidth / screenHeight));
        LogManage.e("setImage", " bitmap.getWidth() " + bitmap.getWidth() + " bitmap.getHeight() " + bitmap.getHeight() + " bitmap.getWidth()/bitmap.getHeight()" + (bitmap.getWidth() / bitmap.getHeight()));

        if ((screenWidth * 1f / screenHeight) < (bitmap.getWidth() * 1f / bitmap.getHeight())) {
            mSpBgImage.setImageBitmap(bitmap);
        } else {//裁切多余的高度
            int cropHeight = bitmap.getWidth() * screenHeight / screenWidth;
            LogManage.e("setImage", "cropHeight " + cropHeight);
            mSpBgImage.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), cropHeight, null, true));
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ad;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();

        mSpBgImage = findViewById(R.id.mSpBgImage);
        ImageView mBgImageBottom = findViewById(R.id.mBgImageBottom);
        mSpJumpBtn = findViewById(R.id.mSpJumpBtn);
        layoutJump = findViewById(R.id.layoutJump);

//        List<BannerBean> bannerBeans = new Gson().fromJson(ACache.get(this)
//                .getAsString(BootInitIntentService.KEY_AD), new TypeToken<List<BannerBean>>() {
//        }.getType());
//        if (!bannerBeans.isEmpty()) {
//            String adPicUrl = GlobalConstant.BASE_URL + bannerBeans.get(0).getBannerPath();
//            String filePath = SharedPref.getString(Codec.MD5.getStringMD5(adPicUrl));
//            if (Kits.File.isFileExist(filePath)) {
//                Bitmap decodeFile = BitmapFactory.decodeFile(filePath);
//                setImage(decodeFile);
//                LogManage.e(TAG, "广告图片地址" + filePath);
//            } else {
//                Glide.with(this).asBitmap().load(adPicUrl)
//                        .into(new SimpleTarget<Bitmap>() {
//                            @Override
//                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                                setImage(resource);
//                                File file = Kits.File.savePhoto(Codec.MD5.getStringMD5(adPicUrl), ImageUtils.compressImage(resource), "cached");
//                                SharedPref.putString(Codec.MD5.getStringMD5(adPicUrl), file.getAbsolutePath());
//                                LogManage.e(TAG, "广告图片下载成功" + file.getAbsolutePath());
//                            }
//                        });
//                LogManage.e(TAG, "广告图片地址" + filePath);
//            }
//        }

        HandlerUtil.runOnUiThread(countDownTimer);
        layoutJump.setVisibility(View.VISIBLE);

        RxView.clicks(mSpBgImage).throttleFirst(3, TimeUnit.SECONDS)
                .subscribe(o -> gotoWebActivity(""));
        RxView.clicks(layoutJump).throttleFirst(3, TimeUnit.SECONDS)
                .subscribe(o -> gotoMainActivity());
    }
}
