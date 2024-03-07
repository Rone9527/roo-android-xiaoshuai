package com.roo.webview.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aries.ui.view.title.TitleBarView;
import com.core.domain.manager.ChainCode;
import com.huantansheng.easyphotos.utils.bitmap.BitmapUtils;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.dialog.ShareDialog;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LanguageUtil;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.webview.R;
import com.roo.webview.activity.WebViewActivity;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.xyzlf.custom.keyboardlib.SimpleKeyboardListener;
import com.ycbjie.webviewlib.base.X5WebChromeClient;
import com.ycbjie.webviewlib.bridge.DefaultHandler;
import com.ycbjie.webviewlib.inter.InterWebListener;
import com.ycbjie.webviewlib.view.X5WebView;
import com.ycbjie.webviewlib.widget.WebProgress;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;

/**
 * <pre>
 *     project name: client-android
 *     author      : 李琼
 *     create time : 2019-09-29 09:45
 *     desc        : 描述--//WebFragment
 * </pre>
 */
public class WebFragment extends BaseFragment {

    private String mUrl;

    private WebProgress pbProgress;
    private X5WebView mWebView;

    public X5WebView getmWebView() {
        return mWebView;
    }

    public void setmWebView(X5WebView mWebView) {
        this.mWebView = mWebView;
    }

    private final InterWebListener interWebListener = new InterWebListener() {
        @Override
        public void hindProgressBar() {
            pbProgress.hide();
        }

        @Override
        public void showErrorView(int type) {

        }

        @Override
        public void startProgress(int newProgress) {
            pbProgress.setProgress(newProgress);
        }

        @Override
        public void showTitle(String title) {
            WebViewActivity webViewActivity = (WebViewActivity) requireActivity();
            if (!webViewActivity.isHasDefinedTitle()) {
                TitleBarView titleBarView = webViewActivity.getTitleBarView();
                webViewActivity.getTitleBarView().setTitleMainText(title);

                switch (title) {
                    case "奖品兑换":
                        UserWallet.ChainWallet myChainWallet = null;
                        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(requireActivity());
                        for (UserWallet.ChainWallet chainWallet : userWallet.getChainWallets()) {
                            if (chainWallet.getChainCode().equals(ChainCode.BSC)) {
                                myChainWallet = chainWallet;
                                break;
                            }
                        }
                        String address = Constants.PREFIX_16 + myChainWallet.getWalletFile().getAddress();
                        mUrl = MessageFormat.format("https://wap.roo.top/invation-active/redeem-history/?chainCode=bsc&address={0}&platform=android", address);
                        titleBarView.getLinearLayout(Gravity.END).removeAllViews();
                        titleBarView.addRightAction(titleBarView.new ImageAction(
                                requireActivity().getDrawable(R.drawable.ic_proo_echange_record),
                                v -> {
                                    mWebView.loadUrl(mUrl);
                                }));
                        break;
                    case "邀请好友":
                        titleBarView.getLinearLayout(Gravity.END).removeAllViews();
                        titleBarView.addRightAction(titleBarView.new ImageAction(requireActivity()
                                .getDrawable(R.drawable.ic_invite_share),
                                v -> {
                                    Bitmap bitmap = BitmapUtils.createBitmapFromView(mWebView);
                                    ShareDialog.newInstance(bitmap).setOnClickListener(() -> {
                                    }).show(getFragmentManager(), getClass().getSimpleName());
                                }));
                        break;
                    case "盲盒抽奖":
                        titleBarView.getLinearLayout(Gravity.END).removeAllViews();
                        titleBarView.addRightAction(titleBarView.new ImageAction(requireActivity()
                                .getDrawable(R.drawable.ic_proo_echange_record),
                                v -> {
                                    mWebView.loadUrl("https://wap.roo.top/invation-active/history?type=lottery");
                                }));
                        break;
                    default:
                        titleBarView.getLinearLayout(Gravity.END).removeAllViews();
                        break;
                }
            }
        }
    };


    public static WebFragment newInstance(String url) {
        Bundle args = new Bundle();
        WebFragment fragment = new WebFragment();
        args.putString(Constants.KEY_URL, url);
        fragment.setArguments(args);
        return fragment;
    }


