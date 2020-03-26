package com.example.meetingscheduler.UI.main;

import android.view.View;

import com.example.meetingscheduler.data.model.Meeting;

import java.util.List;

public interface IMeetingsView {
    void init(View v);

    //update UI data
    void showMeetings(List<Meeting> list);
    void showHeader(String date);
    void showError(String message);

    //bindDataToView();

    //on data load start
    //on data load finish
}
