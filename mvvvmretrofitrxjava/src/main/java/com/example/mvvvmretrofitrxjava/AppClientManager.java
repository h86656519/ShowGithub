package com.example.mvvvmretrofitrxjava;

import android.util.Log;

import androidx.databinding.library.BuildConfig;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jetbrains.annotations.NotNull;

public class AppClientManager {
    private static AppClientManager googleManager = new AppClientManager();
    private static AppClientManager githubManager = new AppClientManager();
    private static Retrofit retrofit;
    private static OkHttpClient.Builder okHttpClient;
    private static String connnect_url;

    //設定 init
    private AppClientManager() {
    }

    public static Retrofit getGithubInstance() {
        //沒run 過，還不確定
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.level(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(logging);
            connnect_url = Config.baseURL_TEST;
        } else {
            connnect_url = Config.baseURL;
        }

        okHttpClient.connectTimeout(30, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(connnect_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient.build())
                .build();
        return githubManager.retrofit;
    }
}
