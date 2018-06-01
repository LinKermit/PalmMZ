package com.newland.palm.main.user;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newland.palm.R;
import com.newland.palm.base.BaseFragment;
import com.newland.palm.data.AccountHelper;
import com.newland.palm.data.bean.Portrait;
import com.newland.palm.data.bean.User;
import com.newland.palm.data.net.RetrofitHelper;
import com.newland.palm.main.user.zxing.MyQRCodeDialog;
import com.newland.palm.utils.LogUtils;
import com.newland.palm.ui.CircleImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lin on 2017/11/18.
 * 4-用户
 */

public class UserFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = "UserFragment";

    @BindView(R.id.iv_logo_setting)
    ImageView mIvLogoSetting;
    @BindView(R.id.iv_logo_zxing)
    ImageView mIvLogoZxing;
    @BindView(R.id.user_info_head_container)
    FrameLayout mFlUserInfoHeadContainer;

    @BindView(R.id.iv_portrait)
    CircleImageView mPortrait;
    @BindView(R.id.iv_gender)
    ImageView mIvGander;
    @BindView(R.id.user_info_icon_container)
    FrameLayout mFlUserInfoIconContainer;

    @BindView(R.id.tv_nick)
    TextView mTvName;
    @BindView(R.id.tv_avail_score)
    TextView mTextAvailScore;
    @BindView(R.id.tv_active_score)
    TextView mTextActiveScore;
    @BindView(R.id.rl_show_my_info)
    LinearLayout mRlShowInfo;


    @BindView(R.id.about_line)
    View mAboutLine;

    @BindView(R.id.lay_about_info)
    LinearLayout mLayAboutCount;
    @BindView(R.id.tv_tweet)
    TextView mTvTweetCount;
    @BindView(R.id.tv_favorite)
    TextView mTvFavoriteCount;
    @BindView(R.id.tv_following)
    TextView mTvFollowCount;
    @BindView(R.id.tv_follower)
    TextView mTvFollowerCount;

    @BindView(R.id.user_info_notice_message)
    TextView mMesView;

    @BindView(R.id.user_info_notice_fans)
    TextView mFansView;

    private MyBCReceiver receiver;
    private User mUserInfo;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Log.e(TAG, "initWidget: "  );
        receiver = new MyBCReceiver();
        register();
    }


    @OnClick({
            R.id.iv_logo_setting, R.id.iv_logo_zxing, R.id.iv_portrait,
            R.id.ly_tweet, R.id.ly_favorite,
            R.id.ly_following, R.id.ly_follower, R.id.rl_message,
            R.id.rl_data,R.id.rl_blog,
            R.id.rl_info_question, R.id.rl_info_activities, R.id.rl_team
    })
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_logo_zxing){
            MyQRCodeDialog dialog = new MyQRCodeDialog(mContext);
            dialog.show();
        }

        if (id == R.id.iv_logo_setting){

        }else {
            if (!AccountHelper.isLogin()){
                WebViewActivity.show(mContext);
                return;
            }
            switch (id){
                case R.id.iv_portrait:
//                    uploadPortrait();
                    break;
            }
        }
    }

    private void uploadPortrait() {
        String filePath = "/mnt/shell/emulated/0/ic_launcher_round.png";
        File file = new File(filePath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("imgfile", file.getName(), imageBody);
        RetrofitHelper.uploadInterface().uploadPortrait(imageBodyPart, AccountHelper.getUser().getAccess_token())
                .enqueue(new Callback<Portrait>() {
                    @Override
                    public void onResponse(Call<Portrait> call, Response<Portrait> response) {
                        LogUtils.error(response.body().getError_description());
                    }

                    @Override
                    public void onFailure(Call<Portrait> call, Throwable t) {
                        LogUtils.error(t.getMessage());
                    }
                });
    }


    /**
     * 登录成功广播注册
     */
    private void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WebViewActivity.ACTION_ACCOUNT_LOGIN_SUCC);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver,filter);
    }

    private class MyBCReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == WebViewActivity.ACTION_ACCOUNT_LOGIN_SUCC){
                if (AccountHelper.isLogin()){
                    User user = AccountHelper.getUser();
                    updateView(user);
                }else {
                    hideView();
                }

            }
        }
    }

    private void hideView() {
        mPortrait.setImageResource(R.mipmap.widget_default_face);
        mTvName.setText(R.string.user_hint_login);
        mTvName.setTextSize(16.0f);
        mIvGander.setVisibility(View.INVISIBLE);
        mTextActiveScore.setVisibility(View.INVISIBLE);
        mTextAvailScore.setVisibility(View.INVISIBLE);
        mLayAboutCount.setVisibility(View.GONE);
        mAboutLine.setVisibility(View.GONE);
    }

    /**
     * 更新UserFragment界面
     * @param userInfo
     */
    private void updateView(User userInfo) {
        Glide.with(mContext).load(userInfo.getPortrait()).asBitmap()
                .placeholder(R.color.black_alpha_48)
                .error(R.mipmap.widget_default_face)
                .into(mPortrait);
        mPortrait.setVisibility(View.VISIBLE);

        mTvName.setText(userInfo.getName());
        mTvName.setVisibility(View.VISIBLE);
        mTvName.setTextSize(20.0f);

        switch (userInfo.getGender()) {
            case 0:
                mIvGander.setVisibility(View.INVISIBLE);
                break;
            case 1:
                mIvGander.setVisibility(View.VISIBLE);
                mIvGander.setImageResource(R.mipmap.ic_male);
                break;
            case 2:
                mIvGander.setVisibility(View.VISIBLE);
                mIvGander.setImageResource(R.mipmap.ic_female);
                break;
            default:
                break;
        }

        mTextAvailScore.setText(String.format("技能积分 %s", userInfo.getUid()));
        mTextActiveScore.setText(String.format("活跃积分 %s", userInfo.getUid()));
        mTextAvailScore.setVisibility(View.VISIBLE);
        mTextActiveScore.setVisibility(View.VISIBLE);
        mAboutLine.setVisibility(View.VISIBLE);
        mLayAboutCount.setVisibility(View.VISIBLE);
        mTvTweetCount.setText(formatCount(userInfo.getFavoriteCount()));
        mTvFavoriteCount.setText(formatCount(userInfo.getFavoriteCount()));
        mTvFollowCount.setText(formatCount(userInfo.getFollowersCount()));
        mTvFollowerCount.setText(formatCount(userInfo.getFansCount()));

        mUserInfo = userInfo;
    }

    /**
     * int to String
     * @param count
     * @return
     */
    private String formatCount(long count) {

        if (count > 1000) {
            int a = (int) (count / 100);
            int b = a % 10;
            int c = a / 10;
            String str;
            if (c <= 9 && b != 0){
                str = c + "." + b;
            }
            else{
                str = String.valueOf(c);
            }
            return str + "k";
        } else {
            return String.valueOf(count);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e(TAG,"UserInfoFragment onResume---");
        if (AccountHelper.isLogin()){
            User user = AccountHelper.getUser();
            updateView(user);
        }else {
            hideView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.error("UserFragment onDestroy");
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(receiver);
    }
}
