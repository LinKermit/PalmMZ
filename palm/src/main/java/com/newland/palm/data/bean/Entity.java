package com.newland.palm.data.bean;

import java.io.Serializable;

/**
 * Created by lin on 2017/11/19.
 */

public class Entity implements Serializable{
    /**
     * {"access_token":"2db3e8fa-cc96-4513-9cd0-7f8523a432d1","refresh_token":"41bf7b67-340e-4dab-9147-6fbc80d8e67d","uid":3725152,"token_type":"bearer","expires_in":604799}
     */

    protected String access_token;
    protected String refresh_token;
    protected String uid;
    protected String token_type;
    protected String expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", uid='" + uid + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }
}
