package com.newland.lonely.nav;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.newland.lonely.R;

/**
 * @author lin
 * @version 2018/8/24 0024
 */
public class NavigationButton extends FrameLayout {

    private ImageView mIconView;
    private TextView mTitleView;
    private TextView mDot;

    private Class<?> mCls;
    private String mTag;


    private Fragment mFragment;
    public NavigationButton(@NonNull Context context) {
        this(context,null);
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_nav_item, this, true);
        mIconView = findViewById(R.id.nav_iv_icon);
        mTitleView = findViewById(R.id.nav_tv_title);
        mDot = findViewById(R.id.nav_tv_dot);
    }

    public void init(@DrawableRes int resId, @StringRes int strId, Class<?> clx){
        mIconView.setImageResource(resId);
        mTitleView.setText(strId);
        mCls = clx;
        mTag = clx.getName();
    }

    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mIconView.setSelected(selected);
        mTitleView.setSelected(selected);
    }

    /**
     * show red dot
     * @param count
     */
    public void showRedDot(int count){
        mDot.setVisibility(count>0 ? VISIBLE : GONE);
        mDot.setText(String.valueOf(count));
    }

    public Class<?> getCls() {
        return mCls;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

}
