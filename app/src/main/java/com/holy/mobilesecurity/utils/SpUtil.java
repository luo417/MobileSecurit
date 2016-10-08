package com.holy.mobilesecurity.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Holy on 2016/5/1.
 */
public class SpUtil {

    public static SharedPreferences sPrefs;

    public static void putBoolean(Context context, String key, boolean value){
        if(sPrefs == null){
            sPrefs = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sPrefs.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue){
        if(sPrefs == null){
            sPrefs = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sPrefs.getBoolean(key, defValue);
    }

    public static void putString(Context context, String key, String value){
        if(sPrefs == null){
            sPrefs = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sPrefs.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defValue){
        if(sPrefs == null){
            sPrefs = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sPrefs.getString(key, defValue);
    }

    public static void remove(Context context, String key){
        if(sPrefs == null){
            sPrefs = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sPrefs.edit().remove(key).commit();
    }
}
