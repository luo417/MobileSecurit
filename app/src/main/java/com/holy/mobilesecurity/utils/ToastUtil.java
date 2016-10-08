package com.holy.mobilesecurity.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Holy on 2016/4/30.
 */
public class ToastUtil {
    public static void show(Context context, String string) {
        Toast.makeText(context, string ,0).show();
    }
}
