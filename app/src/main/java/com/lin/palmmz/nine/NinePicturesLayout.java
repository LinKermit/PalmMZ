package com.lin.palmmz.nine;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.lin.palmmz.R;

/**
 * @author lin
 * @version 创建时间：2018/1/27 0027 下午 4:51
 */
public class NinePicturesLayout extends ViewGroup {
    /**
     * 垂直间隔
     */
    private float mVerticalSpacing;
    /**
     * 水平间隔
     */
    private float mHorizontalSpacing;
    /**
     * 行数
     */
    private int mColumn;
    /**
     * 图片最大个数
     */
    private int mMaxPictureSize;


    public NinePicturesLayout(Context context) {
        this(context,null);
    }

    public NinePicturesLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NinePicturesLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final float density = getResources().getDisplayMetrics().density;//分辨率转换
        int vSpace = (int) (4 * density);
        int hSpace = vSpace;
        if (attrs!=null){
            TypedArray a = context.obtainStyledAttributes(R.styleable.TweetPicturesLayout);
            vSpace = a.getDimensionPixelOffset(R.styleable.TweetPicturesLayout_verticalSpace,vSpace);
            hSpace = a.getDimensionPixelOffset(R.styleable.TweetPicturesLayout_horizontalSpace,hSpace);
            setColumn(a.getInt(R.styleable.TweetPicturesLayout_column, 3));
            setMaxPictureSize(a.getDimensionPixelOffset(R.styleable.NineGridImageView_maxSize,0));
            a.recycle();
        }
        setVerticalSpacing(vSpace);
        setHorizontalSpacing(hSpace);
    }

    /**
     * 设置行数
     * @param column
     */
    private void setColumn(int column) {
        if (column < 1){
            column = 1;
        }
        if (column > 20){
            column = 20;
        }
        mColumn = column;
    }

    //设置垂直间隔
    public void setVerticalSpacing(float mVerticalSpacing) {
        this.mVerticalSpacing = mVerticalSpacing;
    }
    //设置水平间隔
    public void setHorizontalSpacing(float mHorizontalSpacing) {
        this.mHorizontalSpacing = mHorizontalSpacing;
    }

    //设置图片最大个数
    public void setMaxPictureSize(int mMaxPictureSize) {
        if (mMaxPictureSize < 0){
            mMaxPictureSize = 0;
        }
        this.mMaxPictureSize = mMaxPictureSize;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        float childCount = getChildCount();
        if (childCount > 0){
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            if (childCount == 1){
                View childView = getChildAt(0);
                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();
                childView.layout(paddingLeft,paddingTop,paddingLeft+childWidth,paddingTop+childHeight);
            }else {
                int mWidth = r - l;//父布局的宽度
                int paddingRight = getPaddingRight();
                int lineHeight = 0;
                int childLeft = paddingLeft;
                int childTop = paddingTop;

                for (int i = 0; i < childCount; ++i) {
                    View childView = getChildAt(i);
                    if (childView.getVisibility() == GONE){
                        continue;
                    }

                    int childWidth = childView.getMeasuredWidth();
                    int childHeight = childView.getMeasuredHeight();
                    lineHeight = Math.max(childHeight,lineHeight);

                    if (paddingLeft + childWidth +paddingRight > mWidth){
                        childLeft = paddingLeft;
                        childTop += lineHeight + mVerticalSpacing;
                        lineHeight = childHeight;
                    }
                    childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                    childLeft += childWidth + mHorizontalSpacing;
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

    }

    public void setImage(String[] images){
        if (images == null && images.length ==0){
            return;
        }
        for (int i = 0; i < images.length; i++) {

        }
    }
}
