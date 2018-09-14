package com.newland.lonely.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author lin
 * @version 2018/9/5 0005
 */
public class SolarSystemView extends View {


    public SolarSystemView(Context context) {
        this(context,null);
    }

    public SolarSystemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SolarSystemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
