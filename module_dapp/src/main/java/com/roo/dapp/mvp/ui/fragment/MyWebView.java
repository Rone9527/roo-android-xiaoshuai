package com.roo.dapp.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MyWebView extends WebView {

    Context mContext;

    public MyWebView(Context paramContext) {
        super(paramContext);
        this.mContext = paramContext;
        init();
    }

    public MyWebView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.mContext = paramContext;
        init();
    }

    public MyWebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        this.mContext = paramContext;
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        WebSettings localWebSettings = getSettings();
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        localWebSettings.setMediaPlaybackRequiresUserGesture(false);
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        localWebSettings.setDomStorageEnabled(true);
        localWebSettings.setLoadWithOverviewMode(true);
        localWebSettings.setBlockNetworkImage(false);
        localWebSettings.setUseWideViewPort(true);

//        localWebSettings.setLoadsImagesAutomatically(true);
//        localWebSettings.setSaveFormData(true);
//        localWebSettings.setSavePassword(false);
//        localWebSettings.setPluginState(PluginState.ON);
//        localWebSettings.setAllowUniversalAccessFromFileURLs(true);
//        localWebSettings.setSupportZoom(true);
//        localWebSettings.setBuiltInZoomControls(true);
//        localWebSettings.setDisplayZoomControls(false);
//        localWebSettings.setSupportMultipleWindows(false);
//        localWebSettings.setBuiltInZoomControls(true);
//        localWebSettings.setRenderPriority(RenderPriority.HIGH);
//        localWebSettings.setDomStorageEnabled(true);





        try {
            localWebSettings.setAppCachePath(this.mContext.getCacheDir().getAbsolutePath());
            localWebSettings.setAllowFileAccess(true);
            localWebSettings.setAppCacheEnabled(true);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        setDrawingCacheEnabled(true);
        setLongClickable(true);
        setScrollbarFadingEnabled(true);
    }


}