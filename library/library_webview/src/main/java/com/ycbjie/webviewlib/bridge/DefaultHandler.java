package com.ycbjie.webviewlib.bridge;


import com.ycbjie.webviewlib.inter.BridgeHandler;
import com.ycbjie.webviewlib.inter.CallBackFunction;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/9/10
 *     desc  : 默认的BridgeHandler
 *     revise:
 * </pre>
 */
public class DefaultHandler implements BridgeHandler {

    @Override
    public void handler(String data, CallBackFunction function) {
        if (function != null) {
            function.onCallBack("DefaultHandler response data");
        }
    }

}
