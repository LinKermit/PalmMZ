package com.lin.palmmz.main.nav;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.lin.palmmz.publish.PubActivity;
import com.lin.palmmz.base.BaseNavFragment;
import com.lin.palmmz.R;
import com.lin.palmmz.main.fragment.DynamicFragment;
import com.lin.palmmz.main.fragment.ExploreFragment;
import com.lin.palmmz.main.fragment.TweetViewFragment;
import com.lin.palmmz.main.fragment.UserInfoFragment;
import com.lin.palmmz.main.notice.NoticeBean;
import com.lin.palmmz.main.notice.NoticeManager;

import butterknife.BindView;

/**
 * Created by lin on 2017/11/17.
 */

public class NavFragment extends BaseNavFragment implements View.OnClickListener,View.OnLongClickListener,NoticeManager.NoticeNotify {

    @BindView(R.id.nav_item_news)
    NavigationButton mNavNews;
    @BindView(R.id.nav_item_tweet)
    NavigationButton mNavTweet;
    @BindView(R.id.nav_item_explore)
    NavigationButton mNavExplore;
    @BindView(R.id.nav_item_me)
    NavigationButton mNavMe;

    @BindView(R.id.nav_item_add)
    ImageView mNavPub;

    private Context mContext;
    private FragmentManager mFragmentManager;
    private int mContainerId;

    private NavigationButton mCurrentNavBt;//当前按钮，如果是当前按钮，传递按钮给MainActivity,Fragment做刷新处理，跳到一个item
    public static NavFragment newInstance(){
        return new NavFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void initWidget(View root) {
        mNavNews.init(R.drawable.tab_icon_new,
                R.string.nav_text_new,
                DynamicFragment.class);

        mNavTweet.init(R.drawable.tab_icon_tweet,
                R.string.nav_text_tweet,
                TweetViewFragment.class);

        mNavExplore.init(R.drawable.tab_icon_explore,
                R.string.nav_text_explore,
                ExploreFragment.class);

        mNavMe.init(R.drawable.tab_icon_me,
                R.string.nav_text_me,
                UserInfoFragment.class);

        mNavNews.setOnClickListener(this);
        mNavTweet.setOnClickListener(this);
        mNavExplore.setOnClickListener(this);
        mNavMe.setOnClickListener(this);
        mNavPub.setOnClickListener(this);
        //消息接口绑定
        NoticeManager.bindNotify(this);
    }

    //初始化
    public void setup(Context context, FragmentManager manager,int contentId,OnNavigationReselectListener listener){
        mContext = context;
        mFragmentManager = manager;
        mContainerId = contentId;
        mOnNavigationReselectListener = listener;

        doSelected(mNavNews);//默认第一页
    }

    @Override
    public void onClick(View view) {
        if (view instanceof NavigationButton){
            NavigationButton nav = (NavigationButton) view;
            doSelected(nav);
        }else if (view.getId() == R.id.nav_item_add){//发布
            PubActivity.show(mContext);
        }

    }

    NavigationButton oldNavButton = null;
    private void doSelected(NavigationButton newNavButton) {

        if (mCurrentNavBt != null){
            oldNavButton = mCurrentNavBt;

            if (oldNavButton == newNavButton) {
                if (mOnNavigationReselectListener != null){
                    mOnNavigationReselectListener.onReselect(newNavButton);
                }
                return;
            }
            oldNavButton.setSelected(false);
        }
        newNavButton.setSelected(true);
        doTabChange(oldNavButton,newNavButton);
        mCurrentNavBt = newNavButton;
    }

    private void doTabChange(NavigationButton oldNavButton, NavigationButton newNavButton) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldNavButton != null) {//再次点击时。把第一个fragment断开连接
            if (oldNavButton.getFragment() != null) {
                ft.detach(oldNavButton.getFragment());
            }
        }

        if (newNavButton != null){
            if (newNavButton.getFragment() == null){
                //Create a new instance of a Fragment with the given class name.
                //把实例化的fragment 放到了一个SimpleArrayMap集合//newNavButton.getClx().getName()加载的fragment
                Fragment fragment = Fragment.instantiate(mContext,newNavButton.getClx().getName(),null);
                ft.add(mContainerId,fragment,newNavButton.getTag());
                newNavButton.setFragment(fragment);
            }else {//获取不等null，直接把原来fragment提交
                ft.attach(newNavButton.getFragment());
            }
        }
        ft.commit();
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }


    /**
     * 第二次点击传递navigationButton
     */
    private OnNavigationReselectListener mOnNavigationReselectListener;

    public interface OnNavigationReselectListener{
        void onReselect(NavigationButton navigationButton);
    }


    @Override
    public void onNoticeArrived(NoticeBean bean) {
        mNavMe.showDot(bean.getAllCount());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeManager.unBindNotify(this);
    }
}
