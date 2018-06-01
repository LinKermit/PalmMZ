package com.newland.palm.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.newland.palm.R;


/**
 * @author lin
 * @version 2018/5/31 0031
 * Fragment 标题
 */
public class TitleBar extends FrameLayout {

    private TextView mTitle;
    private ImageView mIcon;

    public TitleBar(@NonNull Context context) {
        this(context, null);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.lay_title_bar,this,true);//layout 为 merge
        mTitle = findViewById(R.id.tv_title);
        mIcon = findViewById(R.id.iv_icon);
        if (attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.TitleBar,defStyleAttr,0);

            String title = a.getString(R.styleable.TitleBar_aTitle);
            Drawable drawable = a.getDrawable(R.styleable.TitleBar_aIcon);
            mTitle.setText(title);
            mIcon.setImageDrawable(drawable);
        }else {
            mIcon.setVisibility(GONE);
        }

        setBackgroundColor(getResources().getColor(R.color.main_green));
    }

    public void setTitle(int titleRes){
        if (titleRes <= 0){
            return;
        }
        mTitle.setText(titleRes);
    }

    public void setIcon(int iconRes) {
        if (iconRes <= 0) {
            mIcon.setVisibility(GONE);
            return;
        }
        mIcon.setImageResource(iconRes);
        mIcon.setVisibility(VISIBLE);
    }
}
