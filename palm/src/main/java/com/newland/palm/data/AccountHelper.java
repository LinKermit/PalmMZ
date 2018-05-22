package com.newland.palm.data;

import android.app.Application;
import android.text.TextUtils;

import com.newland.palm.data.bean.User;

/**
 * @author lin
 * @version 2018/5/18 0018
 */
public class AccountHelper {

    private static final String TAG = "AccountHelper";

    private Application application;
    private static AccountHelper helper;
    private User user;

    public AccountHelper(Application application) {
        this.application = application;
    }

    public static void init(Application application){
        if (helper == null){
            helper = new AccountHelper(application);
        }else {
            helper.user = (User) SpHelper.readObject(application,SpHelper.USER_KEY);
        }
    }

    public synchronized static User getUser(){
        if (helper == null){
            return new User();
        }

        //本地取user，如果还是为null，new User()
        if (helper.user == null){
            helper.user = (User) SpHelper.readObject(helper.application,SpHelper.USER_KEY);
        }
        if (helper.user == null){
            return new User();
        }
        return helper.user;
    }

    /**
     * 判断是否登录
     * @return
     */
    public static boolean isLogin(){
        return getUser().getUid()>0 && !TextUtils.isEmpty(getUser().getAccess_token());
    }

    public static void clearUser(){
        helper.user = null;
        SpHelper.remove(helper.application);
    }
}
