package com.newland.lonely.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author lin
 * @version 2018/8/24 0024
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected View mRoot;
    Bundle mBundle; //activity 传递的数据
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle();
    }

    protected void initBundle() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            // Do something
            onBindViewBefore(mRoot);
            ButterKnife.bind(this, mRoot);

            // Get savedInstanceState
            if (savedInstanceState != null){
                onRestartInstance(savedInstanceState);
            }

            // Init
            initWidget(mRoot);
            initData();
        }
        return mRoot;
    }

    protected void onRestartInstance(Bundle savedInstanceState) {

    }

    public abstract int getLayoutId();

    protected void onBindViewBefore(View root) {
        // ...
    }

    protected void initWidget(View root) {

    }

    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBundle = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}
