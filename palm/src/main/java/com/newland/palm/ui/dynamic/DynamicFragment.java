package com.newland.palm.ui.dynamic;

import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.newland.palm.R;
import com.newland.palm.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lin on 2017/11/18.
 * 1-咨询
 */

public class DynamicFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = "DynamicFragment";

    @BindView(R.id.layout_tab)
    TabLayout mLayoutTab;
    @BindView(R.id.view_tab_picker)
    TabPickerView mViewTabPicker;

    @BindView(R.id.iv_arrow_down)
    ImageView mViewArrowDown;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        Log.e(TAG, "initWidget: "  );
    }


    @OnClick(R.id.iv_arrow_down)
    @Override
    public void onClick(View view) {
        if (mViewArrowDown.getRotation() != 0){
            mViewTabPicker.onTurnBack();
        }else {
            mViewTabPicker.show(mLayoutTab.getSelectedTabPosition());
        }
    }
}
