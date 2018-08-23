package com.newland.lonely;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author lin
 * @version 2018/8/23 0023
 */
public class SharedPreferenceUtil {

    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;

    public SharedPreferenceUtil(Context context, String name){
        mSp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        mEditor = mSp.edit();
    }

    public String getString(String key,String defaultStr){
        return mSp.getString(key,defaultStr);
    }

    protected boolean getBoolean(String key, boolean defaultBoolean) {
        return mSp.getBoolean(key, defaultBoolean);
    }

    public int getInt(String key, int defaultInt) {
        return mSp.getInt(key, defaultInt);
    }

    public long getLong(String key, long defaultLong) {
        return mSp.getLong(key, defaultLong);
    }

    public float getFloat(String key, float defaultFloat) {
        return mSp.getFloat(key, defaultFloat);
    }

    public void put(String key, String value) {
        if (key == null || value == null) {
            return;
        }
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public void put(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public void put(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public void put(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public void put(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

}
