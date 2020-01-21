package com.standardyze.networking;

import com.standardyze.utils.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebServiceFactory {
    public static WebService webService;

    public static WebService getInstance() {
        webService = null;
        if (webService == null) {
            HttpInterceptor interceptor = new HttpInterceptor();
           // interceptor.setLevel(HttpInterceptor.Level.BODY);


            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

            webService = new Retrofit.Builder()
                    .baseUrl(AppConstants.SERVER_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WebService.class);
        }
        return webService;
    }


}
