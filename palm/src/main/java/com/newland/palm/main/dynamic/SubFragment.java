package com.newland.palm.main.dynamic;

import android.content.Context;
import android.os.Bundle;

import com.newland.palm.R;
import com.newland.palm.base.BaseFragment;
import com.newland.palm.data.bean.SubTab;

/**
 * @author lin
 * @version 2018/6/1 0001
 */
public class SubFragment extends BaseFragment{


    public static SubFragment newInstance(Context context, SubTab subTab) {
        SubFragment fragment = new SubFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", subTab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tweet;
    }


}
