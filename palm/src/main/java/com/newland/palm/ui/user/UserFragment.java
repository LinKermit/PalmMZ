package com.newland.palm.ui.user;
import android.util.Log;
import android.view.View;

import com.newland.palm.R;
import com.newland.palm.base.BaseFragment;
import com.newland.palm.ui.nav.NavFragment;

/**
 * Created by lin on 2017/11/18.
 * 4-用户
 */

public class UserFragment extends BaseFragment {

    private static final String TAG = "UserFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Log.e(TAG, "initWidget: "  );

    }
}
