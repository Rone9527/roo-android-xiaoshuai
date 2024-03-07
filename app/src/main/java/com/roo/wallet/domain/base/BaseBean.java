package com.roo.wallet.domain.base;

/**
 * Created by TZT on 2017/2/27.
 */

public class BaseBean {

    private String msg;
    private int code;

    public BaseBean(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public BaseBean() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "showMsg='" + msg + '\'' + ", innerCode=" + code;
    }
}
