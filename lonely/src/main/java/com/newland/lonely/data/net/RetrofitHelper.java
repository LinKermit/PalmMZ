package com.newland.lonely.data.net;

import com.newland.lonely.data.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by lin on 2017/11/21.
 */

public class RetrofitHelper {

    public static UserInterface getEntityApi(){
        return createApi(UserInterface.class, Constants.baseUrl);
    }

    /**
     * 获取用户信息
     * @return
     */
    public static UserInterface getUserApi(){
        return createApi(UserInterface.class, Constants.baseUrl);
    }


//    public static UploadInterface uploadInterface(){
//        return createApi(UploadInterface.class,Constants.baseUrl);
//    }


    private static OkHttpClient mOkHttpClient = new OkHttpClient();
    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//不支持rxjava
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        return retrofit.create(clazz);
    }
}
