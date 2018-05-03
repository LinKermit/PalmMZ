package com.lin.palmmz.main.nav;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.palmmz.R;

/**
 * Created by lin on 2017/11/17.
 */

public class NavigationButton extends FrameLayout {

    private Fragment mFragment = null;
    private Class<?> mClx;
    private ImageView mIconView;
    private TextView mTitleView;
    private TextView mDot;
    private String mTag;
    public NavigationButton(@NonNull Context context) {
        this(context,null);
        init();
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        init();
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_nav_item,this,true);

        mIconView = (ImageView) findViewById(R.id.nav_iv_icon);
        mTitleView = (TextView) findViewById(R.id.nav_tv_title);
        mDot = (TextView) findViewById(R.id.nav_tv_dot);
    }

    /**
     *e
     * @param isSelected
     */
    @Override
    public void setSelected(boolean isSelected){
        mIconView.setSelected(isSelected);
        mTitleView.setSelected(isSelected);
    }

    public void showDot(int mCount){
        mDot.setVisibility(mCount>0? VISIBLE:GONE);
        mDot.setText(String.valueOf(mCount));
    }

    public void init(int resId,int strId,Class<?> clx){
        mIconView.setImageResource(resId);
        mTitleView.setText(strId);
        mClx = clx;
        mTag = mClx.getName();
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    public String getTag() {
        return mTag;
    }

    public Class<?> getClx() {
        return mClx;
    }
}
