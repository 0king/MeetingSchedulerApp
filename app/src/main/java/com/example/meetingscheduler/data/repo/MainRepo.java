package com.example.meetingscheduler.data.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.meetingscheduler.data.api.Api;
import com.example.meetingscheduler.data.api.ApiClient;
import com.example.meetingscheduler.data.model.Meeting;
import com.example.meetingscheduler.data.model.Value;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public enum MainRepo { //singleton
    INSTANCE;

    private Api api = null;
    private SimpleDateFormat format;
    private String date;
    private Calendar calendar;

    MainRepo(){
        //Log.d("durga", "MainRepo cons");
        api = ApiClient.getClient().create(Api.class);
        format = new SimpleDateFormat("dd/MM/yyyy");
        //subject = PublishSubject.create();
        calendar = Calendar.getInstance();
    }

    public Single<List<Meeting>> getMeetings(String date){
        //Log.d("durga", "MainRepo getMeetings date=" + date);
        return api.getMeetings(date)//"26/03/2020"
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public String getDateFormatted(Value value){
        int day = 0;
        if (value==Value.POSITIVE) day = 1;
        else if (value==Value.NEGATIVE) day = -1;
        calendar.add(Calendar.DAY_OF_YEAR, day);
        //Log.d("durga", "MainRepo date=" + format.format(calendar.getTime()));
        return format.format(calendar.getTime());
    }

}
