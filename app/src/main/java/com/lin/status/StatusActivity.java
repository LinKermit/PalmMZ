package com.lin.status;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.lin.palmmz.R;

import butterknife.BindView;

/**
 * @author lin
 * @version 2018/9/4 0004
 */
public class StatusActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_status;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setStatusBarDarkMode();
        initToolBar();
    }


    public void initToolBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }
}
