package com.lin.palmmz.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lin.palmmz.R;
import com.lin.palmmz.net.bean.Entity;
import com.lin.palmmz.net.bean.User;
import com.lin.palmmz.net.RetrofitHelper;
import com.lin.palmmz.util.DialogHelper;
import com.lin.palmmz.util.SharedPreferencesHelper;
import com.lin.palmmz.util.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";

    public final static String TITLE = "title";
    private ProgressDialog mDialog;
    private WebView webView;
    String url = "https://www.oschina.net/action/oauth2/authorize?response_type=code&client_id=Vn3CFh6ovf9pamtW7DRL&redirect_uri=https://my.oschina.net/u/3725152";

    public static void show(Context context){
       context.startActivity(new Intent(context,WebViewActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web1);

        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        LogUtils.e(TAG,"key======"+AccountHelper.getUser().getAccess_token());
        //设置页面自适应手机屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                pb.setProgress(newProgress);
//                if (newProgress >= 100) {
//                    pb.setVisibility(View.GONE);
//                }
//            }
//        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                LogUtils.e(TAG,url);
                //解析url，取出code值
//                showFocusWaitDialog(getResources().getString(R.string.logining));
                parseUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);//网页授权--url 返回code


    }

    /**
     * 解析url，取code值，传code值取access_token，用access_token获取数据
     * @param url
     */
    private void parseUrl(String url) {
        int start = url.indexOf("code");
        int end = url.indexOf("state");
        String code = url.substring(start+5,end-1);
        LogUtils.e(TAG,code);
        if (!TextUtils.isEmpty(code)){
            getEntityFromNet(code);
        }
    }

    private void getEntityFromNet(String code){//

        RetrofitHelper.getEntityApi().getEntityData(code,"json").enqueue(new Callback<Entity>() {//异步
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> entity) {

                if (!TextUtils.isEmpty(entity.body().toString())){
                    LogUtils.e(TAG,entity.body().toString());
                    //根据access——token获取用户信息
                    getUserFromNet(entity.body().getAccess_token());
                }else {
                    LogUtils.e(TAG,getResources().getString(R.string.getEntityError));
                }

            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                Toast.makeText(WebViewActivity.this,getString(R.string.netError),Toast.LENGTH_SHORT).show();
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
                    LogUtils.e(TAG,getResources().getString(R.string.getUserError));
                }

                //存入SharedPreferences
                boolean mSave = SharedPreferencesHelper.saveObject(getApplicationContext(),user.body(),SharedPreferencesHelper.USER_KEY);
                if (mSave){
                    Toast.makeText(WebViewActivity.this,getResources().getString(R.string.login_success),Toast.LENGTH_SHORT).show();
                    logSucceed();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    /**
     * 登录成功
     */
    private void logSucceed() {

        sendLocalReceiver();
/**
 *  setResult(RESULT_OK);
     sendLocalReceiver();
     //后台异步同步数据
     ContactsCacheManager.sync();
 */
//        showFocusWaitDialog(getResources().getString(R.string.login_success));

    }

    public static final String ACTION_ACCOUNT_FINISH_ALL = "com.lin.palmmz";
    private void sendLocalReceiver() {
        Intent intent = new Intent();
        intent.setAction(ACTION_ACCOUNT_FINISH_ALL);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);//本地发送一个登录注册完成广播
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected ProgressDialog showFocusWaitDialog(String message) {

        if (mDialog == null) {
            mDialog = DialogHelper.getProgressDialog(this, message, false);//DialogHelp.getWaitDialog(this, message);
        }
        mDialog.show();

        return mDialog;
    }

    /**
     * hide waitDialog
     */
    protected void hideWaitDialog() {
        ProgressDialog dialog = mDialog;
        if (dialog != null) {
            mDialog = null;
            try {
                dialog.cancel();
                // dialog.dismiss();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideWaitDialog();
    }
}
