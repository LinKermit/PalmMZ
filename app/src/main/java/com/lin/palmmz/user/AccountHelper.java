package com.lin.palmmz.user;

import android.app.Application;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;

import com.lin.palmmz.net.bean.User;
import com.lin.palmmz.util.Constants;
import com.lin.palmmz.util.SharedPreferencesHelper;
import com.lin.palmmz.util.LogUtils;

/**
 * Created by lin on 2017/11/19.
 * 账户辅助类，
 * 用于更新用户信息和保存当前账户等操作
 */

public class AccountHelper {
    private static final String TAG = AccountHelper.class.getName();

    private Application application;
    private static AccountHelper accountHelper;

    private  User user;

    private AccountHelper(Application application){//构造器私有化
        this.application = application;
    }

    public static void init(Application application){//全局初始化
        if (accountHelper == null){
            accountHelper = new AccountHelper(application);
            LogUtils.e(TAG,accountHelper+"AccountHelper已经初始化");
        }else {
            accountHelper.user = (User) SharedPreferencesHelper.readObject(accountHelper.application,SharedPreferencesHelper.USER_KEY);
            LogUtils.d(TAG,accountHelper.user.getAccess_token()+"获取User.access_token");
        }
    }

    /**
     * 从本地获取user对象
     * @return
     */
    public synchronized static User getUser() {
        if (accountHelper == null){
            return new User();
        }
        if (accountHelper.user != null){
            //从本地加载
            accountHelper.user = (User) SharedPreferencesHelper.readObject(accountHelper.application,SharedPreferencesHelper.USER_KEY);
        }
//            if (accountHelper.user!=null){
//                LogUtils.e(TAG,accountHelper.user.getAccess_token()+"获取本地User成功");
//            }
        if (accountHelper.user == null){
            accountHelper.user = new User();//第一次初始化
        }
        return accountHelper.user;
    }


    /**
     * 判断是否登录
     * @return
     */
    public static boolean isLogin() {
        return getUser().getUid()>0 && !TextUtils.isEmpty(getUser().getAccess_token());
    }

    public static boolean updateUser(User user){
        if (user == null){
            return false;
        }

        if (!TextUtils.isEmpty(user.getAccess_token())){
            accountHelper.user = user;
        }
        return SharedPreferencesHelper.saveObject(accountHelper.application,user,SharedPreferencesHelper.USER_KEY);
    }

    public static void clearUser(){
        accountHelper.user = null;
        SharedPreferencesHelper.remove(accountHelper.application);
    }

    public static void logout(View view,Runnable runnable){
        //清除用户sharedpreference
        clearUser();
        //再去判断是否还存在
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = (User) SharedPreferencesHelper.readObject(accountHelper.application,SharedPreferencesHelper.USER_KEY);
                if (user == null){
                    clearAndPostBroadcast(accountHelper.application);
                }
            }
        },2000);

    }


    /**
     * 当前用户信息清理完成后调用方法清理服务等信息
     *
     * @param application Application
     */
    private static void clearAndPostBroadcast(Application application) {
        // 清理网络相关
//        ApiHttpClient.destroyAndRestore(application);

//        // 用户退出时清理红点并退出服务
//        NoticeManager.clear(application, NoticeManager.FLAG_CLEAR_ALL);
//        NoticeManager.exitServer(application);
//
//        // 清理动弹对应数据
//        CacheManager.deleteObject(application, TweetFragment.CACHE_USER_TWEET);

        // Logout 广播
        Intent intent = new Intent(Constants.INTENT_ACTION_LOGOUT);
        LocalBroadcastManager.getInstance(application).sendBroadcast(intent);

    }
}
