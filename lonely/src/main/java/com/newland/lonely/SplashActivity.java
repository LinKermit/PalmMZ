package com.newland.lonely;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.newland.lonely.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        super.initData();
        // 完成后进行跳转操作
        SystemClock.sleep(2000);
        redirectTo();
    }

    private void redirectTo() {
        if (OSSharedPreference.getInstance().isFirstInstall()){
            IntroduceActivity.show(this);
        }else {
            MainActivity.show(this);
        }
        finish();
    }
}
