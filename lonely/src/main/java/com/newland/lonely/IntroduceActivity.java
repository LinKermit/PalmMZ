package com.newland.lonely;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.newland.lonely.base.BaseActivity;

public class IntroduceActivity extends BaseActivity {

    public static void show(Context context){
        context.startActivity(new Intent(context,IntroduceActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_introduce;
    }

}
