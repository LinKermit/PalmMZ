package com.lin.palmmz.main.fragment;

import android.util.Log;

import com.lin.palmmz.base.BaseNavFragment;
import com.lin.palmmz.R;

/**
 * Created by lin on 2017/11/18.
 */

public class TweetViewFragment extends BaseNavFragment {

    private static final String TAG = "TweetViewFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tweet_view;
    }


    @Override
    protected void initData() {
        super.initData();

        Log.e(TAG, "initData: ."  );
    }
}
