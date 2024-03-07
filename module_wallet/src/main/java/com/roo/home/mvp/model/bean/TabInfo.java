package com.roo.home.mvp.model.bean;

import android.os.Bundle;

public class TabInfo {
    public String title;
    public Class  clz;
    public Bundle args;

    public TabInfo(String title, Class clz, Bundle args) {
        this.title = title;
        this.clz = clz;
        this.args = args;
    }
}