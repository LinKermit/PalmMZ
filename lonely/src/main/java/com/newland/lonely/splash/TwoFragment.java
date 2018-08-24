package com.newland.lonely.splash;

import android.support.v4.app.Fragment;
import android.view.View;

import com.newland.lonely.MainActivity;
import com.newland.lonely.R;
import com.newland.lonely.base.BaseFragment;

import butterknife.OnClick;

/**
 * @author lin
 * @version 2018/8/24 0024
 */
public class TwoFragment extends BaseFragment implements View.OnClickListener{
    @Override
    public int getLayoutId() {
        return R.layout.fragment_two;
    }

    public static Fragment newInstance() {
        return new TwoFragment();
    }


    @OnClick(R.id.btn_introduce)
    @Override
    public void onClick(View view) {
        MainActivity.show(mContext);
        getActivity().finish();
    }
}
