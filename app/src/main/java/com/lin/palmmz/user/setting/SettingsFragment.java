package com.lin.palmmz.user.setting;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lin.palmmz.AppContext;
import com.lin.palmmz.R;
import com.lin.palmmz.main.back.UIHelper;
import com.lin.palmmz.base.BaseFragment;
import com.lin.palmmz.user.update.CheckUpdateManager;
import com.lin.palmmz.weight.ToggleButton;
import com.lin.palmmz.user.AccountHelper;
import com.lin.palmmz.util.Constants;
import com.lin.palmmz.util.DataCleanManager;
import com.lin.palmmz.util.DialogHelper;
import com.lin.palmmz.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class SettingsFragment extends BaseFragment implements View.OnClickListener,EasyPermissions.PermissionCallbacks{

    @BindView(R.id.tv_cache_size)
    TextView mTvCacheSize;
    @BindView(R.id.rl_check_version)
    FrameLayout mRlCheck_version;
    @BindView(R.id.tb_double_click_exit)
    ToggleButton mTbDoubleClickExit;
    @BindView(R.id.setting_line_top)
    View mSettingLineTop;
    @BindView(R.id.setting_line_bottom)
    View mSettingLineBottom;
    @BindView(R.id.rl_cancel)
    FrameLayout mCancel;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_settings;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (AppContext.get(Constants.KEY_DOUBLE_CLICK_EXIT, true)) {
            mTbDoubleClickExit.setToggleOn();
        } else {
            mTbDoubleClickExit.setToggleOff();
        }
        calculateCacheSize();
    }

    private void calculateCacheSize() {
        String cacheSize = "0KB";
        try {
            cacheSize = DataCleanManager.getTotalCacheSize(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTvCacheSize.setText(cacheSize);
    }


    @OnClick({R.id.rl_clean_cache, R.id.rl_double_click_exit, R.id.rl_feedback,
            R.id.rl_about, R.id.rl_check_version, R.id.rl_cancel})

    @Override
    public void onClick(View v) {

        final int id = v.getId();
        switch (id) {
            case R.id.rl_clean_cache:
                onClickCleanCache();
                break;
            case R.id.rl_double_click_exit:
                mTbDoubleClickExit.toggle();
                break;
            case R.id.rl_feedback://反馈
                break;
            case R.id.rl_about:
                UIHelper.showAboutOSC(mContext);
                break;
            case R.id.rl_check_version:
                onClickUpdate();
                break;
            case R.id.rl_cancel:
                // 清理所有缓存
                DataCleanManager.clearAllCache(mContext);
                // 注销操作
                AccountHelper.logout(mCancel, new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        mTvCacheSize.setText("0KB");
                        ToastUtils.showShort(mContext,getString(R.string.logout_success_hint));
                        mCancel.setVisibility(View.INVISIBLE);
                        mSettingLineTop.setVisibility(View.INVISIBLE);
                        mSettingLineBottom.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 检查更新
     */
    private void onClickUpdate() {
        CheckUpdateManager manager = new CheckUpdateManager(mContext,true);
        manager.checkUpdate(true);
    }


    private void onClickCleanCache() {
        DialogHelper.getConfirmDialog(getActivity(), "是否清空缓存?", new DialogInterface.OnClickListener
                () {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataCleanManager.clearAllCache(mContext);
                mTvCacheSize.setText("0KB");
            }
        }).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean login = AccountHelper.isLogin();
        if (!login) {
            mCancel.setVisibility(View.INVISIBLE);
            mSettingLineTop.setVisibility(View.INVISIBLE);
            mSettingLineBottom.setVisibility(View.INVISIBLE);
        } else {
            mCancel.setVisibility(View.VISIBLE);
            mSettingLineTop.setVisibility(View.VISIBLE);
            mSettingLineBottom.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        DialogHelper.getConfirmDialog(getActivity(), "温馨提示", "需要开启开源中国对您手机的存储权限才能下载安装，是否现在开启", "去开启", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
            }
        }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


}
