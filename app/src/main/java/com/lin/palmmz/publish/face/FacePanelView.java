package com.lin.palmmz.publish.face;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lin.palmmz.R;
import com.lin.palmmz.publish.face.emoji.Emojicon;
import com.lin.palmmz.publish.face.emoji.DisplayRules;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class FacePanelView extends LinearLayout implements View.OnClickListener,
        FaceRecyclerView.OnFaceClickListener, SoftKeyboardUtil.IPanelHeightTarget{

    private ViewPager mPager;
    private FacePanelListener mListener;
    private boolean mKeyboardShowing;
    private AtomicBoolean mWillShowPanel = new AtomicBoolean();
    private int mRealHeight;

    public FacePanelView(Context context) {
        super(context);
        init();
    }

    public FacePanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FacePanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRealHeight = SoftKeyboardUtil.getMinPanelHeight(getResources());//获取软键盘的高度
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.lay_face_panel, this);
        mPager = (ViewPager) findViewById(R.id.view_pager);
        findViewById(R.id.tv_qq).setOnClickListener(this);
        findViewById(R.id.tv_emoji).setOnClickListener(this);
        findViewById(R.id.btn_del).setOnClickListener(this);

        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                final FaceRecyclerView view = createRecyclerView();
                bindRecyclerViewData(view, position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                if (object instanceof FaceRecyclerView) {
                    container.removeView((FaceRecyclerView) object);
                }
            }
        });

        // init soft keyboard helper
        SoftKeyboardUtil.attach((Activity) getContext(), this);
    }

    protected FaceRecyclerView createRecyclerView() {
        return new FaceRecyclerView(getContext(), this);
    }

    protected FaceRecyclerView bindRecyclerViewData(FaceRecyclerView view, final int position) {
        view.setData(DisplayRules.getAllByType(position));
        return view;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_qq:
                mPager.setCurrentItem(0);
                break;
            case R.id.tv_emoji:
                mPager.setCurrentItem(1);
                break;
            case R.id.btn_del:
                onDeleteClick();
                break;
        }
    }

    protected void onDeleteClick() {
        FacePanelListener listener = mListener;
        if (listener != null)
            listener.onDeleteClick();
    }

    /**
     * 表情点击接口
     * @param v
     */
    @Override
    public void onFaceClick(Emojicon v) {
        FacePanelListener listener = mListener;
        if (listener != null)
            listener.onFaceClick(v);
    }

    /**
     * 软键盘
     * @param panelHeight
     */
    @Override
    public void refreshHeight(int panelHeight) {
        mRealHeight = panelHeight;
    }

    @Override
    public int getPanelHeight() {
        return mRealHeight;
    }

    @Override
    public void onKeyboardShowing(boolean showing) {
        mKeyboardShowing = showing;
        if (showing) {
            hidePanel();
        } else if (mWillShowPanel.getAndSet(false)) {
            openPanel();
        }
    }

    public boolean isShow() {
        return getVisibility() != GONE;
    }

    public void hidePanel() {
        setVisibility(GONE);
    }

    public void openPanel() {
        if (mKeyboardShowing) {
            mWillShowPanel.set(true);
            FacePanelListener listener = mListener;
            if (listener != null)
                listener.hideSoftKeyboard();
        } else {
            setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mKeyboardShowing) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.getMode(MeasureSpec.EXACTLY));
        } else {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mRealHeight,
                    MeasureSpec.getMode(MeasureSpec.EXACTLY));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 删除表情按键 监听
     * @param listener
     */
    public void setListener(FacePanelListener listener) {
        mListener = listener;
    }

    public interface FacePanelListener extends FaceRecyclerView.OnFaceClickListener {
        void onDeleteClick();

        void hideSoftKeyboard();
    }
}
