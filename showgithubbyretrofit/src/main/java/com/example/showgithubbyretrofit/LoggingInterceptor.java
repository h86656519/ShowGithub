package com.example.showgithubbyretrofit;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
    int token = 123456789;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        // 可以將每次呼叫時夾帶的共同參數放到 Interceptor 裡面，假設是token 的話就如下
        //  Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token);

        long t1 = System.nanoTime();
//        Log.i("LoggingInterceptor", "intercept: " + String.format("Sending request %s on %s%n%s",
//                request.url(), chain.connection(), request.headers()));
        Response response = chain.proceed(request); //返回重定向的响应

        long t2 = System.nanoTime();
//        Log.i("LoggingInterceptor2", "intercept: " + String.format("Received response for %s in %.1fms%n%s",
//                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
//        Log.i("LoggingInterceptor2", "response.headers() : " + response.headers());
//        Log.i("LoggingInterceptor2", "response.reequest().url() : " + response.request().url()); //請求的url

        return response;
    }
}
