package com.newland.lonely;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.newland.lonely.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    public static void show(Context context) {
        context.startActivity(new Intent(context,MainActivity.class));
    }
}
