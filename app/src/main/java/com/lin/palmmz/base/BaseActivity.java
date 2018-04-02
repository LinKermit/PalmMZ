package com.lin.palmmz.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lin.palmmz.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lin on 2017/11/16.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private Unbinder binder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.App_Theme_Light);
        //在加载布局之前的设置，子类若需要可以重写方法
        onBeforeSetContentView();
        //初始化布局
        setContentView(getLayoutId());
        initContentView();//有toolbar再初始化内容布局
        //初始化黄油刀控件绑定框架
        binder = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);

        //初始化ToolBar
//        initToolBar();
    }

    protected void initContentView() {

    }

    protected abstract int getLayoutId();

    protected void onBeforeSetContentView() {

    }

    public abstract void initViews(Bundle savedInstanceState);

//    public abstract void initToolBar();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }
}
