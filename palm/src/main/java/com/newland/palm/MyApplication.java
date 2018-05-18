package com.newland.palm;

import android.app.Application;
import android.content.Context;

/**
 * @author lin
 * @version 2018/5/18 0018
 */
public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }

    public static Context getAppContext(){
        return mContext;
    }
}
