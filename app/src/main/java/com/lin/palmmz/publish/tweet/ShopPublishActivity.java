package com.lin.palmmz.publish.tweet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lin.palmmz.R;
import com.lin.palmmz.base.BaseBackActivity;

/**
 * Created by lin on 2017/11/29.
 * 博客界面
 */

public class ShopPublishActivity extends BaseBackActivity {

    public static void show(Context context){
        context.startActivity(new Intent(context,ShopPublishActivity.class));
    }
    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_tweet_publish;
    }
}
