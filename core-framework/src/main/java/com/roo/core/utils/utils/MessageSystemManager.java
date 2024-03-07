package com.roo.core.utils.utils;

import android.text.TextUtils;

import com.core.domain.mine.MessageSystem;
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

public class MessageSystemManager {
    public static final String KEY_MESSAGE = "KEY_MESSAGE";

    private MessageSystemManager() {

    }

    public static MessageSystemManager getInstance() {
        return Holder.instantce;
    }

    private static class Holder {
        static MessageSystemManager instantce = new MessageSystemManager();
    }

    public void removeNotice(String id) {
        List<MessageSystem> noticeList = getNoticeList();
        Iterator<MessageSystem> iterator = noticeList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getMsgId().equals(id)) {
                iterator.remove();
            }
        }
        SharedPref.putString(KEY_MESSAGE, new Gson().toJson(noticeList));
    }


    public boolean hasNoticeNotRead() {
        for (MessageSystem MessageSystem : getNoticeList()) {
            if (!MessageSystem.isReadStatus()) {
                return true;
            }
        }
        return false;
    }

    public void setNoticeRead(String id) {
        List<MessageSystem> noticeList = getNoticeList();
        for (MessageSystem MessageSystem : noticeList) {
            if (MessageSystem.getMsgId().equals(id)) {
                MessageSystem.setReadStatus(true);
                break;
            }
        }
        SharedPref.putString(KEY_MESSAGE, new Gson().toJson(noticeList));
    }
    public boolean hasUnReadNotice() {
        List<MessageSystem> noticeList = getNoticeList();
        for (MessageSystem MessageSystem : noticeList) {
            if (!MessageSystem.isReadStatus()) {
                return true;
            }
        }
        return false;
    }

    public void resetNoticeRead() {
        List<MessageSystem> noticeList = getNoticeList();
        for (MessageSystem MessageSystem : noticeList) {
            MessageSystem.setReadStatus(true);
        }
        SharedPref.putString(KEY_MESSAGE, new Gson().toJson(noticeList));
    }

    public MessageSystem getNoticeById(String id) {
        List<MessageSystem> noticeList = getNoticeList();
        for (MessageSystem MessageSystem : noticeList) {
            if (MessageSystem.getMsgId().equals(id)) {
                return MessageSystem;
            }
        }
        return null;
    }

    public List<MessageSystem> getNoticeList() {
        String json = SharedPref.getString(KEY_MESSAGE);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(json, new TypeToken<List<MessageSystem>>() {
        }.getType());
    }

    public void addNotice(MessageSystem notice) {
        MessageSystem noticeById = getNoticeById(notice.getMsgId());
        if (noticeById != null) {
            removeNotice(notice.getMsgId());
        }
        List<MessageSystem> noticeList = getNoticeList();
        noticeList.add(0, notice);
        SharedPref.putString(KEY_MESSAGE, new Gson().toJson(noticeList));
    }

}
