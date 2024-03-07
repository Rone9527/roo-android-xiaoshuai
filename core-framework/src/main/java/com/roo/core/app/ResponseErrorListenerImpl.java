package com.roo.core.app;

import android.content.Context;
import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.roo.core.model.exception.ApiException;
import com.roo.core.utils.LogManage;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import retrofit2.HttpException;

/**
 * ================================================
 * 展示 {@link ResponseErrorListener} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ResponseErrorListenerImpl implements ResponseErrorListener {

    @Override
    public void handleResponseError(Context context, Throwable t) {
        String msg = "未知错误!";

        if (t instanceof ApiException) {
            if (!"系统错误".equals(t.getMessage())) {
                //ToastUtils.show(I18nManger.getLanguage(t));
            }
        } else if (t instanceof UnknownHostException) {
            msg = "暂无网络!";
            //ToastUtils.show(msg);
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时!";
            //ToastUtils.show(msg);
        } else if (t instanceof HttpException) {
            msg = convertStatusCode((HttpException) t);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException) {
            msg = "数据解析错误!";
        }
        LogManage.i(">>handleResponseError()>>Catch-Error>>" + "msg = [" + msg + "], t = [" + t + "]");
    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() >= 500) {
            msg = "服务器发生错误!";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在!";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝!";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面!";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
