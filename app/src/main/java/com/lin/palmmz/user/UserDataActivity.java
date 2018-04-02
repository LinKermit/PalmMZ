package com.lin.palmmz.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lin.palmmz.R;
import com.lin.palmmz.base.BaseBackActivity;
import com.lin.palmmz.net.bean.User;
import com.lin.palmmz.weight.CircleImageView;
import com.lin.palmmz.weight.CustomEmptyView;
import com.lin.palmmz.util.StringUtils;

import butterknife.BindView;

/**
 * Created by lin on 2017/11/25.
 */

public class UserDataActivity extends BaseBackActivity implements View.OnClickListener{
    @BindView(R.id.iv_avatar)
    CircleImageView mUserFace;

    @BindView(R.id.tv_name)
    TextView mName;

    @BindView(R.id.tv_join_time)
    TextView mJoinTime;

    @BindView(R.id.tv_location)
    TextView mFrom;

    @BindView(R.id.tv_development_platform)
    TextView mPlatFrom;

    @BindView(R.id.tv_academic_focus)
    TextView mFocus;

    @BindView(R.id.tv_desc)
    TextView mDesc;

    @BindView(R.id.error_layout)
    CustomEmptyView mErrorLayout;

    private User userInfo;
    @Override
    protected int getContentView() {
        return R.layout.activity_user_data;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        userInfo = (User) getIntent().getSerializableExtra("user");
        if (userInfo == null)
            return;

        fillUI();
    }

    @SuppressWarnings("deprecation")
    private void fillUI() {
        Glide.with(this).load(userInfo.getPortrait()).into(mUserFace);

        mUserFace.setOnClickListener(null);
        mName.setText(getText(userInfo.getName()));
        mJoinTime.setText(getText(StringUtils.formatYearMonthDayNew(userInfo.getJoinTime())));
        mFrom.setText(getText(userInfo.getCity()));
        mPlatFrom.setText(getText(userInfo.getPlatforms().get(0)));
        mFocus.setText(getText(userInfo.getExpertise().get(0)));
//        mDesc.setText(getText(userInfo.getDesc()));
    }

    @Override
    public void onClick(View view) {

    }

    private String getText(String text) {
        if (text == null || text.equalsIgnoreCase("null")){
            return "<无>";
        }else{
            return text;
        }
    }
}
