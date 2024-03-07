package com.core.domain.trade;


import androidx.annotation.StringDef;

import com.alibaba.fastjson.JSON;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

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

    public static final String TOPIC = "topic";
    public static final String CMD = "cmd";

    public static String toJSONString(HashMap<String,String> map) {
        return JSON.toJSONString(map);
    }

}