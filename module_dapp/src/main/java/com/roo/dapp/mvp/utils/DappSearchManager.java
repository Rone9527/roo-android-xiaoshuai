package com.roo.dapp.mvp.utils;

import android.text.TextUtils;

import com.core.domain.dapp.DappHistoryBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiameng.mmkv.SharedPref;
import com.roo.dapp.mvp.ui.adapter.DappSearchAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/13 18:12
 *     desc        : 描述--//DappSearchManager
 * </pre>
 */

public class DappSearchManager {
    public static final String DAPP_SEARCH = "DAPP_SEARCH";

    private DappSearchManager() {

    }

    public static DappSearchManager getInstance() {
        return Holder.instantce;
    }

    private static class Holder {
        static DappSearchManager instantce = new DappSearchManager();
    }

    public void remove(String name) {
        List<DappHistoryBean> select = select();
        Iterator<DappHistoryBean> iterator = select.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(name)) {
                iterator.remove();
            }
        }
        SharedPref.putString(DAPP_SEARCH, new Gson().toJson(select));
    }

    public DappHistoryBean select(String keyword) {
        for (DappHistoryBean res : select()) {
            if (res.getName().equals(keyword)) {
                return res;
            }
        }
        return null;
    }

    public void remove() {
        SharedPref.remove(DAPP_SEARCH);
    }

    public List<DappHistoryBean> select() {
        String json = SharedPref.getString(DAPP_SEARCH);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(json, new TypeToken<List<DappHistoryBean>>() {
        }.getType());
    }

    public void add(String keyword) {

        DappHistoryBean dappHistoryBean = DappHistoryBean.valueOf(keyword,
                DappSearchAdapter.TYPE_HISTORY_CONTENT);

        DappHistoryBean item = select(keyword);
        if (item != null) {
            remove(dappHistoryBean.getName());
        }
        List<DappHistoryBean> select = select();
        select.add(0, dappHistoryBean);
        SharedPref.putString(DAPP_SEARCH, new Gson().toJson(select));
    }

}
