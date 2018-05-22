package com.newland.palm.data.net;

import com.newland.palm.data.bean.Portrait;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author lin
 * @version 2018/5/22 0022
 */
public interface UploadInterface {

    /**
     * 上传一张图片
     * @return
     */
    @Multipart
    @POST("/action/openapi/portrait_update")
    Call<Portrait> uploadPortrait(@Part MultipartBody.Part part , @Query("access_token") String access_token);

}
