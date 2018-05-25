package com.newland.palm.ui.dynamic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.print.PrinterId;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newland.palm.R;
import com.newland.palm.data.bean.SubTab;

import java.util.List;

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

    private int mSelectedIndex = 0;
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

    private void initRecyclerView() {
        if (mRecyclerActive.getAdapter() != null && mRecyclerInactive.getAdapter()!=null){
            return;
        }


    }


    /**
     * tab ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mViewTab;
        TextView mViewBubble;
        ImageView mViewDel;
        public ViewHolder(View view) {
            super(view);

            mViewTab = view.findViewById(R.id.tv_content);
            mViewBubble =  view.findViewById(R.id.tv_bubble);
            mViewDel =  view.findViewById(R.id.iv_delete);
        }
    }

    /**
     * 设置数据
     */
    public abstract static class TabPickerDataManager{


        public List<SubTab> mActiveDataSet;
        public List<SubTab> mOriginalDataSet;

        public List<SubTab> mInactiveDataSet;

        public TabPickerDataManager(){
            mActiveDataSet = setActiveDataSet();
            mOriginalDataSet = setOriginalDataSet();

            if (mOriginalDataSet == null || mOriginalDataSet.size() == 0) {
                throw new RuntimeException("Original Data Set can't be null or empty");
            }
        }

        public List<SubTab> getActiveDataSet() {
            return mActiveDataSet;
        }
        public List<SubTab> getInactiveDataSet() {
            return mInactiveDataSet;
        }
        public List<SubTab> getOriginalDataSet() {
            return mOriginalDataSet;
        }

        public abstract List<SubTab> setOriginalDataSet();

        public abstract List<SubTab> setActiveDataSet();

    }

    /**
     * 显示
     * @param selectedIndex
     */
    public void show(int selectedIndex) {

        setVisibility(VISIBLE);
        mSelectedIndex = selectedIndex;
        mLayoutTop.setAlpha(0);
        mLayoutTop.animate().alpha(1).setDuration(380).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mLayoutTop.setAlpha(1);
            }
        });

        mViewScroller.setTranslationY(-mViewScroller.getHeight());
        mViewScroller.animate().translationY(0).setDuration(380).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mViewScroller.setTranslationY(0);
            }
        });
    }

    /**
     * 隐藏
     * @return
     */
    public boolean onTurnBack() {

        return false;
    }
}
