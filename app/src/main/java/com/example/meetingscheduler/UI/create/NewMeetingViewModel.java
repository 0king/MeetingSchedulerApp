package com.example.meetingscheduler.UI.create;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.meetingscheduler.data.model.Meeting;
import com.example.meetingscheduler.data.model.MultiMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class NewMeetingViewModel extends ViewModel {
    private Meeting mCurrentMeeting = new Meeting();
    private MultiMap<String, Meeting> newlyCreatedMeetings = new MultiMap<>();
    //attach viewmodel to fragment lifecycle

    public boolean isSlotAvailable(){
        String date = mCurrentMeeting.getDate();
        //Log.d("durga", "date=" + date);
        //Log.d("durga", "MultiMap slot check=" + newlyCreatedMeetings.toString());
        if (newlyCreatedMeetings.containsKey(date)){
            //Log.d("durga", "contains key");
            int start = Integer.parseInt(mCurrentMeeting.getStartTime());
            //Log.d("durga", "start=" + start);
            int end = Integer.parseInt(mCurrentMeeting.getEndTime());
            //Log.d("durga", "end=" + end);
            ArrayList<Meeting> a = newlyCreatedMeetings.get(date);
            for (int i=0; i<a.size();i++){
                Meeting m = a.get(i);
                int s = Integer.parseInt(m.getStartTime());
                //Log.d("durga", "s=" + s);
                int e = Integer.parseInt(m.getEndTime());
                //Log.d("durga", "e=" + e);
                if ((start>=s && start<=e) || (end>=s && end<=e)){
                    //Log.d("durga", "return false");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isDateValid(){
        String d = mCurrentMeeting.getDate();
        //Log.d("durga", "curr date=" + d);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date dt = null;
        try {
            dt = f.parse(d);
        } catch (ParseException e) {
            //e.printStackTrace();
            //Log.d("durga", "=ParseException=");
        }
        Calendar cal = Calendar.getInstance();
        Date today = new Date();
        cal.setTime(today);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        today = cal.getTime();
        if (today.after(dt)){
            //Log.d("durga", "today.after(dt)");
            return false;
        }
        else
            return true;
    }

    public void createMeeting(){
        newlyCreatedMeetings.put(mCurrentMeeting.getDate(), mCurrentMeeting.clone());
        //Log.d("durga", "MultiMap create=" + newlyCreatedMeetings.toString());
    }

    public Meeting getMeeting(){
        return mCurrentMeeting;
    }

    public void setMeeting(Meeting m){
        mCurrentMeeting = m;
    }

    public String getDate() {
        return mCurrentMeeting.getDate();
    }

    public void setDate(String date) {
        mCurrentMeeting.setDate(date);
    }

    public String getStartTime() {
        return mCurrentMeeting.getStartTime();
    }

    public void setStartTime(String startTime) {
        mCurrentMeeting.setStartTime(startTime);
    }

    public String getEndTime() {
        return mCurrentMeeting.getEndTime();
    }

    public void setEndTime(String endTime) {
        mCurrentMeeting.setEndTime(endTime);
    }

    public String getDesc() {
        return mCurrentMeeting.getDescription();
    }

    public void setDesc(String desc) {
        mCurrentMeeting.setDescription(desc);
    }
}
