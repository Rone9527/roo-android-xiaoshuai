package com.core.domain.ws;


import androidx.annotation.StringDef;

import com.alibaba.fastjson.JSON;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/*
 * warn：不要删除geter setter 方法：fastjson
 */
public class Arg {
    public static final String SUB = "sub";
    public static final String UNSUB = "unsub";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({SUB, UNSUB})
    public @interface Event {
    }

    private String cmd;

    private List<String> args = new ArrayList<>();

    public Arg(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public Arg addArg(String arg) {
        args.add(arg);
        return this;
    }

    public Arg add(String key, String value) {
        args.add(key);
        args.add(value);
        return this;
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }

}