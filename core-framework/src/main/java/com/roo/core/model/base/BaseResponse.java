package com.roo.core.model.base;


/**
 * description:所有http请求的非列表数据的实体类基类,父类(本类)处理业务逻辑,子类封装数据
 * version: 1.0
 */

public class BaseResponse<T> {

    private int code;
    private String message;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
