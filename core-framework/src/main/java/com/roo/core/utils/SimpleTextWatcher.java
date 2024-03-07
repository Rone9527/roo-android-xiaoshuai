package com.roo.core.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * author: zheng
 * created on: 2017/3/2 17:52
 * description:
 * version: 1.0
 */

public class SimpleTextWatcher implements TextWatcher {
    private String className;

    public SimpleTextWatcher(String className) {
        this.className = className;
    }

    public SimpleTextWatcher() {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        LogManage.i("ClassName = [" + className + "],>>beforeTextChanged()>>" + "s = [" + s + "], start = [" + start + "], count = [" + count + "], after = [" + after + "]");
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        LogManage.i("ClassName = [" + className + "],>>onTextChanged()>>" + "s = [" + s + "], start = [" + start + "], before = [" + before + "], count = [" + count + "]");
    }

    @Override
    public void afterTextChanged(Editable s) {
        LogManage.i("ClassName = [" + className + "],>>afterTextChanged()>>" + "s = [" + s + "]");
    }
}
