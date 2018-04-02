package com.lin.palmmz.main.notice;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.lin.palmmz.R;
import com.lin.palmmz.main.MainActivity;
import com.lin.palmmz.net.RetrofitHelper;
import com.lin.palmmz.net.bean.MessageList;
import com.lin.palmmz.util.LogUtils;
import com.lin.palmmz.user.AccountHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author LIN
 * 定时去服务器读取notice的服务
 */

public class NoticeServer extends Service {

    private static final String TAG = NoticeServer.class.getName();

    /**
     * 服务收到新消息，发出刷新通知
     */
    public static final String FLAG_BROADCAST_REFRESH = TAG + "_BROADCAST_REFRESH";

    private static final String FLAG_ACTION_FIRST = TAG + "_FIRST";//服务
    private static final String FLAG_ACTION_CLEAR = TAG + "_CLEAR";
    private static final String FLAG_ACTION_REFRESH = TAG + "_REFRESH";
    private static final String FLAG_ACTION_EXIT = TAG + "_EXIT";

    private AlarmManager mAlarmMgr;
    //轮询间隔
    private final static int ALARM_INTERVAL_SECOND = 20000;

    /**
     * 刷新和清除
     */
    public static final String EXTRA_BEAN = "bean";
    private static final String EXTRA_TYPE = "type";
    /**
     * 开启服务
     * @param context
     */
    public static void startAction(Context context){
        Intent intent = new Intent(context,NoticeServer.class);
        intent.setAction(FLAG_ACTION_FIRST);
        context.startService(intent);
    }

    /**
     * 清除服务器上notice数据
     * @param context
     * @param type
     */
    public static void clearAction(Context context, int type) {
        Intent intent = new Intent(context,NoticeServer.class);
        intent.setAction(FLAG_ACTION_FIRST);
        intent.putExtra(EXTRA_TYPE, type);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAlarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);//获取定时时钟服务
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null){
            return super.onStartCommand(null, flags, startId);
        }
        String action = intent.getAction();
        if (action != null){
            handleAction(action, intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void handleAction(String action, Intent intent) {
        if (FLAG_ACTION_FIRST.equals(action)){//第一次并注册Alarm和广播，并发送FLAG_ACTION_REFRESH，去服务器获取数据
            registerNextAlarm();
            sendBroadcastToManager(NoticeBean.getInstance(this));
        }else if (FLAG_ACTION_REFRESH.equals(action)){
            refreshNoticeForNet();
        }else if (FLAG_ACTION_CLEAR.equals(action)){

        }
    }

    private void registerNextAlarm() {
        cancelRequestAlarm();
        mAlarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + ALARM_INTERVAL_SECOND, ALARM_INTERVAL_SECOND, getOperationIntent());
        LogUtils.error("registerAlarmByInterval interval:" + ALARM_INTERVAL_SECOND);
    }

    private void cancelRequestAlarm() {
        mAlarmMgr.cancel(getOperationIntent());
    }

    private PendingIntent getOperationIntent() {
        Intent intent = new Intent(this, NoticeServer.class);
        intent.setAction(FLAG_ACTION_REFRESH);//服务受到消息去服务器获取notice
        return PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void sendBroadcastToManager(NoticeBean bean) {
        LogUtils.error("sendBroadcastToManager:" + bean.toString());
        Intent intent = new Intent(FLAG_BROADCAST_REFRESH);
        intent.putExtra(EXTRA_BEAN, bean);
        sendBroadcast(intent);
    }

    /**
     * 读取服务器notice数据
     */
    private void refreshNoticeForNet() {
        RetrofitHelper.getUserApi().getMessageListData(AccountHelper.getUser().getAccess_token())
                .enqueue(new Callback<MessageList>() {
                    @Override
                    public void onResponse(Call<MessageList> call, Response<MessageList> response) {
                        NoticeBean noticeBean = response.body().getNotice();
                        LogUtils.e(TAG,response.body().getMessageList().get(0).getFriendname());
                        LogUtils.e(TAG,response.body().getNotice().getAllCount()+"");
                        doNetFinish(noticeBean);
                    }

                    @Override
                    public void onFailure(Call<MessageList> call, Throwable t) {
                        doNetFinish(null);
                    }
                });
//        RetrofitHelper.getUserApi().getNoticeData(AccountHelper.getUser().getAccess_token())
//                .enqueue(new Callback<NoticeBean>() {
//                    @Override
//                    public void onResponse(Call<NoticeBean> call, Response<NoticeBean> response) {
//                        doNetFinish(response.body());
//                    }
//
//                    @Override
//                    public void onFailure(Call<NoticeBean> call, Throwable t) {
//                        doNetFinish(null);
//                    }
//                });
    }

    private void doNetFinish(NoticeBean bean) {
        LogUtils.e(TAG,"doNetFinish:" + (bean == null ? "null" : bean.toString()));
        if (bean != null ) {
            NoticeBean notice = NoticeBean.getInstance(this);
            if (bean.equals(notice)){
                return;
            }
            NoticeBean.save(this,bean);//存至本地？
            // Send to manager发送广播
            sendBroadcastToManager(notice);
            // Send to notification
            sendNotification(notice);
            // To register alarm重新注册Alarm
            registerNextAlarm();
        } else {
            // To register alarm
            registerNextAlarm();
        }
    }

    private final static int NOTIFY_ID = 0x11111111;

    @SuppressLint("StringFormatMatches")
    private void sendNotification(NoticeBean bean) {
        if (bean == null || bean.getAllCount() == 0) {
            clearNotification();
            return;
        }

        LogUtils.log("sendNotification:" + bean.toString());

        StringBuilder sb = new StringBuilder();
        if (bean.getMention() > 0) {
            sb.append(getString(R.string.mention_count, bean.getMention())).append(" ");
        }
        if (bean.getLetter() > 0) {
            sb.append(getString(R.string.letter_count, bean.getLetter())).append(" ");
        }
        if (bean.getReview() > 0) {
            sb.append(getString(R.string.review_count, bean.getReview()))
                    .append(" ");
        }
        if (bean.getFans() > 0) {
            sb.append(getString(R.string.fans_count, bean.getFans()));
        }
        if (bean.getLike() > 0) {
            sb.append(getString(R.string.like_count, bean.getLike()));
        }
        String content = sb.toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(MainActivity.ACTION_NOTICE);
        PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFY_ID, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this)
                .setTicker(content)
                .setContentTitle(getString(R.string.you_have_unread_messages, bean.getAllCount()))
                .setContentText(content)
                .setAutoCancel(true)
                .setOngoing(false)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_notification);

        Notification notification = builder.build();
        NotificationManagerCompat.from(this).notify(NOTIFY_ID, notification);
    }

    private void clearNotification() {
        NotificationManagerCompat.from(this).cancel(NOTIFY_ID);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
