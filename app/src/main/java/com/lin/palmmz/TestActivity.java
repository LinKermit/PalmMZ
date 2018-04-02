package com.lin.palmmz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lin.palmmz.R;
import com.lin.palmmz.weight.TweetPicturesLayout;

/**
 * @author lin
 * @version 创建时间：2018/1/27 0027 下午 4:51
 */
public class TestActivity extends AppCompatActivity {

    TweetPicturesLayout tweetPicturesLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String[] images = new String[]{"content://media/external/images/media/26",
                "content://media/external/images/media/25",
                "content://media/external/images/media/24",
                "content://media/external/images/media/25",
                "content://media/external/images/media/24"
        };
        tweetPicturesLayout = (TweetPicturesLayout)findViewById(R.id.tweet_pics_layout);
        tweetPicturesLayout.setImage(images);
    }
}
