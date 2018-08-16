package com.ayusch.wunderassignment.networking.adapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;

import com.ayusch.WunderApplication;
import com.ayusch.wunderassignment.utils.Const;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAdapter {
    private static Retrofit carsInfoRetrofit;
    private static Gson gson;

    public static Retrofit getInstance() {
        if (carsInfoRetrofit == null) {
            if (gson == null) {
                gson = new GsonBuilder().setLenient().create();
            }

            carsInfoRetrofit = new Retrofit.Builder().baseUrl(Const.CARS_INFO_API).
                    client(getHttpClientInterceptor()).
                    addConverterFactory(GsonConverterFactory.create(gson)).
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        }
        return carsInfoRetrofit;
    }

    @NonNull
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private static OkHttpClient getHttpClientInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builderHttpClient = new OkHttpClient.Builder();
        builderHttpClient.connectTimeout(15, TimeUnit.SECONDS);
        builderHttpClient.readTimeout(15, TimeUnit.SECONDS);
        builderHttpClient.writeTimeout(15, TimeUnit.SECONDS);
        builderHttpClient.addInterceptor(logging);
        builderHttpClient.addNetworkInterceptor(new StethoInterceptor());
        builderHttpClient.retryOnConnectionFailure(true);

        //Intercept network request
        builderHttpClient.addInterceptor(new ChuckInterceptor(WunderApplication.getInstance()));
        return builderHttpClient.build();
    }


}
