package com.newland.palm.ui.tweet;
import android.util.Log;
import android.view.View;

import com.newland.palm.R;
import com.newland.palm.base.BaseFragment;

/**
 * Created by lin on 2017/11/18.
 * 2-动态
 */

public class TweetFragment extends BaseFragment {

    private static final String TAG = "TweetFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tweet;
    }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Log.e(TAG, "initWidget: "  );
    }
}
