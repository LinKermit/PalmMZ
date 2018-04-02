package com.lin.palmmz.user.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.lin.palmmz.BuildConfig;
import com.lin.palmmz.net.bean.Version;
import com.lin.palmmz.net.RetrofitHelper;
import com.lin.palmmz.util.DialogHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Administrator
 * @version 创建时间：2018/1/27 0027 下午 4:51
 */
public class CheckUpdateManager {

    private Context mContext;
    private ProgressDialog mWaitDialog;//等待对话框
    private boolean mIsShowDialog;//是否展示对话框
    public CheckUpdateManager(Context context,boolean showWaitingDialog){
        this.mContext = context;
        mIsShowDialog = showWaitingDialog;
        if (showWaitingDialog){
            mWaitDialog = DialogHelper.getProgressDialog(mContext,"正在检查中...",true);
        }
    }


    public void checkUpdate(final boolean isHasShow){

        if (mIsShowDialog) {
            mWaitDialog.show();
        }

        RetrofitHelper.getUserApi().getVersionData().enqueue(new Callback<Version>() {
            @Override
            public void onResponse(Call<Version> call, Response<Version> response) {
                List<Version.ResultBean> versions = response.body().getResult();
                if (versions.size()>0){
                    Version.ResultBean version = versions.get(0);
                    int curVersion = BuildConfig.VERSION_CODE;
                    int code = Integer.parseInt(version.getCode());
                    if (curVersion< code){
                       if (UpdateSharedPreference.getInstance().getShowUpdate()&&isHasShow){
                           UpdateActivity.show((Activity) mContext, version);
                       }
                    }else {
                        if (mIsShowDialog){
                            DialogHelper.getProgressDialog(mContext,"已经是新版本了").show();
                        }
                    }
                }

                if (mWaitDialog != null) {
                    mWaitDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Version> call, Throwable t) {
                if (mIsShowDialog) {
                    DialogHelper.getMessageDialog(mContext, "网络异常，无法获取新版本信息").show();
                }
                if (mWaitDialog != null) {
                    mWaitDialog.dismiss();
                }
            }


        });
    }
}
