package com.lin.palmmz.net.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lin on 2017/11/19.
 * 获取当前登录用户的账户信息
 */

/*
/action/openapi/user
access_token	true	string	oauth2_token获取的access_token
dataType	true	string	返回数据类型['json'|'jsonp'|'xml']	json

 */
public class User implements Serializable{

    /**
     * uid : 89964
     * name : 彭博
     * gender : 1
     * province : 广东
     * city : 深圳
     * platforms : ["Java EE","Java SE","JavaScript","HTML/CSS"]
     * expertise : ["WEB开发","桌面软件开发","服务器端开发"]
     * joinTime : 2010-07-14 17:15:55
     * lastLoginTime : 2013-10-21 10:55:36
     * portrait : http://www.oschina.net/uploads/user/44/89964_50.jpg?t=1376365607000
     * fansCount : 19
     * favoriteCount : 176
     * followersCount : 14
     * notice : {"replyCount":0,"msgCount":0,"fansCount":0,"referCount":0}
     */

    private long uid;
    private String name;
    private int gender;
    private String province;
    private String city;
    private String joinTime;
    private String lastLoginTime;
    private String portrait;
    private int fansCount;
    private int favoriteCount;
    private int followersCount;
    private NoticeBean notice;
    private List<String> platforms;
    private List<String> expertise;
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }



    public long getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public NoticeBean getNotice() {
        return notice;
    }

    public void setNotice(NoticeBean notice) {
        this.notice = notice;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<String> expertise) {
        this.expertise = expertise;
    }


    public static class NoticeBean implements Serializable{
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
}
