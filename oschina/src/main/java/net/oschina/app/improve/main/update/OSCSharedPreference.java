package net.oschina.app.improve.main.update;

import android.content.Context;

/**
 * 应用存储
 * Created by huanghaibin on 2017/6/27.
 */
@SuppressWarnings("unused")
public final class OSCSharedPreference extends SharedPreferenceUtil {

    private static OSCSharedPreference mInstance;

    public static void init(Context context, String name) {
        if (mInstance == null) {
            mInstance = new OSCSharedPreference(context, name);
        }
    }

    public static OSCSharedPreference getInstance() {
        return mInstance;
    }

    private OSCSharedPreference(Context context, String name) {
        super(context, name);
    }

    /**
     * 点击更新过的版本
     */
     void putUpdateVersion(int code) {
        put("osc_update_code", code);
    }

    /**
     * 设置更新过的版本
     */
    public int getUpdateVersion() {
        return getInt("osc_update_code", 0);
    }

    /**
     * 设置不弹出更新
     */
    public void putShowUpdate(boolean isShow) {
        put("osc_update_show", isShow);
    }

    /**
     * 是否弹出更新
     * 或者是新版本重新更新 259200000
     */
     boolean isShowUpdate() {
        return getBoolean("osc_update_show", true);
    }

    /**
     * 是否已经弹出更新
     * @return 不弹出更新代表已经更新
     */
    public boolean hasShowUpdate() {
        return !getBoolean("osc_update_show", true);
    }
}
