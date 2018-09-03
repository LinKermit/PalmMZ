package com.newland.lonely.splash;

import android.os.SystemClock;

import com.newland.lonely.operator.AppOperator;
import com.newland.lonely.MainActivity;
import com.newland.lonely.operator.OSSharedPreference;
import com.newland.lonely.R;
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
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                redirectTo();
            }
        });
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
