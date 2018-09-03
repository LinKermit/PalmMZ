package com.newland.lonely.data.bean;

import java.io.Serializable;

/**
 * @author lin
 * @version 2018/5/22 0022
 */
public class Portrait implements Serializable{

    /**
     * error : 200
     * error_description : 操作成功完成
     */

    private String error;
    private String error_description;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
}
