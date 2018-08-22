package com.newland.palm.base;

import android.view.View;
import android.view.ViewStub;

import com.newland.palm.R;
import com.newland.palm.ui.TitleBar;

/**
 * @author lin
 * @version 2018/6/1 0001
 */
public abstract class BaseTitleFragment extends BaseFragment {

    TitleBar mTitleBar;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_title;
    }


    @Override
    protected void onBindViewBefore(View root) {
        super.onBindViewBefore(root);

        //ViewStub 用法
        ViewStub viewStub = root.findViewById(R.id.lay_content);
        viewStub.setLayoutResource(getContentLayoutId());
        viewStub.inflate();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mTitleBar = root.findViewById(R.id.nav_title_bar);
        mTitleBar.setTitle(getTitleRes());
        mTitleBar.setIcon(getIconRes());
    }

    protected int getIconRes() {
        return 0;
    }

    protected abstract int getTitleRes();

    protected abstract int getContentLayoutId();
}
