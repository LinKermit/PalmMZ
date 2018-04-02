package com.lin.palmmz.net;

import com.lin.palmmz.net.bean.NewsInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lin on 2017/11/24.
 */

public interface NewsInterface {

    @GET("action/openapi/news_list?")
    Call<NewsInfo> getNewsInfos(@Query("access_token")String access_token,@Query("catalog")int catalog,
                                @Query("page")int page,@Query("pageSize")int pageSize,
                                @Query("dataType")String dataType);
}
