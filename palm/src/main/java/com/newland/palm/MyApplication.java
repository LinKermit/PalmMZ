package com.newland.palm;

import android.app.Application;
import android.content.Context;

import com.newland.palm.data.AccountHelper;

/**
 * @author lin
 * @version 2018/5/18 0018 9012
 */
public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        AccountHelper.init(this);
    }

    public static Context getAppContext(){
        return mContext;
    }
}
