package com.jiameng.mmkv;

import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mmkv.MMKV;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by wanglei on 2016/11/27.
 */
public class SharedPref {

    public static boolean contains(String key) {
        return MMKV.defaultMMKV().contains(key);
    }

    public static void remove(String key) {
        MMKV.defaultMMKV().remove(key);
    }

    public static void removeAll() {
        MMKV.defaultMMKV().clear();
    }

    public static void putInt(String key, int value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        return MMKV.defaultMMKV().decodeInt(key, defValue);
    }

    public static void putLong(String key, Long value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static long getLong(String key) {
        return getLong(key, 0L);
    }

    public static long getLong(String key, long defValue) {
        return MMKV.defaultMMKV().decodeLong(key, defValue);
    }

    public static void putBoolean(String key, Boolean value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return MMKV.defaultMMKV().decodeBool(key, defValue);
    }

    public static void putString(String key, String value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static String getString(String key) {
        return MMKV.defaultMMKV().decodeString(key, "");
    }

    public static String getString(String key, String defValue) {
        String value = MMKV.defaultMMKV().decodeString(key);
        return TextUtils.isEmpty(value) ? defValue : value;
    }

    public static void putFloat(String key, float value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static float getFloat(String key) {
        return getFloat(key, 0f);
    }

    public static float getFloat(String key, float defValue) {
        return MMKV.defaultMMKV().decodeFloat(key, defValue);
    }

    public static List<String> getStringList(String key) {
        return getStringList(key, null, -1);
    }

    public static List<String> getStringList(String key, int maxCount) {
        return getStringList(key, null, maxCount);
    }

    public static List<String> getStringList(String key, List<String> defaultVal) {
        return getStringList(key, defaultVal, -1);
    }

    public static List<String> getStringList(String key, List<String> defaultVal, int maxCount) {
        String stringSet = MMKV.defaultMMKV().decodeString(key, "");
        if (TextUtils.isEmpty(stringSet)) {
            return defaultVal;
        } else {
            List<String> strings = new Gson().fromJson(stringSet, new TypeToken<List<String>>() {
            }.getType());
            if (maxCount != -1 && strings.size() > maxCount) {
                strings.subList(0, strings.size() - maxCount).clear();
            }
            return strings;
        }
    }

    public static Set<String> getStringSet(String key) {
        return getStringSet(key, null, -1);
    }

    public static Set<String> getStringSet(String key, int maxCount) {
        return getStringSet(key, null, maxCount);
    }

    public static Set<String> getStringSet(String key, Set<String> defaultVal) {
        return getStringSet(key, defaultVal, -1);

    }

    public static Set<String> getStringSet(String key, Set<String> defaultVal, int maxCount) {

        Set<String> stringSet = MMKV.defaultMMKV().decodeStringSet(key, null);
        if (stringSet == null || stringSet.isEmpty()) {
            return defaultVal;
        } else {
            if (maxCount != -1 && stringSet.size() > maxCount) {
                List<String> strings = new ArrayList<>(stringSet);
                strings.subList(0, strings.size() - maxCount).clear();
                return new TreeSet<>(strings);
            } else {
                return stringSet;
            }
        }
    }

    public static void putStringSet(String key, Set<String> value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static void putStringList(String key, List<String> value) {
        MMKV.defaultMMKV().encode(key, new Gson().toJson(value));
    }

    public static void putParcelable(String key, Parcelable object) {
        MMKV.defaultMMKV().encode(key, object);
    }

    public static <T extends Parcelable> T getParcelable(String key, Class<T> tClass) {
        return MMKV.defaultMMKV().decodeParcelable(key, tClass, null);
    }

    public static void putObject(String key, Object object) {
        if (object == null) {
            return;
        }
        if (!(object instanceof Serializable)) {
            throw new RuntimeException("object must implements Serializable!");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            putString(key, new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> T getObject(String key) {
        return getObject(key, null);
    }

    public static <T> T getObject(String key, T obj) {
        if (contains(key)) {
            String objectVal = getString(key, null);
            if (objectVal == null) {
                return obj;
            }
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                Object o = ois.readObject();
                if (o == null) {
                    return obj;
                } else {
                    return (T) o;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    bais.close();
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }
}
