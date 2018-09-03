package com.newland.lonely;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.newland.lonely.base.BaseActivity;
import com.newland.lonely.data.bean.Entity;
import com.newland.lonely.data.bean.User;
import com.newland.lonely.data.net.RetrofitHelper;
import com.newland.lonely.operator.SpHelper;
import com.newland.lonely.utils.LogUtils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author lin
 * @version 2018/5/21 0021
 */

public class WebViewActivity extends BaseActivity {

    private static final String TAG = "WebViewActivity";
    public static final String ACTION_ACCOUNT_LOGIN_SUCC = "com.account.login.success";
    @BindView(R.id.webView)
    WebView webView;

    String url = "https://www.oschina.net/action/oauth2/authorize?response_type=code&client_id=Vn3CFh6ovf9pamtW7DRL&redirect_uri=https://my.oschina.net/u/3725152";

    public static void show(Context context){
        context.startActivity(new Intent(context,WebViewActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_web_view;
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        setStatusBarDarkMode();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                parseUrl(url);
                return true;
            }
        });
    }


    /**
     * 解析url，取code值，传code值取access_token，用access_token获取数据
     * @param url
     */
    private void parseUrl(String url) {
        if (url.contains("error")){
            finish();
        }else {
            int start = url.indexOf("code");
            int end = url.indexOf("state");
            String code = url.substring(start+5,end-1);
            if (!TextUtils.isEmpty(code)){
                getEntityFromNet(code);
            }
        }

    }

    private void getEntityFromNet(String code){

        RetrofitHelper.getEntityApi().getEntityData(code,"json").enqueue(new Callback<Entity>() {//异步
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> entity) {

                if (!TextUtils.isEmpty(entity.body().toString())){
                    LogUtils.e(TAG,entity.body().toString());
                    //根据access——token获取用户信息
                    getUserFromNet(entity.body().getAccess_token());
                }else {
                    LogUtils.e(TAG,getResources().getString(R.string.user_web_entity_error));
                }

            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                Toast.makeText(WebViewActivity.this,getString(R.string.main_net_error),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUserFromNet(final String access_token) {

        RetrofitHelper.getUserApi().getUserData(access_token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> user) {

                if (!TextUtils.isEmpty(user.body().toString())) {
                    LogUtils.e(TAG, user.body().getName());

                    user.body().setAccess_token(access_token);
                }else {
                    LogUtils.e(TAG,getResources().getString(R.string.user_web_user_error));
                }

                //存入SharedPreferences
                boolean mSave = SpHelper.saveObject(getApplicationContext(),user.body(), SpHelper.USER_KEY);
                if (mSave){
                    Toast.makeText(WebViewActivity.this,getResources().getString(R.string.user_web_login_succ),Toast.LENGTH_SHORT).show();
                    logSucceed();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                LogUtils.error(t.getMessage());
            }
        });
    }

    private void logSucceed() {
        sendLocalReceiver();//登录成功后，发送本地广播更新ui
    }

    private void sendLocalReceiver() {
        Intent intent = new Intent();
        intent.setAction(ACTION_ACCOUNT_LOGIN_SUCC);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
