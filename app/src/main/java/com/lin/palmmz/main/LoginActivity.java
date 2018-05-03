package com.lin.palmmz.main;

import android.graphics.Rect;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lin.palmmz.R;
import com.lin.palmmz.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 * @author lin
 * @date on 2018/01/20.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,View.OnFocusChangeListener , ViewTreeObserver.OnGlobalLayoutListener{

    private static final String TAG = "LoginActivity";

    @BindView(R.id.ly_retrieve_bar)
    LinearLayout mLayBackBar;

    @BindView(R.id.iv_login_logo)
    ImageView mIvLoginLogo;

    @BindView(R.id.ll_login_username)
    LinearLayout mLlLoginUsername;
    @BindView(R.id.et_login_username)
    EditText mEtLoginUsername;
    @BindView(R.id.iv_login_username_del)
    ImageView mIvLoginUsernameDel;

    @BindView(R.id.ll_login_pwd)
    LinearLayout mLlLoginPwd;
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;
    @BindView(R.id.iv_login_pwd_del)
    ImageView mIvLoginPwdDel;

    @BindView(R.id.iv_login_hold_pwd)
    ImageView mIvHoldPwd;
    @BindView(R.id.tv_login_forget_pwd)
    TextView mTvLoginForgetPwd;

    @BindView(R.id.bt_login_submit)
    Button mBtLoginSubmit;
    @BindView(R.id.bt_login_register)
    Button mBtLoginRegister;

    @BindView(R.id.ll_login_layer)
    View mLlLoginLayer;
    @BindView(R.id.ll_login_pull)
    LinearLayout mLlLoginPull;

    @BindView(R.id.ll_login_options)
    LinearLayout mLlLoginOptions;

    @BindView(R.id.ib_login_weibo)
    ImageView mIbLoginWeiBo;
    @BindView(R.id.ib_login_wx)
    ImageView mIbLoginWx;
    @BindView(R.id.ib_login_qq)
    ImageView mImLoginQq;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mEtLoginUsername.setOnFocusChangeListener(this);
        mEtLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String username = editable.toString().trim();
                if (username.length() > 0) {
                    mLlLoginUsername.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginUsernameDel.setVisibility(View.VISIBLE);
                } else {
                    mLlLoginUsername.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginUsernameDel.setVisibility(View.INVISIBLE);
                }

                String pwd = mEtLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd)) {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
            }
        });

        mEtLoginPwd.setOnFocusChangeListener(this);
        mEtLoginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0) {
                    mLlLoginPwd.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginPwdDel.setVisibility(View.VISIBLE);
                } else {
                    mIvLoginPwdDel.setVisibility(View.INVISIBLE);
                }

                String username = mEtLoginUsername.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(LoginActivity.this,R.string.message_username_null,Toast.LENGTH_SHORT).show();
                }
                String pwd = mEtLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd)) {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
            }
        });
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()){
            case R.id.et_login_username:
                if (hasFocus) {
                    mLlLoginUsername.setActivated(true);
                    mLlLoginPwd.setActivated(false);
                }
            case  R.id.et_login_pwd:
                if (hasFocus) {
                    mLlLoginUsername.setActivated(false);
                    mLlLoginPwd.setActivated(true);
                }
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    @Override
    public void onGlobalLayout() {
        ImageView ivLogo = this.mIvLoginLogo;
        Rect keypadRect = new Rect();
        mLayBackBar.getWindowVisibleDisplayFrame(keypadRect); //给keypadRect赋值

        int screenHeight = mLayBackBar.getRootView().getHeight();

        int keypadHeight = screenHeight - keypadRect.bottom;
        Log.e(TAG, "screenHeight: " + screenHeight + "keypadRect.bottom:" +  keypadRect.bottom  );
    }


    @OnClick({R.id.ib_navigation_back, R.id.et_login_username, R.id.et_login_pwd, R.id.tv_login_forget_pwd,
            R.id.iv_login_hold_pwd, R.id.bt_login_submit, R.id.bt_login_register, R.id.ll_login_pull, R.id.ib_login_csdn,
            R.id.ib_login_weibo, R.id.ib_login_wx, R.id.ib_login_qq, R.id.ll_login_layer,
            R.id.iv_login_username_del, R.id.iv_login_pwd_del, R.id.lay_login_container})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_login_username:
                mEtLoginPwd.clearFocus();
                mEtLoginUsername.setFocusableInTouchMode(true);
                mEtLoginUsername.requestFocus();
                break;
            case R.id.et_login_pwd:
                mEtLoginUsername.clearFocus();
                mEtLoginPwd.setFocusableInTouchMode(true);
                mEtLoginPwd.requestFocus();
                break;

            default:
                break;
        }
    }
}
