package com.newland.palm.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.newland.palm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author lin
 * @version 2018/5/18 0018
 */
public abstract class BaseActivity extends AppCompatActivity{

    private Unbinder binder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentView();
        //初始化布局
        setContentView(getLayoutId());
        initContentView();
        //初始化黄油刀控件绑定框架
        binder = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
    }

    protected void initContentView() {

    }

    protected abstract int getLayoutId();

    protected void onBeforeSetContentView() {

    }

    public abstract void initViews(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }
}