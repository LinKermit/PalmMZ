package com.lin.palmmz.nine;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lin.palmmz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lin
 * @version 创建时间：2018/1/27 0027 下午 4:51
 * 九宫格图片，T传递的是images 类型
 */
public class NineGridImageView<T> extends ViewGroup {

    public final static int STYLE_GRID = 0;     // 宫格布局
    public final static int STYLE_FILL = 1;     // 全填充布局


    private int mSpanType;                      // 跨行跨列的类型
    // 跨行跨列的类型
    public final static int NOSPAN = 0;         // 不跨行也不跨列
    public final static int TOPCOLSPAN = 2;     // 首行跨列
    public final static int BOTTOMCOLSPAN = 3;  // 末行跨列
    public final static int LEFTROWSPAN = 4;    // 首列跨行

    private int mRowCount;                      // 行数
    private int mColumnCount;                   // 列数

    private int mSingleImgSize;                 // 单张图片时的尺寸
    private int mGap;                           // 宫格间距
    private int mMaxSize;                       // 最大图片数
    private int mShowStyle;                     // 显示风格

    private List<ImageView> mImageViewList = new ArrayList<>();
    private List<T> mImgDataList;
    public NineGridImageView(Context context) {
        this(context ,null);
    }

    public NineGridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NineGridImageView);
        this.mSingleImgSize = a.getDimensionPixelSize(R.styleable.NineGridImageView_singleImgSize,-1);
        this.mGap = (int) a.getDimension(R.styleable.NineGridImageView_imgCap, 0);
        this.mShowStyle = a.getInt(R.styleable.NineGridImageView_showStyle, STYLE_GRID);
        this.mMaxSize = a.getInt(R.styleable.NineGridImageView_maxSize, 9);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int totalWidth = width - getPaddingLeft() - getPaddingRight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
       if (mImgDataList == null){
           return;
       }
    }

    /**
     * 外部设置images 接口
     * @param lists
     */
    public void setImages(List<T> lists) {
        setImagesData(lists, NOSPAN);
    }

    private void setImagesData(List<T> lists, int spanType) {

        if (lists == null||lists.isEmpty()){
            this.setVisibility(GONE);
            return;
        }else {
            this.setVisibility(VISIBLE);
        }
        this.mSpanType = spanType;
        //maxSize展示几张图
        int newShowCount = getNeedShowCount(lists.size());

        int[] gridParam = calculateGridParam(newShowCount, mShowStyle);
        mRowCount = gridParam[0];
        mColumnCount = gridParam[1];

//        if (mImgDataList == null) {
//            int i = 0;
//            while (i < newShowCount) {
//                ImageView iv = getImageView(i);
//                if (iv == null) {
//                    return;
//                }
//                addView(iv, generateDefaultLayoutParams());
//                i++;
//            }
//        }
    }

    private int getNeedShowCount(int size) {
        if (size>0 && size >mMaxSize){
            return mMaxSize;
        }else {
            return size;
        }
    }

    /**
     * 计算行 列数,
     * @param imagesSize 图片个数
     * @param showStyle GRID模式默认三列，FILL
     * @return
     */
    protected int[] calculateGridParam(int imagesSize, int showStyle) {
        int[] gridParam = new int[2];
        switch (showStyle) {
            case STYLE_FILL:
                generatUnitRowAndColumnForSpanType(imagesSize, gridParam);
                break;
            default:
            case STYLE_GRID:
                gridParam[0] = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
                gridParam[1] = 3;
        }
        return gridParam;
    }

    /**
     * 计算填充模式 行列个数
     * @param imagesSize
     * @param gridParam
     */
    private void generatUnitRowAndColumnForSpanType(int imagesSize, int[] gridParam) {
        if (imagesSize <= 2) {
            gridParam[0] = 1;
            gridParam[1] = imagesSize;
        } else if (imagesSize == 3) {
            switch (mSpanType) {
                case TOPCOLSPAN:    //2行2列,首行跨列
                case BOTTOMCOLSPAN: //2行2列,末行跨列
                case LEFTROWSPAN:   //2行2列,首列跨行
                    gridParam[0] = 2;
                    gridParam[1] = 2;
                    break;
                case NOSPAN:    //1行3列
                default:
                    gridParam[0] = 1;
                    gridParam[1] = 3;
                    break;
            }
        } else if (imagesSize <= 6) {
            switch (mSpanType) {
                case TOPCOLSPAN:    //3行3列,首行跨列
                case BOTTOMCOLSPAN: //3行3列,末行跨列
                case LEFTROWSPAN:   //3行3列,首列跨行
                    gridParam[0] = 3;
                    gridParam[1] = 3;
                    break;
                case NOSPAN:    //2行
                default:
                    gridParam[0] = 2;
                    gridParam[1] = imagesSize / 2 + imagesSize % 2;
                    break;
            }
        } else {
            gridParam[0] = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
            gridParam[1] = 3;
        }
    }
}
