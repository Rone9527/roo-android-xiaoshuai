package com.roo.core.app.component;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/4 12:26
 *     desc        : 描述--//Qrcode 二维码组件
 * </pre>
 */

public class Qrcode {

    public static final String NAME = "module_qrcode";

    public static class Param {
        public static final String Text = "text";
    }

    public static class Action {

        public static final String QrcodeScanActivity = "QrcodeScanActivity";

        public static final String BitmapDecode = "BitmapDecode";

        public static final String ContentEncode = "ContentEncode";

    }

    public static class Result {
        public static final String RESULT = "RESULT";

    }
}
