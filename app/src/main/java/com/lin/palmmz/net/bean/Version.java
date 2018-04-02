package com.lin.palmmz.net.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * @version 创建时间：2018/1/27 0027 下午 4:51
 */
public class Version implements Serializable {

    /**
     * code : 1
     * message : success
     * notice : {"like":0,"review":0,"letter":0,"mention":0,"fans":0}
     * result : [{"code":"410","downloadUrl":"https://www.oschina.net/uploads/osc-android-v4.1.0-release.apk","message":"1. 新增英文模块 <br>\n2. 文章详情新增字数与阅读时间提示 <br>\n3. 修复部分机型状态栏适配问题 <br>\n <br>\n大小：14.6M","name":"v4.1.0(1801241000)","status":"release"}]
     * time : 2018-02-02 15:48:54
     */

    private int code;
    private String message;
    private NoticeBean notice;
    private String time;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NoticeBean getNotice() {
        return notice;
    }

    public void setNotice(NoticeBean notice) {
        this.notice = notice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class NoticeBean {
        /**
         * like : 0
         * review : 0
         * letter : 0
         * mention : 0
         * fans : 0
         */

        private int like;
        private int review;
        private int letter;
        private int mention;
        private int fans;

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getReview() {
            return review;
        }

        public void setReview(int review) {
            this.review = review;
        }

        public int getLetter() {
            return letter;
        }

        public void setLetter(int letter) {
            this.letter = letter;
        }

        public int getMention() {
            return mention;
        }

        public void setMention(int mention) {
            this.mention = mention;
        }

        public int getFans() {
            return fans;
        }

        public void setFans(int fans) {
            this.fans = fans;
        }
    }

    public static class ResultBean implements Serializable{
        /**
         * code : 410
         * downloadUrl : https://www.oschina.net/uploads/osc-android-v4.1.0-release.apk
         * message : 1. 新增英文模块 <br>
         2. 文章详情新增字数与阅读时间提示 <br>
         3. 修复部分机型状态栏适配问题 <br>
         <br>
         大小：14.6M
         * name : v4.1.0(1801241000)
         * status : release
         */

        private String code;
        private String downloadUrl;
        private String message;
        private String name;
        private String status;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
