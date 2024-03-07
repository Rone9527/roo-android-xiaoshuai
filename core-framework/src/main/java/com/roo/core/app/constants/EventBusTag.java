package com.roo.core.app.constants;

/**
 * <pre>
 *     project name: xuetu_android
 *     author      : 李琼
 *     create time : 2019/1/14 1:57 PM
 *     desc        : 描述--//GlobalEventBusTag
 * </pre>
 */

public class EventBusTag {
    public static final String DATA_TICKER = "DATA_TICKER";

    /*没有任何意义，只用于eventbus的object*/
    public static final String NULL_EVENT = "NULL_EVENT";
    /*重连事件*/
    public static final String RECONNECT_EVENT = "RECONNECT_EVENT_INDEX";
    /*打开网页*/
    public static final String GOTO_WEBVIEW = "GOTO_WEBVIEW";

    public static final String GOTO_DEX = "GOTO_DEX";
    /*网页返回事件*/
    public static final String EVENT_WEBVIEW = "EVENT_WEBVIEW";

    public static final String GET_BALANCE = "REFRESH_BALANCE";

    public static final String LOADED_BALANCE = "LOADED_BALANCE";

    public static final String LEGAL_CHANGED = "LEGAL_CHANGED";

    public static final String GOTO_BACK = "GOTO_BACK";

    public static final String UPLOAD_JPUSH = "UPLOAD_JPUSH";

    /*点击答案选项*/
    public static final String CHECK_OPTION = "CHECK_OPTION";
}
