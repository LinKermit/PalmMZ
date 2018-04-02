package com.lin.palmmz.main.back;

import com.lin.palmmz.R;
import com.lin.palmmz.user.setting.AboutPalmFragment;
import com.lin.palmmz.user.setting.SettingsFragment;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public enum SimpleBackPage {

    SETTING(15, R.string.actionbar_title_setting, SettingsFragment.class),
    ABOUT_OSC(17, R.string.actionbar_title_about_osc, AboutPalmFragment.class);

    private int title;
    private Class<?> clz;
    private int value;

    private SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }

}
