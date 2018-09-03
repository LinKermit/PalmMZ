package com.newland.lonely.nav;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.newland.lonely.WebViewActivity;
import com.newland.lonely.pub.PubActivity;
import com.newland.lonely.R;
import com.newland.lonely.base.BaseFragment;
import com.newland.lonely.fragment.ExploreFragment;
import com.newland.lonely.fragment.SynthesizeFragment;
import com.newland.lonely.fragment.TweetPagerFragment;
import com.newland.lonely.user.UserInfoFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lin
 * @version 2018/8/24 0024
 */
public class NavFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.nav_item_news)
    NavigationButton mNavNews;
    @BindView(R.id.nav_item_tweet)
    NavigationButton mNavTweet;
    @BindView(R.id.nav_item_explore)
    NavigationButton mNavExplore;
    @BindView(R.id.nav_item_me)
    NavigationButton mNavMe;
    @BindView(R.id.nav_item_tweet_pub)
    ImageView mNavPub;
    private OnNavigationReselectListener mOnNavigationReselectListener;
    private FragmentManager mFragmentManager;
    private int mContainerId;
    private NavigationButton mCurrentNavButton = null;

    /**
     * 初始化
     * @param context
     * @param fragmentManager
     * @param contentId
     * @param listener
     */
    public void setup(Context context, FragmentManager fragmentManager, int contentId, OnNavigationReselectListener listener) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mContainerId = contentId;
        mOnNavigationReselectListener = listener;
        // do clear
//        clearOldFragment();
        // do select first
        doSelect(mNavNews);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void initWidget(View root) {

        super.initWidget(root);
        mNavNews.init(R.drawable.tab_icon_new,
                R.string.main_tab_name_news,
                SynthesizeFragment.class);

        mNavTweet.init(R.drawable.tab_icon_tweet,
                R.string.main_tab_name_tweet,
                TweetPagerFragment.class);

        mNavExplore.init(R.drawable.tab_icon_explore,
                R.string.main_tab_name_explore,
                ExploreFragment.class);

        mNavMe.init(R.drawable.tab_icon_me,
                R.string.main_tab_name_my,
                UserInfoFragment.class);
    }

    @OnClick({R.id.nav_item_news,R.id.nav_item_tweet,R.id.nav_item_explore,R.id.nav_item_me,R.id.nav_item_tweet_pub})
    @Override
    public void onClick(View view) {
        if (view instanceof NavigationButton){
            NavigationButton nav = (NavigationButton) view;
            doSelect(nav);
        }else {
            WebViewActivity.show(getContext());
        }
    }

    private void doSelect(NavigationButton nav) {
        NavigationButton oldNavButton = null;
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton;
            if (mCurrentNavButton == nav && mOnNavigationReselectListener != null) {
                mOnNavigationReselectListener.onReselect(nav);
                return;
            }
            mCurrentNavButton.setSelected(false);
        }
        nav.setSelected(true);
        doTabChanged(oldNavButton, nav);
        mCurrentNavButton = nav;
    }

    private void doTabChanged(NavigationButton oldNavButton, NavigationButton nav) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldNavButton != null && oldNavButton.getFragment() != null){
            ft.detach(oldNavButton.getFragment());
        }
        if (nav != null){
            if (nav.getFragment() == null) {
                Fragment fragment = instantiate(getContext(),nav.getCls().getName() ,null);
                ft.add(mContainerId,fragment, nav.getCls().getName());
                nav.setFragment(fragment);
            } else {
                ft.attach(nav.getFragment());
            }
        }
        ft.commit();
    }

    /**
     * 两次点击刷新
     */
    public interface OnNavigationReselectListener {
        void onReselect(NavigationButton navigationButton);
    }
}
