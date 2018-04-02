package com.lin.palmmz.net.bean;

import com.lin.palmmz.main.notice.NoticeBean;

import java.util.List;

/**
 * @author lin
 * @version 创建时间：2018/1/27 0027 下午 4:51
 */
public class MessageList {

    /**
     * notice : {"replyCount":0,"msgCount":0,"fansCount":0,"referCount":0}
     * messageList : [{"content":"你好啊","senderid":253479,"sender":"张艺辰","friendid":253469,"id":898973,"pubDate":"2013-10-10 15:55:24.0","friendname":"test33","messageCount":2,"portrait":"http://static.oschina.org/uploads/user/126/253469_50.jpg?t=1366257509000"}]
     */

    private NoticeBean notice;
    private List<MessageListBean> messageList;

    public NoticeBean getNotice() {
        return notice;
    }

    public void setNotice(NoticeBean notice) {
        this.notice = notice;
    }

    public List<MessageListBean> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageListBean> messageList) {
        this.messageList = messageList;
    }

    public static class MessageListBean {
        /**
         * content : 你好啊
         * senderid : 253479
         * sender : 张艺辰
         * friendid : 253469
         * id : 898973
         * pubDate : 2013-10-10 15:55:24.0
         * friendname : test33
         * messageCount : 2
         * portrait : http://static.oschina.org/uploads/user/126/253469_50.jpg?t=1366257509000
         */

        private String content;
        private int senderid;
        private String sender;
        private int friendid;
        private int id;
        private String pubDate;
        private String friendname;
        private int messageCount;
        private String portrait;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSenderid() {
            return senderid;
        }

        public void setSenderid(int senderid) {
            this.senderid = senderid;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public int getFriendid() {
            return friendid;
        }

        public void setFriendid(int friendid) {
            this.friendid = friendid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getFriendname() {
            return friendname;
        }

        public void setFriendname(String friendname) {
            this.friendname = friendname;
        }

        public int getMessageCount() {
            return messageCount;
        }

        public void setMessageCount(int messageCount) {
            this.messageCount = messageCount;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }
    }
}
