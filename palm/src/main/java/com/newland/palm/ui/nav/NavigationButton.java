package com.newland.palm.ui.nav;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.newland.palm.R;

/**
 * @author lin
 * @version 2018/5/17 0017
 */
public class NavigationButton extends FrameLayout {

    private ImageView mIconView;
    private TextView mTitleView;
    private TextView mDot;
    private Class<?> cls;
    private String mTag;
    private Fragment fragment;
    public NavigationButton(@NonNull Context context) {
        this(context,null);
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_nav_item,this,true);

        mIconView = findViewById(R.id.nav_iv_icon);
        mTitleView =  findViewById(R.id.nav_tv_title);
        mDot =  findViewById(R.id.nav_tv_dot);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mIconView.setSelected(selected);
        mTitleView.setSelected(selected);
    }

    public void init(int resId, int strId, Class<?> cls){
        mIconView.setImageResource(resId);
        mTitleView.setText(strId);
        this.cls = cls;
        mTag = cls.getName();
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getmTag() {
        return mTag;
    }
}
