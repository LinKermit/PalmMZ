package com.lin.palmmz.user.update;

import android.content.Context;

/**
 * @author Administrator
 * @version 创建时间：2018/1/27 0027 下午 4:51
 */
public class UpdateSharedPreference extends SharedPreferenceUtil {


    public static UpdateSharedPreference mInstance;
    public static UpdateSharedPreference getInstance(){
        return mInstance;
    }


    /**
     * 全局初始化更新sp
     * @param context
     * @param name
     */
    public static void init(Context context,String name){
        if (mInstance == null){
            mInstance = new UpdateSharedPreference(context,name);
        }
    }

    private UpdateSharedPreference(Context context,String name){
        super(context, name);
    }


    /**
     * 更新不再提示
     * @param isShow
     */
    public void putShowUpdate(boolean isShow){
        put("palm_update_show",isShow);
    }
    public boolean getShowUpdate(){
        return getBoolean("palm_update_show",true);
    }


    /**
     * 更新过的版本
     */
    void putUpdateVersion(int code) {
        put("palm_update_code", code);
    }

    /**
     * 设置更新过的版本
     */
    public int getUpdateVersion() {
        return getInt("palm_update_code", 0);
    }
}
