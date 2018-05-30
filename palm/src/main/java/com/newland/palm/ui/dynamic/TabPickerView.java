package com.newland.palm.ui.dynamic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.print.PrinterId;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newland.palm.R;
import com.newland.palm.data.bean.SubTab;
import com.newland.palm.utils.LogUtils;
import com.newland.palm.weight.MyGridLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author lin
 * @version 2018/5/24 0024
 * 添加tab 界面
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

    private TabAdapter mActiveAdapter;
    private TabAdapter mInactiveAdapter;

    /**
     * 当前页面
     */
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
        mViewDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewDone.getText().toString().equals("排序删除")) {
                    mActiveAdapter.startEditMode();
                } else {
                    mActiveAdapter.cancelEditMode();
                }
            }
        });
        addView(view);
    }

    private void initRecyclerView() {
        if (mRecyclerActive.getAdapter() != null && mRecyclerInactive.getAdapter()!=null){
            return;
        }

        mActiveAdapter = new TabAdapter(mTabManager.mActiveDataSet) {
            @NonNull
            @Override
            public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new TabViewHolder(View.inflate(parent.getContext(),R.layout.item_dynamic_tab,null));
            }

            @Override
            public void onBindViewHolder(@NonNull final TabViewHolder holder, final int position) {
                SubTab item = items.get(position);
                holder.mViewTab.setText(item.getName());
                if (item.isFixed()) {
                    holder.mViewTab.setActivated(false);
                } else {
                    holder.mViewTab.setActivated(true);
                }
                if (mSelectedIndex == position) {
                    holder.mViewTab.setSelected(true);
                } else {
                    holder.mViewTab.setSelected(false);
                }
                if (isEditMode() && !item.isFixed()) {
                    holder.mViewDel.setVisibility(VISIBLE);
                } else {
                    holder.mViewDel.setVisibility(GONE);
                }

                //删除
                holder.mViewDel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SubTab tab = mActiveAdapter.getItem(holder.getAdapterPosition());
                        if (tab == null || tab.isFixed()) return;
                        int oldCount = mActiveAdapter.getItemCount();
                        tab = mActiveAdapter.removeItem(holder.getAdapterPosition());
                        // 放到下面需要根据Original DataSet重排序
                        for (SubTab item : mTabManager.mOriginalDataSet) {
                            if (!item.getToken().equals(tab.getToken())) continue;
                            tab.setOrder(item.getOrder());
                            break;
                        }

                        int i = 0;
                        for (; i < mTabManager.mInactiveDataSet.size(); i++) {
                            SubTab item = mTabManager.mInactiveDataSet.get(i);
                            if (item.getOrder() < tab.getOrder()) continue;
                            break;
                        }
                        mTabManager.mInactiveDataSet.add(i, tab);
                        mInactiveAdapter.notifyItemInserted(i);

                        if (mSelectedIndex == holder.getAdapterPosition()) {
                            mSelectedIndex = holder.getAdapterPosition() == oldCount - 1 ? mSelectedIndex - 1 : mSelectedIndex;
                            mActiveAdapter.notifyItemChanged(mSelectedIndex);
                        } else if (mSelectedIndex > holder.getAdapterPosition()) {
                            --mSelectedIndex;
                            mActiveAdapter.notifyItemChanged(mSelectedIndex);
                        }
                        if (mTabPickingListener != null) {
                            mTabPickingListener.onRemove(holder.getAdapterPosition(), tab);
                        }
                    }
                });

                holder.mViewTab.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return false;
                    }
                });

                holder.mViewTab.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSelectedIndex = holder.getAdapterPosition();
                        hide();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        };

        mRecyclerActive.setAdapter(mActiveAdapter);
        mRecyclerActive.setLayoutManager(new MyGridLayoutManager(getContext(), 4));


        mInactiveAdapter = new TabAdapter(mTabManager.mInactiveDataSet) {
            @Override
            public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new TabViewHolder(View.inflate(parent.getContext(),R.layout.item_dynamic_tab,null));
            }

            @Override
            public void onBindViewHolder(@NonNull final TabViewHolder holder, int position) {
                holder.mViewTab.setText(items.get(position).getName());

                holder.mViewTab.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SubTab tab = mInactiveAdapter.removeItem(holder.getAdapterPosition());
                        mActiveAdapter.addItem(tab);
                        if (mTabPickingListener!=null){
                            mTabPickingListener.onRemove(holder.getAdapterPosition(),tab);
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        };
        mRecyclerInactive.setLayoutManager(new MyGridLayoutManager(getContext(), 4));
        mRecyclerInactive.setAdapter(mInactiveAdapter);
    }


    /**************************适配器-- 抽象类TabAdapter，不重写 Adapter的抽象方法**************************************/

    private abstract class TabAdapter extends RecyclerView.Adapter<TabAdapter.TabViewHolder>{

        List<SubTab> items;
        private boolean isEditMode = false;//编辑模式

        TabAdapter(List<SubTab> items) {
            this.items = items;
        }


        void startEditMode() {
            mViewOperator.setText("拖动排序");
            mViewDone.setText("完成");

            isEditMode = true;
            notifyDataSetChanged();
        }

        void cancelEditMode() {
            mViewOperator.setText("切换栏目");
            mViewDone.setText("排序删除");

            isEditMode = false;
            notifyDataSetChanged();
        }

        boolean isEditMode() {
            return isEditMode;
        }

        public SubTab removeItem(int i) {
            //recycler 中使用notifyItemRemoved(),已经在画面中不会再调用onBind，position 改为 holder.getAdapterPosition()
            SubTab subTab = items.remove(i);
            notifyItemRemoved(i);
            return subTab;
        }

        public void addItem(SubTab tab) {
            items.add(tab);
            notifyItemInserted(items.size() - 1);
        }

        SubTab getItem(int position) {
            if (position < 0 || position >= items.size()) return null;
            return items.get(position);
        }

        public class TabViewHolder extends RecyclerView.ViewHolder{
            TextView mViewTab;
            TextView mViewBubble;
            ImageView mViewDel;
            public TabViewHolder(View view) {
                super(view);

                mViewTab = view.findViewById(R.id.tv_content);
                mViewBubble =  view.findViewById(R.id.tv_bubble);
                mViewDel =  view.findViewById(R.id.iv_delete);

                mViewTab.setTextColor(new ColorStateList(new int[][]{
                                new int[]{-android.R.attr.state_activated},
                                new int[]{}
                        }, new int[]{0XFF24CF5F, 0XFF6A6A6A})
                );
                mViewTab.setActivated(true);

                mViewTab.setTag(this);
                mViewDel.setTag(this);
            }
        }
    }


    /****************************初始化TabPick数据 *****************************************/

    private TabPickerDataManager mTabManager;

    public void setTabPickerManager(TabPickerDataManager manager) {
        if (manager == null) return;
        mTabManager = manager;
        initRecyclerView();
    }

    public TabPickerDataManager getTabPickerManager() {
        return mTabManager;
    }
    /**
     * 设置数据
     */
    public abstract static class TabPickerDataManager{

        //界面上的
        public List<SubTab> mActiveDataSet;
        public List<SubTab> mOriginalDataSet;

        public List<SubTab> mInactiveDataSet;

        public TabPickerDataManager(){//1746762092
            mActiveDataSet = setActiveDataSet();
            mOriginalDataSet = setOriginalDataSet();

            mInactiveDataSet = new ArrayList<>();
            if (mOriginalDataSet == null || mOriginalDataSet.size() == 0) {
                throw new RuntimeException("Original Data Set can't be null or empty");
            }

            if (mActiveDataSet == null){
                mActiveDataSet = new ArrayList<>();
                for (SubTab item : mOriginalDataSet){
                    if (item.isIsActived() || item.isFixed()){
                        mActiveDataSet.add(item);
                    }
                }
                //本地保存界面上的item
                restoreActiveDataSet(mActiveDataSet);
            }
            LogUtils.e(TAG,mActiveDataSet.hashCode() +  "");
            for (SubTab item : mOriginalDataSet) {
                if (mActiveDataSet.contains(item)){
                    continue;
                }
                mInactiveDataSet.add(item);
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

        //设置全部数据，界面上数据，保存界面上的数据
        public abstract List<SubTab> setOriginalDataSet();
        public abstract List<SubTab> setActiveDataSet();
        public abstract void restoreActiveDataSet(List<SubTab> mActiveDataSet);

    }

    /**
     * 显示
     * @param selectedIndex
     */
    public void show(int selectedIndex) {

        setVisibility(VISIBLE);
        mSelectedIndex = selectedIndex;
        mActiveAdapter.notifyItemChanged(mSelectedIndex);
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
        if (mActiveAdapter.isEditMode){
            mActiveAdapter.cancelEditMode();
            return false;
        }
        if (getVisibility() == VISIBLE) {
            hide();
            return true;
        }
        return false;
    }

    private void hide() {
        if (mTabPickingListener != null) {
            mTabPickingListener.onRestore(mTabManager.mActiveDataSet);
            mTabPickingListener.onSelected(mSelectedIndex);
        }

        mLayoutTop.animate().alpha(0).setDuration(380).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(GONE);
            }
        });
        mViewScroller.animate().translationY(-mViewScroller.getHeight()).setDuration(380);
    }

    public void setTabPickingListener(OnTabPickingListener mTabPickingListener) {
        this.mTabPickingListener = mTabPickingListener;
    }

    private OnTabPickingListener mTabPickingListener;
    public interface OnTabPickingListener {
        /**
         * 单击选择某个tab
         * @param position select a tab
         */
        void onSelected(int position);

        /**
         * 删除某个tab
         * @param position the moved tab's position
         * @param tab      the moved tab
         */
        void onRemove(int position, SubTab tab);

        /**
         * 添加某个tab
         * @param tab the inserted tab
         */
        void onInsert(SubTab tab);

        /**
         * 交换tab
         * @param op      the mover's position
         * @param np      the swapper's position
         */
        void onMove(int op, int np);

        /**
         * 重新持久化活动的tabs
         * @param activeTabs the actived tabs
         */
        void onRestore(List<SubTab> activeTabs);
    }
}
