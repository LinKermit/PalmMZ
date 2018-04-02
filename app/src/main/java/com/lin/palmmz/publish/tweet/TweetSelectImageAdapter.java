package com.lin.palmmz.publish.tweet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lin.palmmz.R;
import com.lin.palmmz.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/5 0005.
 */

public class TweetSelectImageAdapter extends RecyclerView.Adapter<TweetSelectImageAdapter.TweetSelectImageHolder>{

    private final int MAX_SIZE = 9;
    private final int TYPE_NONE = 0;
    private final int TYPE_ADD = 1;
    List<Model>  mModels = new ArrayList<>();
    Callback mCallback;
    public static Context mContext;

    public TweetSelectImageAdapter(Callback callback,Context context) {
        this.mCallback = callback;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        int size = mModels.size();
        if (size >= MAX_SIZE){
            return TYPE_NONE;//无+
        }else if (size == position){
            return TYPE_ADD;
        }else {
            return TYPE_NONE;//最后一个为+
        }
    }

    @Override
    public TweetSelectImageHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tweet_publish_selecter, parent, false);
        if (viewType == TYPE_NONE){
            return new TweetSelectImageHolder(view, new TweetSelectImageHolder.HolderListener() {
                @Override
                public void onDelete(Model model) {
                    int pos = mModels.indexOf(model);
                    if (pos == -1){
                        return;
                    }
                    mModels.remove(pos);
                    if (mModels.size()>0){
                        notifyItemRemoved(pos);
                    }else {
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onDrag(TweetSelectImageHolder holder) {

                }

                @Override
                public void onClick(Model model) {
                    ToastUtils.showShort(parent.getContext(),model.path);
                }
            });
        }else {
            return new TweetSelectImageHolder(view, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Callback callback = mCallback;
                    if (callback!=null){
                        callback.onLoadMoreClick();
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(TweetSelectImageHolder holder, int position) {

        int size = mModels.size();
        if (size >= MAX_SIZE || size != position) {
            Model model = mModels.get(position);
            holder.bind(position, model);
        }
    }

    @Override
    public int getItemCount() {
        int size = mModels.size();
        if (size == MAX_SIZE) {
            return size;
        } else if (size == 0) {
            return 0;
        } else {
            return size + 1;
        }
    }


    //图片路径类
    public class Model{
        public Model(String path) {
            this.path = path;
        }
        public String path;
    }

    /**
     * 数据操作
     */
    public void clear() {
        mModels.clear();
    }

    public void add(Model model) {
        if (mModels.size() >= MAX_SIZE)
            return;
        mModels.add(model);
    }

    public void add(String path) {
        add(new Model(path));
    }

    public String[] getPaths() {
        int size = mModels.size();
        if (size == 0)
            return null;
        String[] paths = new String[size];
        int i = 0;
        for (Model model : mModels) {
            paths[i++] = model.path;
        }
        return paths;
    }

    public interface Callback {
        void onLoadMoreClick();

//        RequestManager getImgLoader();

        Context getContext();
    }

    static class TweetSelectImageHolder extends RecyclerView.ViewHolder{

        private ImageView mImage;
        private ImageView mDelete;
        private ImageView mGifMask;
        private HolderListener mListener;

        /**
         * 加载图片的构造器
         * @param itemView
         * @param listener
         */
        public TweetSelectImageHolder(View itemView,HolderListener listener) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.iv_content);
            mDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
            mGifMask = (ImageView) itemView.findViewById(R.id.iv_is_gif);
            this.mListener = listener;

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object obj = v.getTag();
                    final HolderListener holderListener = mListener;
                    if (holderListener != null && obj != null && obj instanceof TweetSelectImageAdapter.Model) {
                        holderListener.onDelete((TweetSelectImageAdapter.Model) obj);
                    }
                }
            });
            mImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final HolderListener holderListener = mListener;
                    if (holderListener != null) {
                        holderListener.onDrag(TweetSelectImageHolder.this);
                    }
                    return true;
                }
            });
            mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object obj = mDelete.getTag();
                    final HolderListener holderListener = mListener;
                    if (holderListener != null && obj != null && obj instanceof TweetSelectImageAdapter.Model) {
                        holderListener.onClick((TweetSelectImageAdapter.Model) obj);
                    }
                }
            });
            mImage.setBackgroundColor(0xffdadada);
        }

        /**
         * 添加构造器
         * @param itemView
         * @param clickListener
         */
        private TweetSelectImageHolder(View itemView, View.OnClickListener clickListener) {
            super(itemView);

            mImage = (ImageView) itemView.findViewById(R.id.iv_content);
            mDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
            mGifMask = (ImageView) itemView.findViewById(R.id.iv_is_gif);

            mDelete.setVisibility(View.GONE);
            mImage.setImageResource(R.mipmap.ic_tweet_add);
            mImage.setOnClickListener(clickListener);
            mImage.setBackgroundDrawable(null);
        }


        public void bind(int position, TweetSelectImageAdapter.Model model) {
            mDelete.setTag(model);
            // In this we need clear before load
            Glide.clear(mImage);
            // Load image
            if (model.path.toLowerCase().endsWith("gif")) {
                Glide.with(mContext).load(model.path)
                        .asBitmap()
                        .centerCrop()
                        .error(R.mipmap.ic_split_graph)
                        .into(mImage);
                // Show gif mask
                mGifMask.setVisibility(View.VISIBLE);
            } else {
                Glide.with(mContext).load(model.path)
                        .centerCrop()
                        .error(R.mipmap.ic_split_graph)
                        .into(mImage);
                mGifMask.setVisibility(View.GONE);
            }
        }


        /**
         * 内部按键监听,Holder 与Adapter之间的桥梁
         */
        interface HolderListener{

            void onDelete(TweetSelectImageAdapter.Model model);

            void onDrag(TweetSelectImageHolder holder);

            void onClick(TweetSelectImageAdapter.Model model);
        }
    }
}
