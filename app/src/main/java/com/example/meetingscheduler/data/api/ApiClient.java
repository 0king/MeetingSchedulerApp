package com.example.meetingscheduler.data.api;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
         if (retrofit == null){
             retrofit = new Retrofit.Builder()
                     .baseUrl(Api.BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                     .build();
         }
         return retrofit;
    }
}
