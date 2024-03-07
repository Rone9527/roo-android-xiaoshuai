package com.ycbjie.webviewlib.inter;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/9/10
 *     desc  : js接口
 *     revise: demo地址：https://github.com/yangchong211/YCWebView
 * </pre>
 */
public interface WebViewJavascriptBridge {

    void callHandler(String handlerName);

    void callHandler(String handlerName, String data);

    void callHandler(String handlerName, String data, CallBackFunction callBack);

    void unregisterHandler(String handlerName);

    void registerHandler(String handlerName, BridgeHandler handler);

}
