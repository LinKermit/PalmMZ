package com.lin.palmmz.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.lin.palmmz.R;
import com.lin.palmmz.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by lin on 2017/11/25.
 */

public abstract class BaseBackActivity extends BaseActivity {

    public ViewGroup contentView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_back;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        setTranslucentStatus();
        initToolBar();
        initData(savedInstanceState);
    }
    protected abstract void initData(Bundle savedInstanceState);

    private void initToolBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getTitle());
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置状态栏背景状态
     */
    private void setTranslucentStatus()
    {
        //判断当前SDK版本号，如果是4.4以上，就是支持沉浸式状态栏的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }

    }

    @Override
    protected void initContentView() {
        super.initContentView();
        contentView = (ViewGroup) findViewById(R.id.base_contentview);
        contentView.addView(View.inflate(this, getContentView(), null));

    }

    /**
     * 获取中间内容显示区
     * @return
     */
    protected abstract int getContentView();
}
