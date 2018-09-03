package com.newland.lonely.data;

import android.app.Application;
import android.text.TextUtils;

import com.newland.lonely.data.bean.User;
import com.newland.lonely.operator.SpHelper;

/**
 * @author lin
 * @version 2018/8/28 0028
 */
public class AccountHelper {

    private static AccountHelper instance;
    private Application application;
    private User user;

    private AccountHelper(Application application){
        this.application = application;
    }

    public static void init(Application application){
        if (instance == null){
            instance = new AccountHelper(application);
        }else {
            instance.user = (User) SpHelper.readObject(instance.application,SpHelper.USER_KEY);
        }
    }

    public synchronized static User getUser(){
        if (instance == null){
            return new User();
        }
        if (instance.user == null){
            instance.user = (User) SpHelper.readObject(instance.application,SpHelper.USER_KEY);
        }
        if (instance.user == null){
            instance.user = new User();
        }
        return instance.user;
    }

    public static boolean isLogin(){
        return getUser().getUid() > 0 && !TextUtils.isEmpty(getUser().getAccess_token());
    }
}
