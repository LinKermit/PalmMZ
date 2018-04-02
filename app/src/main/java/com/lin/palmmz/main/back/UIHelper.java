package com.lin.palmmz.main.back;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class UIHelper {


    /**
     * 显示设置界面
     *
     * @param context
     */
    public static void showSetting(Context context) {
        showSimpleBack(context, SimpleBackPage.SETTING);
    }

    /**
     * 显示关于界面
     *
     * @param context
     */
    public static void showAboutOSC(Context context) {
        showSimpleBack(context, SimpleBackPage.ABOUT_OSC);
    }

    public static void showSimpleBack(Context context, SimpleBackPage page) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

}
