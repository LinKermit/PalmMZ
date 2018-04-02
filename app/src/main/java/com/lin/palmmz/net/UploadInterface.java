package com.lin.palmmz.net;

import com.lin.palmmz.net.bean.Version;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author lin
 * @version 创建时间：2018/1/27 0027 下午 4:51
 */
public interface UploadInterface {

    @Multipart
    @POST()
    Call<String> uploadAvatar(@Part MultipartBody.Part img);
}
