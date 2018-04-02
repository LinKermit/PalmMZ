package com.lin.palmmz.publish.tweet;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5 0005.
 */

public class TweetPicturesPreviewer extends RecyclerView implements TweetSelectImageAdapter.Callback{
    private TweetSelectImageAdapter mImageAdapter;

    public TweetPicturesPreviewer(Context context) {
        super(context);
        init();
    }

    public TweetPicturesPreviewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TweetPicturesPreviewer(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mImageAdapter = new TweetSelectImageAdapter(this,getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        this.setLayoutManager(layoutManager);
        this.setAdapter(mImageAdapter);
        this.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    public void set(List<Uri> pathList) {
        String[] paths = new String[pathList.size()];
        for (int i=0; i<pathList.size(); i++){
            paths[i] = pathList.get(i).toString();
        }

        mImageAdapter.clear();
        for (String path : paths) {
            mImageAdapter.add(path);
        }
        mImageAdapter.notifyDataSetChanged();
    }


    @Override
    public void onLoadMoreClick() {

    }
}
