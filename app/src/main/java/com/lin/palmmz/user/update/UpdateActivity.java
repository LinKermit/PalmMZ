package com.lin.palmmz.user.update;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lin.palmmz.BuildConfig;
import com.lin.palmmz.R;
import com.lin.palmmz.base.BaseActivity;
import com.lin.palmmz.net.bean.Version;
import com.lin.palmmz.util.DialogHelper;
import com.lin.palmmz.util.TDevice;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author Administrator
 * @version 创建时间：2018/1/27 0027 下午 4:51
 */
public class UpdateActivity extends BaseActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks{

    @BindView(R.id.tv_update_info)
    TextView mTextUpdateInfo;

    //存储权限
    private static final int RC_EXTERNAL_STORAGE = 0x04;
    private Version.ResultBean mVersion;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_update;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        mVersion = (Version.ResultBean) getIntent().getSerializableExtra("version");
        mTextUpdateInfo.setText(mVersion.getMessage());
    }

    public static void show(Activity mContext, Version.ResultBean version) {
        Intent intent = new Intent(mContext,UpdateActivity.class);
        intent.putExtra("version",version);
        mContext.startActivity(intent);
    }


    @OnClick({R.id.btn_update, R.id.btn_close,R.id.btn_not_show})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_update:
                if (!TDevice.isWifiOpen()){
                    DialogHelper.getConfirmDialog(this, "当前非wifi环境，是否升级？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            requestExternalStorage();
                            finish();
                        }
                    }).show();
                }
                requestExternalStorage();
                finish();
                break;
            case R.id.btn_close:
                finish();
                break;
            case R.id.btn_not_show:
                //不再提示更新，本地版本变更为BuildConfig.VERSION_CODE
                UpdateSharedPreference.getInstance().putShowUpdate(false);
                UpdateSharedPreference.getInstance().putUpdateVersion(BuildConfig.VERSION_CODE);
                finish();
                break;
            default:
                break;
        }
    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    private void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            DownloadService.startService(this, mVersion.getDownloadUrl());
        }else {
            EasyPermissions.requestPermissions(this, "需要读取存储权限", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        DialogHelper.getConfirmDialog(this, "温馨提示", "需要开启对您手机的存储权限才能下载安装，是否现在开启", "去开启", "取消", true, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
            }
        }, null).show();
    }
}
