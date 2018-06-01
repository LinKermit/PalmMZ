package com.newland.palm.main.nav;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.newland.palm.R;
import com.newland.palm.base.BaseFragment;
import com.newland.palm.main.dynamic.DynamicFragment;
import com.newland.palm.main.explore.ExploreFragment;
import com.newland.palm.main.publish.PublishActivity;
import com.newland.palm.main.tweet.TweetFragment;
import com.newland.palm.main.user.UserFragment;
import com.newland.palm.utils.LogUtils;

import butterknife.BindView;

/**
 * @author lin
 * @version 2018/5/17 0017
 */
public class NavFragment extends BaseFragment implements View.OnClickListener,View.OnLongClickListener{

    private static final String TAG = "NavFragment";
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

    private Context context;
    private FragmentManager fragmentManager;
    private int contentId;

    /**
     * 初始化fragment
     * @param context
     * @param fragmentManager
     * @param contentId
     * @param listener
     */
    public void init(Context context, FragmentManager fragmentManager, int contentId ,OnNavigationReselectListener listener){
        this.context =  context;
        this.fragmentManager = fragmentManager;
        this.contentId = contentId;
        this.listener = listener;

        doSelected(mNavNews);//默认第一页
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        mNavNews.init(R.drawable.tab_icon_new,
                R.string.nav_text_new,
                DynamicFragment.class);

        mNavTweet.init(R.drawable.tab_icon_tweet,
                R.string.nav_text_tweet,
                TweetFragment.class);

        mNavExplore.init(R.drawable.tab_icon_explore,
                R.string.nav_text_explore,
                ExploreFragment.class);

        mNavMe.init(R.drawable.tab_icon_me,
                R.string.nav_text_me,
                UserFragment.class);

        mNavNews.setOnClickListener(this);
        mNavTweet.setOnClickListener(this);
        mNavExplore.setOnClickListener(this);
        mNavMe.setOnClickListener(this);
        mNavPub.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view instanceof NavigationButton){
            NavigationButton button = (NavigationButton) view;
            doSelected(button);
        }else if (view.getId() == R.id.nav_item_add){
            PublishActivity.show(context);
        }
    }

    NavigationButton currentButton;
    NavigationButton oldButton;

    private void doSelected(NavigationButton navButton) {
        if (currentButton != null){
            oldButton = currentButton;
            oldButton.setSelected(false);

            if (oldButton == navButton){
                LogUtils.e(TAG, "doSelected: " + "the same");
                if (listener != null){
                    listener.onReselect(navButton);
                }
            }
        }
        navButton.setSelected(true);
        doTabChange(oldButton,navButton);
        currentButton = navButton;
    }

    /**
     * 选择fragment界面，每次点击都会初始化
     * @param oldButton
     * @param navButton
     */
    private void doTabChange(NavigationButton oldButton, NavigationButton navButton) {
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (oldButton != null){
            if (oldButton.getFragment() != null){
                ft.detach(oldButton.getFragment());//解绑
            }
        }
        if (navButton != null){
            Fragment fragment = Fragment.instantiate(context,navButton.getmTag(),null);
            ft.add(contentId,fragment,navButton.getmTag());
            navButton.setFragment(fragment);
        }else {
            ft.detach(navButton.getFragment());
        }
        
        ft.commitAllowingStateLoss();
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    private OnNavigationReselectListener listener;

    /**
     * 两次点击同一个button 接口
     */
    public interface OnNavigationReselectListener {
        void onReselect(NavigationButton navigationButton);
    }
}
