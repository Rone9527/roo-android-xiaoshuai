package com.roo.core.model.exception;

/**
 * <pre>
 *     project name: xuetu_android
 *     author      : 李琼
 *     create time : 2018/8/14 下午1:04
 *     desc        : 描述--//ApiException 业务异常
 * </pre>
 */
public class ApiException extends RuntimeException {
    private int resultCode;
    private String url;
    private String message;

    public ApiException(String message, int resultCode, String url) {
        super(message);
        this.message = message;
        this.resultCode = resultCode;
        this.url = url;
    }

    public ApiException(String message, int resultCode) {
        super(message);
        this.message = message;
        this.resultCode = resultCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getResultCode() {
        return resultCode;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "resultCode=" + resultCode +
                "message=" + message +
                "url=" + url +
                '}';
    }
}
