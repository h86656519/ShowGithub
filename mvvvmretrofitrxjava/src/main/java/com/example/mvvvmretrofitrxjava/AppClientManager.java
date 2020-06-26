package com.example.mvvvmretrofitrxjava;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class AppClientManager {
    private static AppClientManager googleManager = new AppClientManager();
    private static AppClientManager githubManager = new AppClientManager();
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    static OkHttpClient.Builder builder = new OkHttpClient.Builder();

    //設定 init
    private AppClientManager() { }

    public static Retrofit getGithubInstance() {
        okHttpClient = builder // 新增攔截器
                .connectTimeout(30, TimeUnit.SECONDS)   // 設置連線Timeout，沒寫預設是10秒
               // .addInterceptor(new LoggingInterceptor()) //新增攔截器
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Config.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return githubManager.retrofit;
    }

    public static Retrofit getGoogleInstance() {
        okHttpClient = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return googleManager.retrofit;
    }
}
