package com.lin.palmmz;

import android.content.Context;

import com.lin.palmmz.user.update.UpdateSharedPreference;
import com.lin.palmmz.user.AccountHelper;

/**
 * Created by lin on 2017/11/16.
 */

public class MyApplication extends AppContext {

    private static Context context;
    private final String UPDATE_SHARED_NAME = "palm_update";
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        init();
    }

    private void init() {

        //初始化账号基本信息
        AccountHelper.init(this);
        UpdateSharedPreference.init(this,UPDATE_SHARED_NAME);
    }

    public static Context getAppInstance(){
        return context;
    }
}
