package com.roo.core.app.component;

public class Upgrade {

    public static final String NAME = "module_upgrade";

    public static class Param {

        public static final String PACKAGE_NAME = "PACKAGE_NAME";

        public static final String BUILD_TYPE = "BUILD_TYPE";

        public static final String VERSION_NAME = "VERSION_NAME";

        //保存忽略的版本 | 已经忽略的版本是否弹出提示
        public static final String NEVER_IGNORE = "NEVER_IGNORE";

        //直接下载apk
        public static final String DOWNLOAD_DIRECTLY = "DOWNLOAD_DIRECTLY";

        //作为下载功能
        public static final String DOWNLOAD = "DOWNLOAD";

        public static final String DOWNLOAD_URL = "DOWNLOAD_URL";
    }

    public static class Action {

        public static final String versionNew = "versionNew";

        public static final String check = "check";

        public static final String download = "download";

    }

    public static class Result {
        public static final String RESULT = "RESULT";
    }

}