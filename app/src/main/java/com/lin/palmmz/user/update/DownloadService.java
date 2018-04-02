package com.lin.palmmz.user.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.lin.palmmz.R;
import com.lin.palmmz.main.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Administrator
 * @version 创建时间：2018/1/27 0027 下午 4:51
 */
public class DownloadService extends Service {

    private static final String TAG = "DownloadService";

    public static boolean isDownload = false;
    private String mUrl;
    private String mTitle = "正在下载";
    private String saveFileName = Environment.getExternalStorageDirectory() + "/palm/download/";

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    RemoteViews remoteViews;
    NotificationCompat.Builder mBuilder;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mNotificationManager.cancel(0);
                    installApk();
                    break;
                case 1:
                    int rate = msg.arg1;
                    if (rate < 100) {
                        remoteViews.setTextViewText(R.id.tv_download_progress, mTitle + "(" + rate
                                + "%" + ")");
                        remoteViews.setProgressBar(R.id.pb_progress, 100, rate, false);
                    } else {
                        // 下载完毕后变换通知形式
                        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                        mBuilder.setContent(null);
                        mNotification = mBuilder.build();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("completed", "yes");

                        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                    }
                    mNotificationManager.notify(0, mNotification);
                    break;
            }

        }
    };


    public static void startService(Context context, String downloadUrl) {
        Intent intent = new Intent(context,DownloadService.class);
        intent.putExtra("downloadUrl",downloadUrl);
        context.startService(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        isDownload = true;
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mUrl = intent.getStringExtra("downloadUrl");

        File file = new File(saveFileName);
        if (!file.exists()){
            file.mkdirs();
        }
        final File apkFile = new File(saveFileName + "palm.apk");
        setUpNotification();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    downloadUpdateFile(mUrl, apkFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private long downloadUpdateFile(String downloadUrl, File saveFile) throws Exception {
        int downloadCount = 0;
        int currentSize = 0;
        long totalSize = 0;
        int updateTotalSize = 0;

        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(20000);
            updateTotalSize = httpConnection.getContentLength();
            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }
            is = httpConnection.getInputStream();
            fos = new FileOutputStream(saveFile, false);
            byte buffer[] = new byte[2048];
            int readSize = 0;
            while ((readSize = is.read(buffer)) > 0) {

                fos.write(buffer, 0, readSize);
                totalSize += readSize;
                if ((downloadCount == 0)
                        || (int) (totalSize * 100 / updateTotalSize) - 4 > downloadCount) {
                    downloadCount += 4;
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = downloadCount;
                    mHandler.sendMessage(msg);
                }
            }

            mHandler.sendEmptyMessage(0);
            isDownload = false;

        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (is != null){
                is.close();
            }
            if (fos != null){
                fos.close();
            }
            stopSelf();
        }
        return totalSize;
    }

    private void setUpNotification() {
        remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification_view);
        remoteViews.setTextViewText(R.id.tv_download_progress,mTitle);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContent(remoteViews)
                .setWhen(System.currentTimeMillis())
                .setTicker("开始下载")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentIntent(pendingIntent);
        mNotification = mBuilder.build();
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;

        mNotificationManager.notify(0,mNotification);
    }

    private void installApk() {
        File file = new File(saveFileName + "palm.apk");
        if (!file.exists())
            return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.lin.palmmz.provider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isDownload = false;
    }


}
