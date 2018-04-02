package com.lin.palmmz.publish;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lin.palmmz.R;
import com.lin.palmmz.publish.tweet.TweetPublishActivity;
import com.lin.palmmz.publish.tweet.ShopPublishActivity;
import com.lin.palmmz.base.BaseActivity;
import com.lin.palmmz.util.Util;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lin on 2017/11/29.
 * 发动态和博客的动画页面
 */

public class PubActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.btn_pub)
    ImageView mBtnPub;

    @BindView(R.id.ll_pub_blog)
    LinearLayout mLaysBlog;

    @BindView(R.id.ll_pub_tweet)
    LinearLayout mLaysTweet;

    public static void show(Context context){
        Intent intent = new Intent(context, PubActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pub;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        startAnimate();
    }

    private void startAnimate() {
        mBtnPub.animate()
                .rotation(135.0f)
                .setDuration(250)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
        show(mLaysBlog,0);
        show(mLaysTweet,1);

    }

    /**
     * 开启动画,x，y轴平移
     * @param linearLayout
     * @param position
     */
    private void show(LinearLayout linearLayout, int position) {
        int angle = position==0 ?45:135;
        float x = (float) Math.cos(angle * (Math.PI / 180)) * Util.dipTopx(this, 80);
        float y = (float) -Math.sin(angle * (Math.PI / 180)) * Util.dipTopx(this, 80);
        ObjectAnimator objectX = ObjectAnimator.ofFloat(linearLayout,"translationX",x);
        ObjectAnimator objectY = ObjectAnimator.ofFloat(linearLayout,"translationY",y);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(250);
        animatorSet.play(objectX).with(objectY);
        animatorSet.start();
    }

    private void close(LinearLayout linearLayout,int position) {
        int angle = position == 0 ? 45 : 135;
        float x = (float) Math.cos(angle * (Math.PI / 180)) * Util.dipTopx(this, 80);
        float y = (float) -Math.sin(angle * (Math.PI / 180)) * Util.dipTopx(this, 80);
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(linearLayout, "translationX", x, 0);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(linearLayout, "translationY", y, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(250);
        animatorSet.play(objectAnimatorX).with(objectAnimatorY);
        animatorSet.start();
    }

    private void dismiss(){
        close();
        close(mLaysBlog,0);
        close(mLaysTweet,1);
    }

    private void close() {
        mBtnPub.clearAnimation();
        mBtnPub.animate()
                .rotation(0f)
                .setDuration(250)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        finish();
                    }
                })
                .start();
    }


    @OnClick({R.id.rl_main, R.id.ll_pub_tweet, R.id.ll_pub_blog})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_main:
                dismiss();
                break;
            case R.id.ll_pub_tweet:
//                if(!AccountHelper.isLogin()){
//                    LoginActivity.show(this);
//                    finish();
//                    return;
//                }
                TweetPublishActivity.show(this,mBtnPub);//传递mBtnPub,长按直接跳转
                finish();
                break;
            case R.id.ll_pub_blog:
//                if(!AccountHelper.isLogin()){
//                    LoginActivity.show(this);
//                    finish();
//                    return;
//                }
                ShopPublishActivity.show(this);
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);//activity返回无动画效果
    }
}
