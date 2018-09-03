package com.newland.lonely.splash;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.newland.lonely.operator.OSSharedPreference;
import com.newland.lonely.R;
import com.newland.lonely.base.BaseActivity;

import butterknife.BindView;

public class IntroduceActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    public static void show(Context context){
        context.startActivity(new Intent(context,IntroduceActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_introduce;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return position == 0 ? OneFragment.newInstance() : TwoFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        OSSharedPreference.getInstance().putFirstInstall();
    }
}
