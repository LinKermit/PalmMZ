package com.lin.palmmz.net;

import com.lin.palmmz.net.bean.Entity;
import com.lin.palmmz.net.bean.MessageList;
import com.lin.palmmz.net.bean.User;
import com.lin.palmmz.net.bean.Version;
import com.lin.palmmz.main.notice.NoticeBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lin on 2017/11/21.
 * 用户信息接口，包含OAuth验证
 */

/**
 * https://www.oschina.net/action/oauth2/authorize?response_type=code&client_id=Vn3CFh6ovf9pamtW7DRL&redirect_uri=https://my.oschina.net/u/3725152
 *
 * https://www.oschina.net/action/openapi/token?client_id=Vn3CFh6ovf9pamtW7DRL&client_secret=4VllsZqFhFMl4z5PCUp4zfz06akBnesi&grant_type=authorization_code&redirect_uri=https://my.oschina.net/u/3725152&code=p04TuH&dataType=json

 */
public interface UserInterface {

    /**
     *  getCall() = 接收网络请求数据的方法
        其中返回类型为Call<*>，*是接收数据的类（即上面定义的Translation类）
        如果想直接获得Responsebody中的内容，可以定义网络请求返回值为Call<ResponseBody>
        &code=p04Tu
     */
    @GET("action/openapi/token?client_id=Vn3CFh6ovf9pamtW7DRL&client_secret=4VllsZqFhFMl4z5PCUp4zfz06akBnesi&grant_type=authorization_code&redirect_uri=https://my.oschina.net/u/3725152")
    Call<Entity> getEntityData(@Query("code") String code, @Query("dataType")String dataType);
    //&code=p04TuH&dataType=json

    /**
     * /action/openapi/user
     * access_token
     * dataType         json
     */
    @GET("action/openapi/my_information?dataType=json")
    Call<User> getUserData(@Query("access_token") String access_token);


    /**
     * 检测版本更新
     * @return
     */
    @GET("action/apiv2/product_version?appId=1&catalog=1&all=false")
    Call<Version>  getVersionData();

    /**
     * 获取notice数据
     */
    @GET("action/openapi/user_notice?dataType=json&page=1&pageSize=20")
    Call<NoticeBean> getNoticeData(@Query("access_token") String access_token);

    @GET("action/openapi/message_list?dataType=json")
    Call<MessageList> getMessageListData(@Query("access_token") String access_token);
}
