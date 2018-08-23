package com.newland.lonely;

import android.content.Context;

/**
 * @author lin
 * @version 2018/8/23 0023
 */
public class OSSharedPreference extends SharedPreferenceUtil {

    private static OSSharedPreference mInstance;

    public static void init(Context context,String name){
        if (mInstance == null){
            mInstance = new OSSharedPreference(context,name);
        }
    }

    private OSSharedPreference(Context context, String name) {
        super(context, name);
    }

    public static OSSharedPreference getInstance() {
        return mInstance;
    }

    /**
     * 第一次安装
     */
    public void putFirstInstall() {
        put("osc_first_install", false);
    }

    /**
     * 第一次安装
     */
    public boolean isFirstInstall() {
        return getBoolean("osc_first_install", true);
    }
}
