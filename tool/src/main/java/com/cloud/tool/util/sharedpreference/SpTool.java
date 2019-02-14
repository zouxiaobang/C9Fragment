package com.cloud.tool.util.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.cloud.common.base.Cloud9;
import com.cloud.common.base.ConfigurationKey;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * @Author: xb.zou
 * @Date: 2019/2/12
 * @Desc: to-> SharedPreference 文件存储
 */
public class SpTool {
    public static String SP_NAME = Cloud9.getConfig(ConfigurationKey.SP_NAME);

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        String value = sp.getString(key, defValue);

        return value;
    }

    public static void putStringMap(Context context, Map<String, String> map) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (String key: map.keySet()) {
            String value = map.get(key);
            editor.putString(key, value);
        }
        editor.commit();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        boolean value = sp.getBoolean(key, defValue);

        return value;
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        int value = sp.getInt(key, defValue);

        return value;
    }
}
