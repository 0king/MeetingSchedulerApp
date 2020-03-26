package com.example.meetingscheduler.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Meeting {

    private String date;

    @Expose
    @SerializedName("start_time")
    private String startTime;

    @Expose
    @SerializedName("end_time")
    private String endTime;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("participants")
    private List<String> participants;

    public Meeting(String dt, String startTime, String endTime, String description, List<String> participants) {
        date = dt;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.participants = participants;
    }

    public Meeting(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public Meeting clone(){
        Meeting m = new Meeting();
        m.setDate(this.getDate());
        m.setStartTime(this.getStartTime());
        m.setEndTime(this.getEndTime());
        m.setDescription(this.getDescription());
        return m;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Meeting: start=")
                .append(startTime)
                .append(" end=")
                .append(endTime)
                .append(" desc=")
                .append(description);
        return builder.toString();
    }
}
