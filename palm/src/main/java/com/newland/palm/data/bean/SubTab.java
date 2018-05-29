package com.newland.palm.data.bean;

import java.io.Serializable;

/**
 * @author lin
 * @version 2018/5/25 0025
 */
public class SubTab implements Serializable{


    /**
     * banner : {"catalog":1,"href":"https://www.oschina.net/action/apiv2//banner?catalog=1"}
     * fixed : true
     * href : https://www.oschina.net/action/apiv2/sub_list?token=d6112fa662bc4bf21084670a857fbd20
     * name : 开源资讯
     * needLogin : false
     * isActived : true
     * order : 1
     * subtype : 1
     * token : d6112fa662bc4bf21084670a857fbd20
     * type : 6
     */

    private BannerBean banner;
    private boolean fixed;
    private String href;
    private String name;
    private boolean needLogin;
    private boolean isActived;
    private int order;
    private int subtype;
    private String token;
    private int type;

    public BannerBean getBanner() {
        return banner;
    }

    public void setBanner(BannerBean banner) {
        this.banner = banner;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }

    public boolean isIsActived() {
        return isActived;
    }

    public void setIsActived(boolean isActived) {
        this.isActived = isActived;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getSubtype() {
        return subtype;
    }

    public void setSubtype(int subtype) {
        this.subtype = subtype;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class BannerBean implements Serializable{
        /**
         * catalog : 1
         * href : https://www.oschina.net/action/apiv2//banner?catalog=1
         */

        private int catalog;
        private String href;

        public int getCatalog() {
            return catalog;
        }

        public void setCatalog(int catalog) {
            this.catalog = catalog;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof SubTab) {
            SubTab tab = (SubTab) obj;
            if (tab.getToken() == null) return false;
            if (this.token == null) return false;
            return tab.getToken().equals(this.token);
        }
        return false;
    }

    @Override
    public String toString() {
        return "SubTab{" +
                "banner=" + banner +
                ", fixed=" + fixed +
                ", href='" + href + '\'' +
                ", name='" + name + '\'' +
                ", needLogin=" + needLogin +
                ", isActived=" + isActived +
                ", order=" + order +
                ", subtype=" + subtype +
                ", token='" + token + '\'' +
                ", type=" + type +
                '}';
    }
}
