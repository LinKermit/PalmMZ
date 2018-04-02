package com.lin.palmmz.main.notice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.lin.palmmz.user.AccountHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lin
 */

public class NoticeManager {

    /**
     * 清除服务器上某种类型的数据。clear调用
     */
    public static final int FLAG_CLEAR_MENTION = 0x00000001;
    public static final int FLAG_CLEAR_LETTER = 0x00000010;
    public static final int FLAG_CLEAR_REVIEW = 0x00000100;
    public static final int FLAG_CLEAR_FANS = 0x00001000;
    public static final int FLAG_CLEAR_LIKE = 0x00010000;
    public static final int FLAG_CLEAR_ALL = 0x00011111;

    private static NoticeManager manager;
    private NoticeManager(){
    }

    public static synchronized NoticeManager instance(){
        if (manager == null){
            manager = new NoticeManager();
        }
        return manager;
    }

    /**
     * 通知服务初始化，可开启服务
     * @param context
     */
    public static void init(Context context){
        if (!AccountHelper.isLogin()){
            return;
        }
        // 启动服务
        NoticeServer.startAction(context);
        //注册广播--用于接收notice服务的消息
        IntentFilter intentFilter = new IntentFilter(NoticeServer.FLAG_BROADCAST_REFRESH);
        context.registerReceiver(instance().mReceiver,intentFilter);
    }
    /**
     * 用于接收服务的消息
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent !=null){
                if (NoticeServer.FLAG_BROADCAST_REFRESH.equals(intent.getAction())){
                    NoticeBean noticeBean = (NoticeBean) intent.getSerializableExtra(NoticeServer.EXTRA_BEAN);
                    if (noticeBean != null){
                        onNoticeChanged(noticeBean);
                    }
                }
            }
        }
    };

    /**
     * 注销动态广播
     * @param context
     */
    public static void stopListen(Context context) {
        context.unregisterReceiver(instance().mReceiver);
    }


    /***
     * 取消消息变化监听
     * @param noticeNotify NoticeNotify
     */
    public static void unBindNotify(NoticeNotify noticeNotify) {
        instance().notifies.remove(noticeNotify);
    }

    /**
     * 实现类通过该方法绑定监听
     * @param notify
     */
    public static void bindNotify(NoticeNotify notify){
        instance().notifies.add(notify);
        instance().check(notify);
    }
    //绑定消息变化接口时进行一次检查，直接通知一次最新状态
    private void check(NoticeNotify noticeNotify) {
        if (mNotice != null){
            noticeNotify.onNoticeArrived(mNotice);
        }
    }
    /**
     * UserMessage UserInfo NavFragment,三处接口调用
     */
    private List<NoticeNotify> notifies = new ArrayList<>();
    private NoticeBean mNotice;

    /**
     * 通知消息来了
     * @param bean
     */
    private void onNoticeChanged(NoticeBean bean) {
        mNotice = bean;
        for (NoticeNotify notify : notifies){
            notify.onNoticeArrived(mNotice);
        }
    }

    public static void clear(Context context,int type){
        if (getNotice().getAllCount()>0){
            NoticeServer.clearAction(context, type);
        }
    }

    /**
     * 获取当前消息
     * @return NoticeBean
     */
    public static NoticeBean getNotice() {
        final NoticeBean bean = instance().mNotice;
        if (bean == null) {
            return new NoticeBean();
        } else {
            return bean;
        }
    }

    public interface NoticeNotify{
        void onNoticeArrived(NoticeBean bean);
    }
}
