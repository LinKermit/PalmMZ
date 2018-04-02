package com.lin.palmmz.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Lin on 2017/5/18.
 * Fragment的基类
 * 1、getLayoutResId()加载布局
 * 2、懒加载：是否是用户可见的状态
 * 3、onViewCreated ：在View创建完成时，初始化ButterKnife，
 * 通过finishCreateView()，令isPrepared=true，开始加载数据
 */

public abstract class BaseFragment extends Fragment {
    public FragmentActivity mContext;
    View parentView;

    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;
    //标志位 fragment是否可见
    protected boolean isVisible;
    private Unbinder binder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutResId(),container,false);
        mContext = getSupportActivity();
        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binder = ButterKnife.bind(this,view);
        finishCreateView(savedInstanceState);
    }

    public abstract int getLayoutResId();

    public abstract void finishCreateView(Bundle state);

    protected void lazyLoad() {}
    /**
     * Fragment的懒加载
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//在onCreateView之前调用
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {//是否是用户可见的状态，如果是加载数据
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {}


    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.mContext = (FragmentActivity) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder.unbind();
    }

    public boolean onBackPressed() {
        return false;
    }

}
