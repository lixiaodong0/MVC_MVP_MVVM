package com.lixd.example.base.net;

import com.lixd.example.base.data.DetailBean;
import com.lixd.example.base.data.ShareBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    //域名
    String BASE_URL = "https://gank.io/";

    /**
     * 获取最新的分享
     */
    @GET("api/today")
    Observable<ResponseBody> getTodayShare();

    /**
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     */
    @GET("api/data/福利/{size}/{page}")
    Observable<ShareBean<List<DetailBean>>> getCategory(@Path("page") int page, @Path("size") int size);
}
