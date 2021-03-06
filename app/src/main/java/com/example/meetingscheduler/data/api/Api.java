package com.example.meetingscheduler.data.api;

import com.example.meetingscheduler.data.model.Meeting;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "http://fathomless-shelf-5846.herokuapp.com";

    @GET("api/schedule")
    Single<List<Meeting>> getMeetings(@Query("date") String date);

}
