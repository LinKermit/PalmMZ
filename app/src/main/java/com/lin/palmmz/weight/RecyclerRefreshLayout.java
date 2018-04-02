package com.lin.palmmz.weight;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.lin.palmmz.R;

/**
 * @author Administrator
 * @version 创建时间：2018/1/27 0027 下午 4:51
 */
public class RecyclerRefreshLayout extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener{

    private int mTouchSlop;
    private SuperRefreshLayoutListener listener;
    private RecyclerView mRecyclerView;

    private boolean mIsOnLoading = false;

    private boolean mCanLoadMore = true;

    private boolean mHasMore = true;
    /**
     * 点击时y坐标
     */
    private int mYDown;
    /**
     * 滑动结束时y坐标
     */
    private int mLastY;
    public RecyclerRefreshLayout(Context context) {
        this(context,null);
    }

    public RecyclerRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //触发移动事件的最小距离
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOnRefreshListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //RecyclerRefreshLayout父布局 获取子控件RecyclerView
        if (mRecyclerView == null){
            getRecyclerView();
        }
    }

    private void getRecyclerView() {
        if (getChildCount()>0){
            View childView = getChildAt(0);
            if (!(childView instanceof RecyclerView)){
                childView = findViewById(R.id.recyclerview);
            }
            if (childView != null && childView instanceof RecyclerView){
                mRecyclerView = (RecyclerView) childView;
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (canLoad() && mCanLoadMore) {
                            loadData();
                        }
                    }
                });
            }
        }
    }

    /**
     * 是否可以加载更多, 条件是到了最底部
     *
     * @return isCanLoad
     */
    private boolean canLoad() {
        return isScrollBottom() && !mIsOnLoading && isPullUp() && mHasMore;
    }

    /**
     * 判断是否拉倒底部
     * @return
     */
    private boolean isScrollBottom(){
        return (mRecyclerView!=null && mRecyclerView.getAdapter()!=null)
                && getLastVisiblePosition() == (mRecyclerView.getAdapter().getItemCount()-1);
    }

    /**
     * 获取RecyclerView可见的最后一项
     * @return 可见的最后一项position
     */
    private int getLastVisiblePosition() {
        int position;
        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = mRecyclerView.getLayoutManager().getItemCount() - 1;
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

    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
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

    /**
     * 设置正在加载
     * @param loading 设置正在加载
     */
    public void setOnLoading(boolean loading) {
        mIsOnLoading = loading;
        if (!mIsOnLoading) {
            mYDown = 0;
            mLastY = 0;
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mYDown = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = (int) ev.getRawY();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onRefresh() {
        if (listener !=null && !mIsOnLoading){
            listener.onRefreshing();
        }else {
            setRefreshing(false);
        }
    }



    /**
     * 是否可加载更多
     * @param mCanLoadMore 是否可加载更多
     */
    public void setCanLoadMore(boolean mCanLoadMore) {
        this.mCanLoadMore = mCanLoadMore;
    }

    /**
     * 加载结束记得调用
     */
    public void onComplete() {
        setOnLoading(false);
        setRefreshing(false);
        mHasMore = true;
    }

    public void setListener(SuperRefreshLayoutListener listener) {
        this.listener = listener;
    }
    /**
     * 下拉刷新、下拉加载更多
     */
    public interface SuperRefreshLayoutListener{
        void onRefreshing();
        void onLoadMore();
    }
}
