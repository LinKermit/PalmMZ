package com.newland.lonely.pub;

import android.content.Context;
import android.content.Intent;

import com.newland.lonely.R;
import com.newland.lonely.base.BaseActivity;

public class PubActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_pub;
    }

    public static void show(Context context) {
        context.startActivity(new Intent(context,PubActivity.class));
    }
}
