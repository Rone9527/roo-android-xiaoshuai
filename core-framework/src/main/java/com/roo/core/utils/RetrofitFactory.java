package com.roo.core.utils;

import com.roo.core.app.GlobalContext;
import com.jess.arms.utils.ArmsUtils;

import okhttp3.OkHttpClient;

/**
 * Created by TZT on 2017/2/27.
 */

public class RetrofitFactory {

    public static <T> T getRetrofit(Class<T> service) {

        return ArmsUtils.obtainAppComponentFromContext(GlobalContext.getAppContext()).repositoryManager().obtainRetrofitService(service);
    }

    public static <T> T getCache(Class<T> service) {
        return ArmsUtils.obtainAppComponentFromContext(GlobalContext.getAppContext()).repositoryManager().obtainCacheService(service);
    }

    public static OkHttpClient getOkHttpClient() {
        return ArmsUtils.obtainAppComponentFromContext(GlobalContext.getAppContext()).okHttpClient();
    }

}
