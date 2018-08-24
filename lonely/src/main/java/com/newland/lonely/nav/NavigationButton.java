package com.newland.lonely.nav;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.newland.lonely.R;

/**
 * @author lin
 * @version 2018/8/24 0024
 */
public class NavigationButton extends FrameLayout {


    public NavigationButton(@NonNull Context context) {
        this(context,null);
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_nav_item, this, true);

    }
}
