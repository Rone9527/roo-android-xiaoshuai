package cachewebviewlib;

import android.text.TextUtils;

import java.io.IOException;

import cachewebviewlib.config.CacheExtensionConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


class HttpCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String cache = request.header(WebViewCacheInterceptor.KEY_CACHE);
        Response originResponse = chain.proceed(request);
        if (!TextUtils.isEmpty(cache) && cache.equals(CacheType.NORMAL.ordinal() + "")) {
            return originResponse;
        }
        return originResponse.newBuilder().removeHeader("pragma").removeHeader("Cache-Control")
                .header("Cache-Control", "max-age=" + CacheExtensionConfig.maxAge).build();
    }
}
