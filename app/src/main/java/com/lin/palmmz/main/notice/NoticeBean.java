package com.lin.palmmz.main.notice;

import android.content.Context;

import com.lin.palmmz.util.SharedPreferencesHelper;

import java.io.Serializable;

/**
 * Created by lin on 2017/11/27.
 */

public class NoticeBean implements Serializable {

    private int mention;//新@个数
    private int letter; //新私信个数
    private int review;//新评论数
    private int fans;//新粉丝
    private int like = 0;

    public int getMention() {
        return mention;
    }

    public void setMention(int mention) {
        this.mention = mention;
    }

    public int getLetter() {
        return letter;
    }

    public void setLetter(int letter) {
        this.letter = letter;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }


    public int getAllCount(){
        return mention+letter+review+fans;
    }
    @Override
    public String toString() {
        return "NoticeBean{" +
                "mention=" + mention +
                ", letter=" + letter +
                ", review=" + review +
                ", fans=" + fans +
                ", like=" + like +
                '}';
    }

    public static void save(Context context,NoticeBean noticeBean){
        SharedPreferencesHelper.saveObject(context,noticeBean,SharedPreferencesHelper.NOTICE_KEY);
    }
    public static NoticeBean getInstance(Context context){
        NoticeBean bean = (NoticeBean) SharedPreferencesHelper.readObject(context,SharedPreferencesHelper.NOTICE_KEY);
        if (bean == null){
            bean = new NoticeBean();
        }
        return bean;
    }
}
