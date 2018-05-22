package com.lin.palmmz.main.fragment;

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
import com.lin.palmmz.user.WebViewActivity;
import com.lin.palmmz.main.back.UIHelper;
import com.lin.palmmz.base.BaseNavFragment;
import com.lin.palmmz.R;
import com.lin.palmmz.net.bean.NewsInfo;
import com.lin.palmmz.net.bean.User;
import com.lin.palmmz.net.RetrofitHelper;
import com.lin.palmmz.main.notice.NoticeBean;
import com.lin.palmmz.main.notice.NoticeManager;
import com.lin.palmmz.weight.CircleImageView;
import com.lin.palmmz.user.AccountHelper;
import com.lin.palmmz.user.UserDataActivity;
import com.lin.palmmz.util.LogUtils;
import com.lin.palmmz.user.zxing.MyQRCodeDialog;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lin.palmmz.user.WebViewActivity.ACTION_ACCOUNT_FINISH_ALL;

/**
 * Created by lin on 2017/11/18.
 * 个人用户界面
 */

public class UserInfoFragment extends BaseNavFragment implements View.OnClickListener,NoticeManager.NoticeNotify{

    private static final String TAG = "UserInfoFragment";
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

    private User mUserInfo;
    private MyBroadCastReceiver mReceiver;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    protected void initData() {
        super.initData();
        mReceiver = new MyBroadCastReceiver();
        registerLocalReceiver();
        requestUserCache();

        NoticeManager.bindNotify(this);
    }

    private void requestUserCache() {
        if (AccountHelper.isLogin()){
            Log.e(TAG, "requestUserCache: ");
            User user = AccountHelper.getUser();
            updateView(user);
        }
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
        if (id == R.id.iv_logo_zxing) {
            MyQRCodeDialog dialog = new MyQRCodeDialog(mContext);
            dialog.show();
        }

        if (id == R.id.iv_logo_setting){
            UIHelper.showSetting(getActivity());
        }else {

            if (!AccountHelper.isLogin()){//如果没有登录
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                mContext.startActivity(intent);
                return;
            }

            switch (id){

                case R.id.iv_portrait://更换头像

                case R.id.rl_data:
                    Intent intent = new Intent(mContext, UserDataActivity.class);
                    intent.putExtra("user",mUserInfo);
                    mContext.startActivity(intent);
                    break;
            }
        }
    }


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

    /**
     * 注册监听WebActivity登录成功
     */
    private void registerLocalReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_ACCOUNT_FINISH_ALL);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mReceiver,intentFilter);
    }

    private class MyBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == ACTION_ACCOUNT_FINISH_ALL){

                if (AccountHelper.isLogin()){
                    NoticeManager.init(context);
                    User user = AccountHelper.getUser();
                    updateView(user);
                }else {
                    hideView();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(mContext)
                .unregisterReceiver(mReceiver);
        NoticeManager.unBindNotify(this);
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
    public void onNoticeArrived(NoticeBean bean) {
        if (mMesView != null) {
            int allCount = bean.getReview() + bean.getLetter() + bean.getMention();
            mMesView.setVisibility(allCount > 0 ? View.VISIBLE : View.GONE);
            mMesView.setText(String.valueOf(allCount));
        }
        if (mFansView != null) {
            int fans = bean.getFans();
            mFansView.setVisibility(fans > 0 ? View.VISIBLE : View.GONE);
            mFansView.setText(String.valueOf(fans));
        }
    }

}
