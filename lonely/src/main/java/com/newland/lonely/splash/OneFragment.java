package com.newland.lonely.splash;

import android.support.v4.app.Fragment;

import com.newland.lonely.R;
import com.newland.lonely.base.BaseFragment;

/**
 * @author lin
 * @version 2018/8/24 0024
 */
public class OneFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_one;
    }

    public static Fragment newInstance() {
        return new OneFragment();
    }
}
