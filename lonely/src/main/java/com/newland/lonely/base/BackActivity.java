package com.newland.lonely.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.newland.lonely.R;

/**
 * @author lin
 * @version 2018/9/4 0004
 */
public abstract class BackActivity extends BaseActivity {

    Toolbar mToolBar;

    @Override
    protected void initWidget() {
        super.initWidget();

        mToolBar = findViewById(R.id.toolbar);
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
            mToolBar.setNavigationIcon(R.mipmap.btn_back_dark_pressed);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true); //小箭头
                actionBar.setHomeButtonEnabled(false);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
