package com.newland.lonely;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.newland.lonely.base.BaseActivity;
import com.newland.lonely.nav.NavFragment;
import com.newland.lonely.nav.NavigationButton;
import com.newland.lonely.utils.LogUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavFragment.OnNavigationReselectListener{


    private NavFragment mNavBar;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    public static void show(Context context) {
        context.startActivity(new Intent(context,MainActivity.class));
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setStatusBarDarkMode();
        FragmentManager manager = getSupportFragmentManager();
        //activity 中获取fragment
        mNavBar = (NavFragment) manager.findFragmentById(R.id.fag_nav);
        mNavBar.setup(this,manager,R.id.main_container,this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onReselect(NavigationButton navigationButton) {
        LogUtils.error("onReselect");
    }
}
