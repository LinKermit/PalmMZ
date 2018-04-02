package com.lin.palmmz.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class Setting {

    private static final String KEY_SOFT_KEYBOARD_HEIGHT = "softKeyboardHeight";



    public static SharedPreferences getSettingPreferences(Context context) {
        return context.getSharedPreferences(Setting.class.getName(), Context.MODE_PRIVATE);
    }

    public static int getSoftKeyboardHeight(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        return sp.getInt(KEY_SOFT_KEYBOARD_HEIGHT, 0);
    }

    public static void updateSoftKeyboardHeight(Context context, int height) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putInt(KEY_SOFT_KEYBOARD_HEIGHT, height);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }
}
