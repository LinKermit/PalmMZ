package net.oschina.app.improve.main.nav;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.oschina.app.R;
import net.oschina.app.improve.account.AccountHelper;
import net.oschina.app.improve.account.activity.LoginActivity;
import net.oschina.app.improve.base.activities.BaseActivity;
import net.oschina.app.improve.media.Util;
import net.oschina.app.improve.tweet.activities.TweetPublishActivity;
import net.oschina.app.improve.write.WriteActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 发布界面
 * Created by huanghaibin on 2017/9/25.
 */

public class PubActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.btn_pub)
    ImageView mBtnPub;

    @Bind({R.id.ll_pub_blog, R.id.ll_pub_tweet})
    LinearLayout[] mLays;

    public static void show(Context context) {
        context.startActivity(new Intent(context, PubActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_pub;
    }

    @Override
    protected void initWindow() {
        super.initWindow();
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mBtnPub.animate()
                .rotation(135.0f)
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
        show(0);
        show(1);
    }

    @OnClick({R.id.rl_main, R.id.ll_pub_tweet, R.id.ll_pub_blog})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_main:
                dismiss();
                break;
            case R.id.ll_pub_tweet:
                if(!AccountHelper.isLogin()){
                    LoginActivity.show(this);
                    finish();
                    return;
                }
                TweetPublishActivity.show(this, mBtnPub);
                finish();
                break;
            case R.id.ll_pub_blog:
                if(!AccountHelper.isLogin()){
                    LoginActivity.show(this);
                    finish();
                    return;
                }
                WriteActivity.show(this);
                finish();
                break;
        }
    }

    private void dismiss(){
        close();
        close(0);
        close(1);
    }

    private void close() {
        mBtnPub.clearAnimation();
        mBtnPub.animate()
                .rotation(0f)
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        finish();
                    }
                })
                .start();
    }

    private void show(int position) {
        int angle = position == 0 ? 45 : 135;
        float x = (float) Math.cos(angle * (Math.PI / 180)) * Util.dipTopx(this, 80);
        float y = (float) -Math.sin(angle * (Math.PI / 180)) * Util.dipTopx(this, 80);
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mLays[position], "translationX", 0, x);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mLays[position], "translationY", 0, y);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(180);
        animatorSet.play(objectAnimatorX).with(objectAnimatorY);
        animatorSet.start();
    }

    private void close(int position) {
        int angle = position == 0 ? 45 : 135;
        float x = (float) Math.cos(angle * (Math.PI / 180)) * Util.dipTopx(this, 80);
        float y = (float) -Math.sin(angle * (Math.PI / 180)) * Util.dipTopx(this, 80);
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mLays[position], "translationX", x, 0);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mLays[position], "translationY", y, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(180);
        animatorSet.play(objectAnimatorX).with(objectAnimatorY);
        animatorSet.start();
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
