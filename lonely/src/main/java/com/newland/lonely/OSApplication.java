package com.newland.lonely;

import android.app.Application;
import android.content.Context;

/**
 * @author lin
 * @version 2018/8/23 0023
 */
public class OSApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        init();
    }

    public static OSApplication getAppContext(){
        return (OSApplication) context;
    }

    private void init() {
        OSSharedPreference.init(this, "osc_update_sp");
    }
}