    public boolean onKeyDown(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;//退出网页
            } else {
                requireActivity().finishAfterTransition();
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            //syncCookie(mUrl);
            mWebView.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mWebView != null) {
            mWebView.getSettings().setJavaScriptEnabled(false);
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (mWebView != null) {
                mWebView.stopLoading();
                mWebView.destroy();
                ViewGroup parent = (ViewGroup) mWebView.getParent();
                if (parent != null) {
                    parent.removeView(mWebView);
                }
                mWebView = null;
            }
        } catch (Exception e) {
            Log.e("X5WebViewActivity", e.getMessage());
        }
        super.onDestroy();
    }

    private void syncCookie(String url) {
        try {
            CookieSyncManager.createInstance(getActivity());
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);

            String token = "token=xxx";
            cookieManager.setCookie(url, token);

            String client = "client=android," + android.os.Build.MODEL;
            cookieManager.setCookie(url, client);

            String lang = "lang=" + LanguageUtil.getLang();
            cookieManager.setCookie(url, lang);

            String statusHeight = "statusHeight=" + ArmsUtils.px2sp(getActivity(), DeviceUtils.getStatuBarHeight(getActivity()));
            cookieManager.setCookie(url, statusHeight);

            CookieSyncManager.getInstance().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == X5WebChromeClient.FILE_CHOOSER_RESULT_CODE) {
            mWebView.getX5WebChromeClient().uploadMessage(intent, resultCode);
        } else if (requestCode == X5WebChromeClient.FILE_CHOOSER_RESULT_CODE_5) {
            mWebView.getX5WebChromeClient().uploadMessageForAndroid5(intent, resultCode);
        }
    }

    @JavascriptInterface
    public void initWebViewBridge() {
        mWebView.setDefaultHandler(new DefaultHandler());
        //js调native
        mWebView.registerHandler("keyboard.up.security", (data, function) -> {
            LogManage.i(">>handler()>>" + "data = [" + data + "], function = [" + function + "]");

            //使用安全密码开启
            KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(requireActivity());
            keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {
                @Override
                public void onPasswordComplete(String text) {
                    if (SafePassword.isEquals(text)) {
                        String data = String.format("{\"success\": %s}", System.currentTimeMillis());
                        mWebView.evaluateJavascript("(function(){if(window.native){window.native(" + data + ");}})()");

                        keyboardDialog.dismiss();
                    } else {
                        keyboardDialog.clearPassword();
                        ToastUtils.show(getString(R.string.false_safe_pass));
                    }
                }
            });
            keyboardDialog.show();
            keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_input));
            keyboardDialog.getTipsLayout().setVisibility(View.GONE);
        });

        //js调native
        mWebView.registerHandler("save.photo.wechat", (data, function) -> {
            //保存图片
            Bitmap bitmap = BitmapUtils.createBitmapFromView(mWebView);
            String fileName = "proo-" + Kits.Date.getYmdhms(System.currentTimeMillis());
            File file = Kits.File.savePhoto(fileName, bitmap);
            Kits.File.flushMedia(requireActivity(), file);
            ToastUtils.show(getString(R.string.toast_save_pic_success));
        });
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.layout_webview, container, false);
        mWebView = inflate.findViewById(R.id.web_view);
        setmWebView(mWebView);
        pbProgress = inflate.findViewById(R.id.progress);
        pbProgress.show();
        return inflate;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mUrl = getArguments().getString(Constants.KEY_URL);

        mWebView.getSettings().setTextZoom(100);
        mWebView.getX5WebChromeClient().setWebListener(interWebListener);
        mWebView.getX5WebViewClient().setWebListener(interWebListener);
        mWebView.setBackgroundColor(0); // 设置背景色
        mWebView.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255
        //禁用滑动按钮
        if (mWebView.getX5WebViewExtension() != null) {
            mWebView.getX5WebViewExtension().setHorizontalScrollBarEnabled(false);//水平不显示滚动按钮
            mWebView.getX5WebViewExtension().setVerticalScrollBarEnabled(false); //垂直不显示滚动按钮
        }

        initWebViewBridge();//js交互方法

        LogManage.d(mUrl);
        //syncCookie(mUrl);
        HashMap<String, String> head = new HashMap<>();
        mWebView.loadUrl(mUrl, head);
    }


    @Override
    public void setData(@Nullable Object data) {

    }
}
