package com.newland.palm.ui.dynamic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newland.palm.R;

/**
 * @author lin
 * @version 2018/5/24 0024
 */
public class TabPickerView extends FrameLayout {

    private static final String TAG = "TabPickerView";

    private TextView mViewDone;
    private TextView mViewOperator;
    private RecyclerView mRecyclerActive;
    private RecyclerView mRecyclerInactive;
    private LinearLayout mLayoutWrapper;
    private RelativeLayout mLayoutTop;
    private LinearLayout mViewWrapper;
    private NestedScrollView mViewScroller;

    public TabPickerView(@NonNull Context context) {
        this(context,null);
    }

    public TabPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidgets();
    }

    private void initWidgets() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tab_picker,this,false);

        mRecyclerActive =  view.findViewById(R.id.view_recycler_active);
        mRecyclerInactive =  view.findViewById(R.id.view_recycler_inactive);
        mViewScroller =  view.findViewById(R.id.view_scroller);
        mLayoutTop =  view.findViewById(R.id.layout_top);
        mViewWrapper =  view.findViewById(R.id.view_wrapper);
        mViewDone =  view.findViewById(R.id.tv_done);
        mViewOperator =  view.findViewById(R.id.tv_operator);
        mLayoutWrapper =  view.findViewById(R.id.layout_wrapper);

        addView(view);
    }
}
