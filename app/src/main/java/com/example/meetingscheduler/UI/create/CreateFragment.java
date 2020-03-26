package com.example.meetingscheduler.UI.create;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.meetingscheduler.Navigator;
import com.example.meetingscheduler.R;
import com.example.meetingscheduler.data.model.Meeting;

import java.util.Calendar;

public class CreateFragment extends Fragment implements ICreateMeetingView{

    private TextView mDateView, mStartTime, mEndTime;
    private EditText mDesc;
    private Button mSubmit;
    private View mGoBackView;

    private NewMeetingViewModel viewModel;

    public CreateFragment() {}

    public static CreateFragment newInstance(String date){
        CreateFragment fragment = new CreateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(NewMeetingViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create, container, false);
        init(root);
        bindDataToView(savedInstanceState);
        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("rotated", true);
    }

    @Override
    public void init(View view) {
        mDateView = view.findViewById(R.id.tvDate);
        mStartTime = view.findViewById(R.id.tvStartTime);
        mEndTime = view.findViewById(R.id.tvEndTime);
        mDesc= view.findViewById(R.id.etDesc);
        mSubmit = view.findViewById(R.id.btnSubmit);
        mGoBackView = view.findViewById(R.id.prev);

        mDateView.setOnClickListener(v -> onDateClick());
        mStartTime.setOnClickListener(v -> onStartClick());
        mEndTime.setOnClickListener(v -> onEndClick());
        mSubmit.setOnClickListener(v -> onSubmit());
        mGoBackView.setOnClickListener(v -> onBackClick());
    }

    private void bindDataToView(Bundle savedInstanceState){
        if (savedInstanceState!=null){
            Log.d("durga", "savedInstanceState not null");
            String dt = viewModel.getDate();
            if (dt!=null){
                Log.d("durga", "dt not null");
                mDateView.setText("Date: " + dt);
                updateButtonState();
            }
            String s = viewModel.getStartTime();
            if (s!=null)
                mStartTime.setText("Start = "+s);
            String e = viewModel.getEndTime();
            if (e!=null)
                mEndTime.setText("End = " + e);
        }
        else {
            //initial value
            if (getArguments()!=null){
                String date = getArguments().getString("date");
                viewModel.setDate(date);
                mDateView.setText("Date: " + date);
                updateButtonState();
            }
        }
    }

    private void updateButtonState(){
        if (!viewModel.isDateValid()){
            mSubmit.setEnabled(false);
        }
        else
            mSubmit.setEnabled(true);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateViewOnAdd(Meeting meeting) {
        viewModel.setMeeting(meeting);
    }

    private void onDateClick(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        viewModel.setDate(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        mDateView.setText("Date: " + viewModel.getDate());
                        updateButtonState();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void onStartClick(){

        //todo start < end

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String m = minute/10 == 0 ? "0"+minute : ""+minute;
                        viewModel.setStartTime(hourOfDay + "" + m);
                        mStartTime.setText("Start: " + hourOfDay + ":" + m);//viewModel.getStartTime()
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void onEndClick(){
        //todo end > start

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String m = minute/10 == 0 ? "0"+minute : ""+minute;
                        viewModel.setEndTime(hourOfDay + "" + m);
                        mEndTime.setText("End: " + hourOfDay + ":" + m);
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void onSubmit(){
        if (viewModel.isSlotAvailable()){
            viewModel.createMeeting();
            //viewModel.setMeeting(null);//todo clear data
            Toast.makeText(getContext(), "Meeting created.", Toast.LENGTH_SHORT).show();
        }
        else {
            showError("Slot not available");
        }
    }

    private void onBackClick(){
        Navigator.INSTANCE.toMainScreen();
    }

}
