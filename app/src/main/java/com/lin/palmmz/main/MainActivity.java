package com.lin.palmmz.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lin.palmmz.base.BaseActivity;
import com.lin.palmmz.R;
import com.lin.palmmz.main.notice.NoticeManager;
import com.lin.palmmz.user.update.CheckUpdateManager;
import com.lin.palmmz.util.LogUtils;
import com.lin.palmmz.main.nav.NavFragment;
import com.lin.palmmz.main.nav.NavigationButton;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavFragment.OnNavigationReselectListener{

    private static final String TAG = "MainActivity";
    public static final String ACTION_NOTICE = "ACTION_NOTICE";
    @BindView(R.id.main_container)
    FrameLayout mMainUi;

    private NavFragment mNavBar;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();

        mNavBar = ((NavFragment) manager.findFragmentById(R.id.fag_nav));
        mNavBar.setup(this,manager,R.id.main_container,this);
        //notice service
        NoticeManager.init(this);
        //update
        CheckUpdateManager checkManager = new CheckUpdateManager(this,false);
        checkManager.checkUpdate(true);


    }


    @Override
    public void onReselect(NavigationButton navigationButton) {//给我回传的navigationButton
        Fragment fragment = navigationButton.getFragment();//通过回传的navigationButton获取得到Fragment
        Toast.makeText(this,navigationButton.getId()+"second tab",Toast.LENGTH_SHORT).show();
//        OnTabReselectListener onTabReselectListener  = (OnTabReselectListener) fragment;
//        onTabReselectListener.onTabReselect();//

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_NOTICE.equals(intent.getAction())){
            LogUtils.e(TAG,"Notification:"+intent.getAction());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NoticeManager.stopListen(this);
    }
}
