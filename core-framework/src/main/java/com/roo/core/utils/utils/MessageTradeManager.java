package com.roo.core.utils.utils;

import android.text.TextUtils;

import com.core.domain.mine.MessageTrade;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiameng.mmkv.SharedPref;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/13 18:12
 *     desc        : 描述--//NoticeManager
 * </pre>
 */

public class MessageTradeManager {
    public static final String KEY_NOTICE = "KEY_NOTICE";

    private MessageTradeManager() {

    }

    public static MessageTradeManager getInstance() {
        return Holder.instantce;
    }

    private static class Holder {
        static MessageTradeManager instantce = new MessageTradeManager();
    }

    public void removeNotice(String id) {
        List<MessageTrade> noticeList = getNoticeList();
        Iterator<MessageTrade> iterator = noticeList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
            }
        }
        SharedPref.putString(KEY_NOTICE, new Gson().toJson(noticeList));
    }


    public boolean hasNoticeNotRead() {
        for (MessageTrade messageTrade : getNoticeList()) {
            if (!messageTrade.isReadStatus()) {
                return true;
            }
        }
        return false;
    }

    public void setNoticeRead(String id) {
        List<MessageTrade> noticeList = getNoticeList();
        for (MessageTrade messageTrade : noticeList) {
            if (messageTrade.getId().equals(id)) {
                messageTrade.setReadStatus(true);
                break;
            }
        }
        SharedPref.putString(KEY_NOTICE, new Gson().toJson(noticeList));
    }
    public boolean hasUnReadNotice() {
        List<MessageTrade> noticeList = getNoticeList();
        for (MessageTrade messageTrade : noticeList) {
            if (!messageTrade.isReadStatus()) {
                return true;
            }
        }
        return false;
    }

    public void resetNoticeRead() {
        List<MessageTrade> noticeList = getNoticeList();
        for (MessageTrade messageTrade : noticeList) {
            messageTrade.setReadStatus(true);
        }
        SharedPref.putString(KEY_NOTICE, new Gson().toJson(noticeList));
    }

    public MessageTrade getNoticeById(String id) {
        List<MessageTrade> noticeList = getNoticeList();
        for (MessageTrade MessageTrade : noticeList) {
            if (MessageTrade.getId().equals(id)) {
                return MessageTrade;
            }
        }
        return null;
    }

    public List<MessageTrade> getNoticeList() {
        String json = SharedPref.getString(KEY_NOTICE);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(json, new TypeToken<List<MessageTrade>>() {
        }.getType());
    }

    public void addNotice(MessageTrade notice) {
        MessageTrade noticeById = getNoticeById(notice.getId());
        if (noticeById != null) {
            removeNotice(notice.getId());
        }
        List<MessageTrade> noticeList = getNoticeList();
        noticeList.add(0, notice);
        SharedPref.putString(KEY_NOTICE, new Gson().toJson(noticeList));
    }

    public void replaceNotice(MessageTrade notice) {
        List<MessageTrade> noticeList = getNoticeList();
        for (int i = 0; i < noticeList.size(); i++) {
            MessageTrade MessageTrade = noticeList.get(i);
            if (MessageTrade.getId().equals(notice.getId())) {
                noticeList.set(i, notice);
                SharedPref.putString(KEY_NOTICE, new Gson().toJson(noticeList));
                break;
            }
        }
    }

}
