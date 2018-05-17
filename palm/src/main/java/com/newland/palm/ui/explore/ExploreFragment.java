package com.newland.palm.ui.explore;


import android.util.Log;
import android.view.View;

import com.newland.palm.R;
import com.newland.palm.base.BaseFragment;

/**
 * Created by lin on 2017/11/18.
 */

public class ExploreFragment extends BaseFragment {

    private static final String TAG = "ExploreFragment";
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Log.e(TAG, "initWidget: "  );
    }
}
