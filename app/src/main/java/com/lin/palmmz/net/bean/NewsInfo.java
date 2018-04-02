package com.lin.palmmz.net.bean;

import java.util.List;

/**
 * Created by lin on 2017/11/24.
 */

public class NewsInfo {

    /**
     * newslist : [{"id":26754,"author":"test33","pubDate":"2013-09-17 16:49:50.0","title":"asdfa","authorid":253469,"commentCount":0,"type":4}]
     * notice : {"replyCount":0,"msgCount":0,"fansCount":0,"referCount":0}
     */

    private NoticeBean notice;
    private List<NewslistBean> newslist;

    public NoticeBean getNotice() {
        return notice;
    }

    public void setNotice(NoticeBean notice) {
        this.notice = notice;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NoticeBean {
        /**
         * replyCount : 0
         * msgCount : 0
         * fansCount : 0
         * referCount : 0
         */

        private int replyCount;
        private int msgCount;
        private int fansCount;
        private int referCount;

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public int getMsgCount() {
            return msgCount;
        }

        public void setMsgCount(int msgCount) {
            this.msgCount = msgCount;
        }

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public int getReferCount() {
            return referCount;
        }

        public void setReferCount(int referCount) {
            this.referCount = referCount;
        }
    }

    public static class NewslistBean {
        /**
         * id : 26754
         * author : test33
         * pubDate : 2013-09-17 16:49:50.0
         * title : asdfa
         * authorid : 253469
         * commentCount : 0
         * type : 4
         */

        private int id;
        private String author;
        private String pubDate;
        private String title;
        private int authorid;
        private int commentCount;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAuthorid() {
            return authorid;
        }

        public void setAuthorid(int authorid) {
            this.authorid = authorid;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
