package com.newland.palm.data.net;

import com.newland.palm.data.bean.Entity;
import com.newland.palm.data.bean.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lin on 2017/11/21.
 * 用户信息接口，包含OAuth验证
 */

public interface UserInterface {

    @GET("action/openapi/token?client_id=Vn3CFh6ovf9pamtW7DRL&client_secret=4VllsZqFhFMl4z5PCUp4zfz06akBnesi&grant_type=authorization_code&redirect_uri=https://my.oschina.net/u/3725152")
    Call<Entity> getEntityData(@Query("code") String code, @Query("dataType") String dataType);

    @GET("action/openapi/my_information?dataType=json")
    Call<User> getUserData(@Query("access_token") String access_token);

}
