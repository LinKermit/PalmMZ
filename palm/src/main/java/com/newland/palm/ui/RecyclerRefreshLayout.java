package com.newland.palm.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


/**
 * @author lin
 * @version 2018/5/30 0030
 */
public class RecyclerRefreshLayout extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener{

    //滑动的最小距离
    private int mTouchSlop;
    private RecyclerView mRecycleView;
    private int yDown;
    private int yLast;


    private boolean mCanLoadMore = true;

    private boolean mHasMore = true;

    private boolean mIsOnLoading = false;

    private SuperRefreshLayoutListener listener;

    public RecyclerRefreshLayout(@NonNull Context context) {
        this(context,null);
    }

    public RecyclerRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOnRefreshListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mRecycleView == null){
            if (getChildCount() > 0){
                View childView = getChildAt(0);
                if (childView != null && childView instanceof RecyclerView){
                    mRecycleView = (RecyclerView) childView;

                    mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (canLoad() && mCanLoadMore){
                                loadData();
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * 是否可以加载更多, 条件是到了最底部
     * @return
     */
    private boolean canLoad() {
        return isScrollBottom() && !mIsOnLoading && isPullUp() && mHasMore;
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isScrollBottom() {
        return (mRecycleView != null && mRecycleView.getAdapter() != null)
                && getLastVisiblePosition() == (mRecycleView.getAdapter().getItemCount() - 1);
    }

    /**
     * 判断是否上拉
     * @return
     */
    private boolean isPullUp(){
        return (yDown - yLast) >= mTouchSlop;
    }


    /**
     * 获取RecyclerView可见的最后一项
     *
     * @return 可见的最后一项position
     */
    public int getLastVisiblePosition() {
        int position;
        if (mRecycleView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) mRecycleView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecycleView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) mRecycleView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecycleView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mRecycleView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = mRecycleView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     * @param positions 获得最大的位置
     * @return 获得最大的位置
     */
    private int getMaxPosition(int[] positions) {
        int maxPosition = Integer.MIN_VALUE;
        for (int position : positions) {
            maxPosition = Math.max(maxPosition, position);
        }
        return maxPosition;
    }

    /**
     * 是否可加载更多 --- 外部
     * @param mCanLoadMore 是否可加载更多
     */
    public void setCanLoadMore(boolean mCanLoadMore) {
        this.mCanLoadMore = mCanLoadMore;
    }

    /**
     * 设置正在加载
     *
     * @param loading 设置正在加载
     */
    public void setOnLoading(boolean loading) {
        mIsOnLoading = loading;
        if (!mIsOnLoading) {
            yDown = 0;
            yLast = 0;
        }
    }

    @Override
    public void onRefresh() {
        if (listener != null && !mIsOnLoading) {
            listener.onRefreshing();
        } else{
            setRefreshing(false);
        }
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (listener != null) {
            setOnLoading(true);
            listener.onLoadMore();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case KeyEvent.ACTION_DOWN:
                yDown = (int) ev.getRawY();
                break;
            case KeyEvent.ACTION_UP:
                yLast = (int) ev.getRawY();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 添加加载和刷新
     *
     * @param listener add the listener for SuperRefreshLayout
     */
    public void setSuperRefreshLayoutListener(SuperRefreshLayoutListener listener) {
        this.listener = listener;
    }

    public interface SuperRefreshLayoutListener {
        void onRefreshing();

        void onLoadMore();
    }

}
