package com.cnrylmz.challengemobilist.api;

import android.util.Log;

import com.cnrylmz.challengemobilist.BuildConfig;
import com.cnrylmz.challengemobilist.logger.Level;
import com.cnrylmz.challengemobilist.logger.Logger;
import com.cnrylmz.challengemobilist.logger.LoggingInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.internal.Platform;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Caner on 07.05.2019.
 */

public class ApiModule {

    private static Retrofit retrofit = null;
    private static String baseUrl = "http://wamp.mobilist.com.tr/";


    private ApiModule() {

    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getLoggerClient())
                    .build();
            return retrofit;
        }
        return retrofit;

    }

    private static OkHttpClient getLoggerClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BODY)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .logger(new Logger() {
                    @Override
                    public void log(int level, String tag, String msg) {
                        Log.d(tag, msg);
                    }
                }).build());


        OkHttpClient client = builder.build();

        return client;
    }
}