package com.roo.dapp.mvp.utils.autoWeb3;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.FragmentActivity;

import com.roo.core.utils.ToastUtils;
import com.roo.dapp.R;
import com.roo.dapp.mvp.interfaces.UrlHandler;
import com.roo.dapp.mvp.utils.JsInjectorClient;
import com.roo.dapp.mvp.utils.JsInjectorResponse;
import com.roo.dapp.mvp.utils.UrlHandlerManager;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.N;

public class Web3ViewClient extends WebViewClient {

    private final Object lock = new Object();

    private final JsInjectorClient jsInjectorClient;
    private final UrlHandlerManager urlHandlerManager;

    private Activity context;

    private boolean isInjected;

    public Web3ViewClient(JsInjectorClient jsInjectorClient, UrlHandlerManager urlHandlerManager) {
        this.jsInjectorClient = jsInjectorClient;
        this.urlHandlerManager = urlHandlerManager;
    }

    public void addUrlHandler(UrlHandler urlHandler) {
        urlHandlerManager.add(urlHandler);
    }

    public void removeUrlHandler(UrlHandler urlHandler) {
        urlHandlerManager.remove(urlHandler);
    }

    public JsInjectorClient getJsInjectorClient() {
        return jsInjectorClient;
    }

    /**
     * shouldOverrideUrlLoading
     * <p>
     * 当加载的网页需要重定向的时候就会回调这个函数告知我们应用程序是否需要接管控制网页加载，如果应用程序接管，
     * 并且return true意味着主程序接管网页加载，如果返回false让webview自己处理。
     * </p>
     * 参数说明：
     *
     * @param view 接收WebViewClient的那个实例，前面看到webView.setWebViewClient(new
     *             MyAndroidWebViewClient())，即是这个webview。
     * @param url  即将要被加载的url
     * @return true 当前应用程序要自己处理这个url， 返回false则不处理。 注："post"请求方式不会调用这个回调函数
     */

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return shouldOverrideUrlLoading(view, url, false, false);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (request == null || view == null) {
            return false;
        }
        String url = request.getUrl().toString();
        boolean isMainFrame = request.isForMainFrame();
        boolean isRedirect = SDK_INT >= N && request.isRedirect();
        return shouldOverrideUrlLoading(view, url, isMainFrame, isRedirect);
    }

    public boolean didInjection() {
        return isInjected;
    }
    private boolean shouldOverrideUrlLoading(WebView webView, String url, boolean isMainFrame, boolean isRedirect) {
        boolean result = false;
        synchronized (lock) {
            isInjected = false;
        }
        String urlToOpen = urlHandlerManager.handle(url);
        //manually handle trusted intents
        if (handleTrustedApps(url)) {
            return true;
        }
        //如果不是http开头的自己处理
        if (!url.startsWith("http")) {
            result = true;
        }
        //获取请求是否是为了获取主框架的文档  &&  获取请求是否是服务器端重定向的结果
        if (isMainFrame && isRedirect) {
            urlToOpen = url;
            result = true;
        }

        if (result && !TextUtils.isEmpty(urlToOpen)) {
            webView.loadUrl(urlToOpen);
        }
        return result;
    }

    /**
     * shouldInterceptRequest
     * 通知应用程序内核即将加载request制定的资源，应用程序可以返回本地的资源提供给内核，若本地处理返回数据，内核不从网络上获取数据。
     * <p>
     * 参数说明：
     *
     * @param view    接收WebViewClient的那个实例，前面看到webView.setWebViewClient(new
     *                MyAndroidWebViewClient())，即是这个webview。
     * @param request WebResourceRequest url 制定的资源
     * @return 返回WebResourceResponse包含数据对象，或者返回null
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (request == null) {
            return null;
        } else if (request.getUrl().toString().contains(".auth0.com/")) //don't inject into auth0 pages
        {
            return super.shouldInterceptRequest(view, request);
        } else if (!request.getMethod().equalsIgnoreCase("GET") || !request.isForMainFrame()) {
            if (request.getMethod().equalsIgnoreCase("GET")
                    && (request.getUrl().toString().contains(".js")
                    || request.getUrl().toString().contains("json")
                    || request.getUrl().toString().contains("css"))) {
                synchronized (lock) {
                    if (!isInjected) {
                        injectScriptFile(view);
                        isInjected = true;
                    }
                }
            }
            super.shouldInterceptRequest(view, request);
            return null;
        }
        //check for known extensions
        else if (handleTrustedExtension(request.getUrl().toString())) {
            return null;
        }

        HttpUrl httpUrl = HttpUrl.parse(request.getUrl().toString());
        if (httpUrl == null) {
            return null;
        }
        Map<String, String> headers = request.getRequestHeaders();

        JsInjectorResponse response;
        try {
            response = jsInjectorClient.loadUrl(httpUrl.toString(), headers);
        } catch (Exception ex) {
            return null;
        }
        if (response == null || response.isRedirect) {
            return null;
        } else if (TextUtils.isEmpty(response.data)) {
            return null;
        } else {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(response.data.getBytes());
            WebResourceResponse webResourceResponse = new WebResourceResponse(
                    response.mime, response.charset, inputStream);
            synchronized (lock) {
                isInjected = true;
            }
            return webResourceResponse;
        }
    }

    void injectScriptFile(WebView view) {
        String js = jsInjectorClient.assembleJs(view.getContext(), "%1$s%2$s");
        byte[] buffer = js.getBytes();
        String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);

        view.post(() -> view.loadUrl("javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var script = document.createElement('script');" +
                "script.type = 'text/javascript';" +
                // Tell the browser to BASE64-decode the string into your script !!!
                "script.innerHTML = window.atob('" + encoded + "');" +
                "parent.appendChild(script)" +
                "})()"));
    }

    @Override
    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {

    }

    public void onReload() {
        synchronized (lock) {
            isInjected = false;
        }
    }

    public void setActivity(FragmentActivity activity) {
        this.context = activity;
    }

    //Handling of trusted apps
    private boolean handleTrustedApps(String url) {
        //get list
        String[] strArray = context.getResources().getStringArray(R.array.TrustedApps);
        for (String item : strArray) {
            String[] split = item.split(",");
            if (url.startsWith(split[1])) {
                intentTryApp(split[0], url);
                return true;
            }
        }

        return false;
    }

    private boolean handleTrustedExtension(String url) {
        String[] strArray = context.getResources().getStringArray(R.array.TrustedExtensions);
        for (String item : strArray) {
            String[] split = item.split(",");
            if (url.endsWith(split[1])) {
                useKnownOpenIntent(split[0], url);
                return true;
            }
        }

        return false;
    }

    private void intentTryApp(String appId, String msg) {
        final boolean isAppInstalled = isAppAvailable(appId);
        if (isAppInstalled) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW);
            myIntent.setPackage(appId);
            myIntent.setData(Uri.parse(msg));
            myIntent.putExtra(Intent.EXTRA_TEXT, msg);
            context.startActivity(myIntent);
        } else {
            ToastUtils.show("Required App not Installed");
        }
    }

    private void useKnownOpenIntent(String type, String url) {
        Intent openIntent = new Intent(Intent.ACTION_VIEW);
        openIntent.setDataAndType(Uri.parse(url), type);
        Intent intent = Intent.createChooser(openIntent, "Open using");
        if (isIntentAvailable(intent)) {
            context.startActivity(intent);
        } else {
            ToastUtils.show("Required App not Installed");
        }
    }

    private boolean isIntentAvailable(Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private boolean isAppAvailable(String appName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    public void resetInject() {
        isInjected = false;
    }
}