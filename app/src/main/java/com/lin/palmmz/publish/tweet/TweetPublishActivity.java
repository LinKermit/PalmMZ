package com.lin.palmmz.publish.tweet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.lin.palmmz.R;
import com.lin.palmmz.base.BaseActivity;

/**
 * Created by lin on 2017/11/29.
 * 发动态界面
 * 有个接口先不写
 */

public class TweetPublishActivity extends BaseActivity {

    public static void show(Context context){
        show(context,null);
    }

    public static void show(Context context, View view) {
        show(context, view, null);
    }

    private static void show(Context context, View view, String defaultContent) {
        show(context, view, defaultContent, null);
    }

    private static void show(Context context, View view, String defaultContent, String localImageUrl) {
        int[] location = new int[]{0, 0};
        int[] size = new int[]{0, 0};

        if (view != null) {
            view.getLocationOnScreen(location);
            size[0] = view.getWidth();
            size[1] = view.getHeight();
        }
        show(context, location, size, defaultContent, localImageUrl);
    }

    private static void show(Context context, int[] viewLocationOnScreen, int[] viewSize, String defaultContent, String localImageUrl) {
        // Check login before show
//        if (!AccountHelper.isLogin()) {
//            WebViewActivity.show(context);
//            return;
//        }

        Intent intent = new Intent(context, TweetPublishActivity.class);

        if (viewLocationOnScreen != null) {
            intent.putExtra("location", viewLocationOnScreen);
        }
        if (viewSize != null) {
            intent.putExtra("size", viewSize);
        }
        if (defaultContent != null) {
            intent.putExtra("defaultContent", defaultContent);
        }
//        if (share != null) {
//            intent.putExtra("aboutShare", share);
//        }
        if (!TextUtils.isEmpty(localImageUrl)) {
            intent.putExtra("imageUrl", localImageUrl);
        }
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tweet_publish;
    }
    TweetPublishFragment fragment;
    @Override
    public void initViews(Bundle savedInstanceState) {
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        //暂时不做缓存
        fragment = new TweetPublishFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.activity_tweet_publish, fragment);
        trans.commit();

    }

    @Override
    public void onBackPressed() {
        if (fragment.mFacePanel.isShow()){
            fragment.mFacePanel.hidePanel();
            return;
        }
        super.onBackPressed();
    }
}
