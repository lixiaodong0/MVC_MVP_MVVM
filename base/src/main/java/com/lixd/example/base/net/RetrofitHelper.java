package com.lixd.example.base.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static RetrofitHelper INSTANCE;
    private static String BASE_URL = "";
    private static int READ_TIME = 60;
    private static int WRITE_TIME = 60;
    private static int CONN_TIME = 60;

    public static RetrofitHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitHelper();
                }
            }
        }
        return INSTANCE;
    }

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    private RetrofitHelper() {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME, TimeUnit.SECONDS)
                .connectTimeout(CONN_TIME, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> T getService(Class<T> tClass) {
        T t = retrofit.create(tClass);
        return t;
    }
}
