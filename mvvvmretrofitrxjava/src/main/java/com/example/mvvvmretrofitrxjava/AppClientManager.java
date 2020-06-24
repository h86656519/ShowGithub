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
    private AppClientManager() {

//        寫法2，採用post 請求的話也可以用寫法2將token 加進去在header 裡面，或是用寫法1(在PostApi)
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Interceptor.Chain chain) throws IOException {
//                Request original = chain.request();
//                Request request = original.newBuilder()
//                        .header("Authorization", "token ae10c2bdf77f47ed95034fdf275e715bd573e938")
//                        .header("Content-Type", "application/json")
//                        .method(original.method(), original.body())
//                        .build();
//                return chain.proceed(request);
//            }
//        });

//        一般GET請求的話只需要37~42行即可，需要POST請求的話需要再加上23~34行
//        okHttpClient = builder.build();
//            retrofit = new Retrofit.Builder()
//                .baseUrl(Config.baseURL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();

    }

//    public static Retrofit getClient() {
//        return manager.retrofit;
//    }


    public static Retrofit getGithubInstance() {
        //okHttpClient = builder.build();
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
