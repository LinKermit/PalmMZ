package com.newland.lonely.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author lin
 * @version 2018/8/22 0022
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder binder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        initWindow();
        binder = ButterKnife.bind(this);
        initWidget();
        initData();
    }

    //5.0上沉浸式状态栏
    private void initWindow() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();

            // 部分机型的statusbar会有半透明的黑色背景
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    protected abstract int getContentView();
    protected void initWidget() {}

    protected void initData() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }
}
