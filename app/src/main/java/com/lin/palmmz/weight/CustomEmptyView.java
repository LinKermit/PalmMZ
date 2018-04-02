package com.lin.palmmz.weight;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.palmmz.R;

/**
 * Created by Lin on 2017/5/19.
 */

public class CustomEmptyView extends FrameLayout {

    private ImageView emptyImage;
    private TextView emptyText;
    public CustomEmptyView(@NonNull Context context) {
        this(context,null);
    }

    public CustomEmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomEmptyView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.layout_empty,this);
        emptyImage = (ImageView) view.findViewById(R.id.empty_img);
        emptyText = (TextView) view.findViewById(R.id.empty_text);
    }

    public void setEmptyImage(int imageRes) {
        emptyImage.setImageResource(imageRes);
    }

    public void setEmptyText(String text) {
        emptyText.setText(text);
    }
}
