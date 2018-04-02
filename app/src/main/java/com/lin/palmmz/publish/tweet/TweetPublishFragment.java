package com.lin.palmmz.publish.tweet;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.palmmz.R;
import com.lin.palmmz.base.BaseFragment;
import com.lin.palmmz.publish.face.FacePanelView;
import com.lin.palmmz.publish.face.emoji.Emojicon;
import com.lin.palmmz.publish.face.emoji.InputHelper;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lin on 2017/12/2.
 * 动态界面
 */

public class TweetPublishFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.edit_content)
    EditText mEditContent;

    @BindView(R.id.recycler_images)
    TweetPicturesPreviewer mLayImages;

    @BindView(R.id.txt_indicator)
    TextView mIndicator;

    @BindView(R.id.icon_back)
    ImageView mIconBack;

    @BindView(R.id.icon_send)
    Button mIconSend;

    @BindView(R.id.panel_face)
    FacePanelView mFacePanel;

    private static final int REQUEST_CODE_CHOOSE = 1;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_tweet_publish;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        isPrepared = false;
        //初始化数据
        mFacePanel.setListener(new FacePanelView.FacePanelListener() {
            @Override
            public void onDeleteClick() {
                InputHelper.backspace(mEditContent);
            }

            @Override
            public void hideSoftKeyboard() {
                TweetPublishFragment.this.hideSoftKeyboard();
            }

            @Override
            public void onFaceClick(Emojicon v) {
                InputHelper.input2OSC(mEditContent, v);
            }
        });

        mEditContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mFacePanel.hidePanel();
                return false;
            }
        });

        // Show keyboard
        showSoftKeyboard(mEditContent);
    }

    @OnClick({R.id.iv_picture, R.id.iv_mention, R.id.iv_tag,
            R.id.iv_emoji, R.id.txt_indicator, R.id.icon_back,
            R.id.icon_send, R.id.edit_content})
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_picture:
                hideAllKeyBoard();
                mLayImages.onLoadMoreClick();
                Matisse.from(this)
                        .choose(MimeType.allOf())
                        .theme(R.style.Matisse_Zhihu)
                        .countable(true)
                        .maxSelectable(9)
//                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            case R.id.iv_mention:
                hideAllKeyBoard();
//                    toSelectFriends();
                break;
            case R.id.iv_tag:
//                    toSelectTopic();
                break;
            case R.id.iv_emoji:
                handleEmojiClick(view);
                break;
//                case R.id.txt_indicator:
//                    handleClearContentClick();
//                    break;
//                case R.id.icon_back:
//                    mOperator.onBack();
//                    break;
//                case R.id.icon_send:
//                    mOperator.publish();
//                    break;
            case R.id.edit_content:
                mFacePanel.hidePanel();
                break;
        }
    }

    private void hideAllKeyBoard() {
        mFacePanel.hidePanel();
        hideSoftKeyboard();
    }

    private void hideSoftKeyboard() {
        mEditContent.clearFocus();
        ((InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                mEditContent.getWindowToken(), 0);
    }

    /**
     * Emoji 表情点击
     *
     * @param view View
     */
    private void handleEmojiClick(View view) {
        if (mFacePanel.isShow()) {
            mFacePanel.hidePanel();
            showSoftKeyboard(mEditContent);
        } else {
            mFacePanel.openPanel();
        }
    }

    private void showSoftKeyboard(final EditText requestView) {
        if (requestView == null)
            return;
        requestView.requestFocus();
        ((InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE)).showSoftInput(requestView,
                InputMethodManager.SHOW_FORCED);
    }



    List<Uri> mSelected;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.e("Matisse", "mSelected: " + mSelected);
            mLayImages.set(mSelected);
        }
    }
}
