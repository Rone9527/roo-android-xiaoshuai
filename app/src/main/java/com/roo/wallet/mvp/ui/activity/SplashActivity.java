package com.roo.wallet.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.billy.cc.core.component.CC;
import com.core.domain.SharePreUtil;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.jiameng.lock.InputType;
import com.jiameng.lock.activity.FingerprintActivity;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.GlobalContext;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.utils.ACache;
import com.roo.core.utils.IntentUtils;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.handler.HandlerUtil;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.wallet.R;
import com.roo.wallet.app.service.BootInitIntentService;
import com.roo.wallet.di.component.DaggerSplashComponent;
import com.roo.wallet.mvp.contract.SplashContract;
import com.roo.wallet.mvp.presenter.SplashPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {
    String configUrl = "https://cboeapi-hk.oss-cn-hongkong.aliyuncs.com/config.json";
    @Inject
    RxErrorHandler mErrorHandler;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSplashComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();
        PermissionUtil.RequestPermission requestPermission = new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {

                String id = getIntent().getStringExtra(Constants.PUSH_EXTRA);
                String type = getIntent().getStringExtra(Constants.PUSH_TYPE);
                if (!TextUtils.isEmpty(id)) {
                    SharedPref.putString(Constants.PUSH_EXTRA, id);
                    SharedPref.putString(Constants.PUSH_TYPE, type);
                }

                BootInitIntentService.start(SplashActivity.this);

                if (SharedPref.getBoolean(Constants.KEY_OPEN_SAFE_FINGER_VERIFY)) {
                    FingerprintActivity.start(SplashActivity.this, InputType.TYPE_LOGIN);
                } else if (SharedPref.getBoolean(Constants.KEY_OPEN_SAFE_PASSWORD_VERIFY)) {
                    verifyBySafePassword();
                } else {
                    HandlerUtil.runOnThreadDelay(() -> gotoNextPage(), 1000);
                }
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                IntentUtils.gotoPermissionDetail(SplashActivity.this, getPackageName());
            }
        };
        RxPermissions rxPermissions = new RxPermissions(this);
        PermissionUtil.requestPermission(requestPermission, rxPermissions, mErrorHandler,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);
    }

    private void verifyBySafePassword() {
        SafeKeyboardVerifyActivity.start(SplashActivity.this);
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                String urlStr = configUrl;
//                OkHttpClient httpClient = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(urlStr)
//                        .build();
//                Response response = null;
//                String line = null;
//                try {
//                    response = httpClient.newCall(request).execute();
//                    line = response.body().string();
//                    JSONObject jsonObject =  JSON.parseObject(line);
//                    SharePreUtil.deleShare(getApplicationContext(), "IP");
//                    SharePreUtil.deleShare(getApplicationContext(), "WS");
//                    String ip= (String) jsonObject.get("IP");
//                    String ws= (String) jsonObject.get("WS");
//                    SharePreUtil.putString(getApplicationContext(), "IP", ip);
//                    SharePreUtil.putString(getApplicationContext(), "WS", ws);
////                    SpUtil.put(CommonConfig.IP, "https://soure.exness-api.com");
////                    SpUtil.put(CommonConfig.WS, "ws://soure.exness-api.com/ws");
////                    {"IP": "https://source.cbotapi.com","WS": "wss://source.cbotapi.com/ws"}
//                    System.out.println("SplashActivity IP:"+ip);
//                    System.out.println("SplashActivity WS:"+ws);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                SafeKeyboardVerifyActivity.start(SplashActivity.this);
//            }
//        }).start();
    }

    @Override
    public void gotoNextPage() {
        if (SharedPref.getBoolean(Constants.KEY_GOTO_GUIDE, true)) {
            GuiderActivity.start(SplashActivity.this);
        } else {
            if (TextUtils.isEmpty(ACache.get(SplashActivity.this).getAsString(BootInitIntentService.KEY_AD))) {
                if (Kits.Empty.check(EthereumWalletUtils.getInstance().getWallet(GlobalContext.getAppContext()))) {
                    CC.obtainBuilder(Wallet.NAME)
                            .setContext(SplashActivity.this)
                            .setActionName(Wallet.Action.WalletCreateNormalActivity)
                            .build().callAsyncCallbackOnMainThread((cc, result) -> {
                    });
                } else {
                    MainActivity.start(SplashActivity.this, false);
                }
            } else {
                AdActivity.start(SplashActivity.this, AdActivity.KEY_SOURCE_SPLASH);
            }
        }
        finish();

//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                String urlStr = configUrl;
//                OkHttpClient httpClient = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(urlStr)
//                        .build();
//                Response response = null;
//                String line = null;
//                try {
//                    response = httpClient.newCall(request).execute();
//                    line = response.body().string();
//                    JSONObject jsonObject =  JSON.parseObject(line);
//                    SharePreUtil.deleShare(getApplicationContext(), "IP");
//                    SharePreUtil.deleShare(getApplicationContext(), "WS");
//                    String ip= (String) jsonObject.get("IP");
//                    String ws= (String) jsonObject.get("WS");
//                    SharePreUtil.putString(getApplicationContext(), "IP", ip);
//                    SharePreUtil.putString(getApplicationContext(), "WS", ws);
////                    SpUtil.put(CommonConfig.IP, "https://soure.exness-api.com");
////                    SpUtil.put(CommonConfig.WS, "ws://soure.exness-api.com/ws");
////                    {"IP": "https://source.cbotapi.com","WS": "wss://source.cbotapi.com/ws"}
//                    System.out.println("SplashActivity IP:"+ip);
//                    System.out.println("SplashActivity WS:"+ws);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if (SharedPref.getBoolean(Constants.KEY_GOTO_GUIDE, true)) {
//                    GuiderActivity.start(SplashActivity.this);
//                } else {
//                    if (TextUtils.isEmpty(ACache.get(SplashActivity.this).getAsString(BootInitIntentService.KEY_AD))) {
//                        if (Kits.Empty.check(EthereumWalletUtils.getInstance().getWallet(GlobalContext.getAppContext()))) {
//                            CC.obtainBuilder(Wallet.NAME)
//                                    .setContext(SplashActivity.this)
//                                    .setActionName(Wallet.Action.WalletCreateNormalActivity)
//                                    .build().callAsyncCallbackOnMainThread((cc, result) -> {
//                            });
//                        } else {
//                            MainActivity.start(SplashActivity.this, false);
//                        }
//                    } else {
//                        AdActivity.start(SplashActivity.this, AdActivity.KEY_SOURCE_SPLASH);
//                    }
//                }
//                finish();
//            }
//        }).start();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void onBackPressed() {
        HandlerUtil.removeCallbacksAndMessages();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (requestCode == SafeKeyboardVerifyActivity.REQUEST_CODE_SAFE_KEYBOARD_VERIFY) {
                gotoNextPage();
            } else if (requestCode == FingerprintActivity.REQUEST_CODE_VERIFY_FINGER) {
                int result = data.getIntExtra(Constants.KEY_DEFAULT, 0);
                switch (result) {
                    case FingerprintActivity.RETURN_TYPE_OK://指纹登录通过
                        gotoNextPage();
                        break;
                    case FingerprintActivity.RETURN_TYPE_SELECT:  //切换登录方式
                        verifyBySafePassword();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}