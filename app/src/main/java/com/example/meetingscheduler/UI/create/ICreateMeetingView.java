package com.example.meetingscheduler.UI.create;

import android.view.View;

import com.example.meetingscheduler.data.model.Meeting;

public interface ICreateMeetingView {
    void init(View view);
    void showError(String msg);
    void updateViewOnAdd(Meeting meeting);
}
