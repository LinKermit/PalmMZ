package com.newland.palm.ui.dynamic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newland.palm.R;
import com.newland.palm.base.BaseFragment;
import com.newland.palm.data.bean.SubTab;
import com.newland.palm.ui.AppOperator;
import com.newland.palm.utils.LogUtils;
import com.newland.palm.utils.StreamUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lin on 2017/11/18.
 * 1-资讯
 */

public class DynamicFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = "DynamicFragment";

    @BindView(R.id.layout_tab)
    TabLayout mLayoutTab;
    @BindView(R.id.view_tab_picker)
    TabPickerView mViewTabPicker;

    @BindView(R.id.iv_arrow_down)
    ImageView mViewArrowDown;

    TabPickerView.TabPickerDataManager mTabPickerDataManager;
    List<SubTab> subTabs;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Log.e(TAG, "initWidget: "  );

        mViewTabPicker.setTabPickerManager(initTabPickerManager());
        mViewTabPicker.setTabPickingListener(new TabPickerView.OnTabPickingListener() {

            private boolean isChangeIndex = false;
            @Override
            public void onSelected(int position) {
                //跳转主界面position tab
                mViewArrowDown.setRotation(0);
            }

            @Override
            public void onRemove(int position, SubTab tab) {
                isChangeIndex = true;
            }

            @Override
            public void onInsert(SubTab tab) {
                isChangeIndex = true;
            }

            @Override
            public void onMove(int op, int np) {
                isChangeIndex = true;
            }

            @Override
            public void onRestore(final List<SubTab> activeTabs) {
                if (!isChangeIndex){
                    return;
                }
                AppOperator.runOnThread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStreamWriter writer = null;
                        try {
                            writer = new OutputStreamWriter(
                                    mContext.openFileOutput("sub_tab_active.json", Context.MODE_PRIVATE)
                                    , "UTF-8");
                            new Gson().toJson(activeTabs,writer);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            StreamUtil.close(writer);
                        }
                    }
                });

                isChangeIndex = false;
                subTabs.clear();
                subTabs.addAll(activeTabs);
//                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 初始化TabPick 数据
     * @return
     */
    private TabPickerView.TabPickerDataManager initTabPickerManager() {
        if (mTabPickerDataManager == null){
            mTabPickerDataManager = new TabPickerView.TabPickerDataManager() {
                @Override
                public List<SubTab> setOriginalDataSet() {
                    InputStreamReader reader = null;
                    try {
                        reader = new InputStreamReader(mContext.getAssets().open("sub_tab_original.json"), "UTF-8");
                        return new Gson().fromJson(reader,new TypeToken<List<SubTab>>(){}.getType());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        StreamUtil.close(reader);
                    }
                    return null;
                }

                @Override
                public List<SubTab> setActiveDataSet() {
                    FileReader reader = null;
                    try {
                        File file = mContext.getFileStreamPath("sub_tab_active.json");
                        if (!file.exists()) return null;
                        reader = new FileReader(file);
                        List<SubTab> subTabs = new Gson().fromJson(reader,new TypeToken<ArrayList<SubTab>>() {}.getType());
                        return subTabs;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        StreamUtil.close(reader);
                    }
                    return null;
                }

                @Override
                public void restoreActiveDataSet(List<SubTab> mActiveDataSet) {
                    OutputStreamWriter writer = null;
                    try {
                        writer = new OutputStreamWriter(mContext.openFileOutput("sub_tab_active.json", Context.MODE_PRIVATE)
                                ,"UTF-8");
                        new Gson().toJson(mActiveDataSet,writer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        StreamUtil.close(writer);
                    }

                }
            };
        }
        return mTabPickerDataManager;
    }


    @OnClick(R.id.iv_arrow_down)
    @Override
    public void onClick(View view) {
        if (mViewArrowDown.getRotation() != 0){
            if (!mViewTabPicker.onTurnBack()){
                return;
            }
            mViewArrowDown.animate().rotation(-180).setDuration(380).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mViewArrowDown.setRotation(0);
                    mViewArrowDown.setEnabled(true);
                }
            });
        }else {
            mViewTabPicker.show(mLayoutTab.getSelectedTabPosition());
            mViewArrowDown.animate().rotation(225).setDuration(380).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mViewArrowDown.setRotation(45);
                    mViewArrowDown.setEnabled(true);
                }
            });
        }
    }
}
