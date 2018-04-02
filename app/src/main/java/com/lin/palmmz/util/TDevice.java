package com.lin.palmmz.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.lin.palmmz.AppContext;
import com.lin.palmmz.MyApplication;

import static com.lin.palmmz.MyApplication.getAppInstance;

/**
 * Created by lin on 2017/11/23.
 */

public class TDevice {

    public static boolean hasInternet() {
        ConnectivityManager cm = (ConnectivityManager) getAppInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnected();
    }


    public static DisplayMetrics getDisplayMetrics() {
        return MyApplication.getAppInstance().getResources().getDisplayMetrics();
    }


    /**
     * Change SP to PX
     *
     * @param resources Resources
     * @param sp        SP
     * @return PX
     */
    public static float spToPx(Resources resources, float sp) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }


    /**
     * Change Dip to PX
     *
     * @param resources Resources
     * @param dp        Dip
     * @return PX
     */
    public static float dipToPx(Resources resources, float dp) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public static float dp2px(float dp) {
        return dp * getDisplayMetrics().density;
    }

    public static float px2dp(float px) {
        return px / getDisplayMetrics().density;
    }

    /**
     * 获取屏幕宽高
     * @return
     */
    public static float getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    public static float getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }


    /**
     * 获取系统版本
     * @return
     */
    public static String getVersionName() {
        try {
            return AppContext.getInstance()
                    .getPackageManager()
                    .getPackageInfo(AppContext.getInstance().getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            return "undefined version name";
        }
    }

    public static boolean isWifiOpen() {
        ConnectivityManager cm = (ConnectivityManager) getAppInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {return false;}
        if (!info.isAvailable() || !info.isConnected()){ return false;}
        if (info.getType() != ConnectivityManager.TYPE_WIFI) {return false;}
        return true;
    }
}
