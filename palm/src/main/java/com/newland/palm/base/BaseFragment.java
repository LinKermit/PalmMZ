package com.newland.palm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * @author lin
 * @version 2018/5/17 0017
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected Bundle bundle;
    protected View mRoot;

    protected LayoutInflater mInflater;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();//传递数据
        initBundle(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(getLayoutId(), container, false);
        mInflater = inflater;
        // Do something
        onBindViewBefore(mRoot);
        // Bind view
        ButterKnife.bind(this, mRoot);

        if (savedInstanceState != null){
            onRestartInstance(savedInstanceState);//保存的数据
        }
        initWidget(mRoot);
        initData();
        return mRoot;
    }



    protected void initBundle(Bundle bundle) {}

    protected abstract int getLayoutId();

    protected void onBindViewBefore(View root) {
        // ...
    }

    protected void onRestartInstance(Bundle bundle) {

    }

    protected void initWidget(View root) {

    }

    protected void initData() {

    }
}
