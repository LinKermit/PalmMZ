package com.lin.palmmz.user.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lin.palmmz.R;
import com.lin.palmmz.base.BaseFragment;
import com.lin.palmmz.util.TDevice;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

//@RuntimePermissions
public class AboutPalmFragment extends BaseFragment implements View.OnClickListener{


    @BindView(R.id.tv_version_name)
    TextView mTvVersionName;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_about;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mTvVersionName.setText(TDevice.getVersionName());
    }


    @OnClick({R.id.tv_grade, R.id.tv_oscsite, R.id.tv_knowmore,
            R.id.img_portrait})

    @Override
    public void onClick(View v) {

        final int id = v.getId();
        switch (id) {
            case R.id.tv_grade:
                break;
            case R.id.tv_oscsite:
                break;
            case R.id.tv_knowmore:
                break;
            case R.id.img_portrait:
                break;
            default:
                break;
        }
    }
}
