package com.newland.palm.ui.dynamic;

import android.util.Log;
import android.view.View;

import com.newland.palm.R;
import com.newland.palm.base.BaseFragment;

/**
 * Created by lin on 2017/11/18.
 * 1-咨询
 */

public class DynamicFragment extends BaseFragment {

    private static final String TAG = "DynamicFragment";
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        Log.e(TAG, "initWidget: "  );
    }
}
