package com.ycbjie.webviewlib.base;

import java.io.Serializable;
import java.util.Map;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/9/10
 *     desc  : 自定义RequestInfo实体类
 *     revise:
 * </pre>
 */
public class RequestInfo implements Serializable {

    public String url;

    public Map<String, String> headers;

    public RequestInfo(String url, Map<String, String> additionalHttpHeaders) {
        this.url = url;
        this.headers = additionalHttpHeaders;
    }

}
