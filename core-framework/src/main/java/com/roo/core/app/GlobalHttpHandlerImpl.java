package com.roo.core.app;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.jess.arms.http.GlobalHttpHandler;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.model.exception.ApiException;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LanguageUtil;
import com.roo.core.utils.LogManage;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 展示 {@link GlobalHttpHandler} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {

    private final Gson mGson;
    private final String versionName;

    public GlobalHttpHandlerImpl() {
        mGson = new Gson();
        versionName = Kits.Package.getVersionName(GlobalContext.getAppContext());
    }

    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        String url = response.request().url().toString();
        if (!(url.contains(".jpeg") || url.contains(".svg") || url.contains(".jpg") || url.contains(".png"))) {//不打印图片的日志
            LogManage.i(">>onHttpResultResponse()>>" + "response = [" + response + "], httpResult = [" + httpResult + "]");
            if (url.startsWith(GlobalConstant.BASE_URL)) {

                try {
                    JSONObject jsonObject = new JSONObject(httpResult);
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        BaseResponse result = mGson.fromJson(httpResult, BaseResponse.class);
                    }else if (code == 500){
                        ApiException apiException = new ApiException("未知错误", 1001);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//
//
//                    if (result == null || 200 != result.getCode()) {
//                        ApiException apiException = new ApiException(result.getMessage(), result.getCode());
//                        LogManage.d(apiException.toString() + " url:" + url);
//                        throw apiException;
//                    }
//                } catch (Exception e) {
////                    ApiException apiException = new ApiException("未知错误", 1001);
////                    LogManage.d(apiException.toString() + " url:" + url);
//                    throw e;
//                }
            }
        }
        return response;
    }

    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
        LogManage.i(">>onHttpRequestBefore()>> request = [" + request.toString() + "]");
        Request.Builder builder = chain.request().newBuilder();

        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        Request.Builder header = builder.url(urlBuilder.build())
                .header("client", "android," + android.os.Build.MODEL)
                .header("Accept-Language", LanguageUtil.getLang() + "");

        String token = SharedPref.getString(Constants.KEY_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            header.header("authorization", token);
        }
        header.header("via", "android");
        if (!TextUtils.isEmpty(versionName)) {
            header.header("version", versionName);
        }
        return builder.build();
    }
}
