package com.example.meetingscheduler.UI.main;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetingscheduler.Navigator;
import com.example.meetingscheduler.R;
import com.example.meetingscheduler.UI.base.IProgressView;
import com.example.meetingscheduler.data.model.Meeting;
import com.example.meetingscheduler.data.model.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListFragment extends Fragment implements IMeetingsView, IProgressView {

    //views
    private View mNextView, mPrevView;
    private TextView mCurrentDate;
    private Button mCreateBtn;
    private RecyclerView mMeetingsListView;
    private ProgressBar progressBar;

    //private IMeetingsView mMeetingsView;
    private MeetingsViewModel viewModel;
    //private CompositeDisposable mDisposable;
    //private boolean orientationLand = false;

    //todo listen to internet connectivity
    //fragment change layout on rotation

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MeetingsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        init(root);//init UI
        bindDataToView();
        return root;
    }

    //fragment on back - lose data viewmodel
    private void bindDataToView(){
        String dt = viewModel.getDate().getValue();
        if (dt !=null && !dt.isEmpty()){
            showHeader(dt);
        }
        Resource r = viewModel.getMeetingsList().getValue();
        if (r != null){
            showMeetings((List<Meeting>) r.getData());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //mDisposable = new CompositeDisposable();
        fetchData();
    }

    private void fetchData(){
        //get data
        viewModel.getDate().observe(this, dateObserver);
        viewModel.getMeetingsList().observe(this, listObserver);
    }

    // Create the observer which updates the UI.
    private Observer<String> dateObserver = new Observer<String>() {
        @Override
        public void onChanged(@Nullable final String newDate) {
            // Update the UI
            showHeader(newDate);
        }
    };

    private Observer<Resource> listObserver = (response) -> {
        switch (response.getStatus()){
            case ERROR: {
                showMeetings(new ArrayList<>());
                showError(response.getError().toString());
            }
            break;
            case SUCCESS: {
                List<Meeting> newList = (List<Meeting>) response.getData();
                if (newList.isEmpty()) //UI logic
                    Toast.makeText(getContext(), "Nothing found", Toast.LENGTH_SHORT).show();
                showMeetings(newList);
            }
            break;
        }
    };

    @Override
    public void init(View v) {
        //todo use data binding
        progressBar = v.findViewById(R.id.progressBar);
        mCurrentDate = v.findViewById(R.id.tvDate);
        mCreateBtn = v.findViewById(R.id.btnCreate);
        mNextView = v.findViewById(R.id.next);
        mPrevView = v.findViewById(R.id.prev);
        mMeetingsListView = v.findViewById(R.id.meetingsList);

        Context context = v.getContext();
        mMeetingsListView.setLayoutManager(
                new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        mMeetingsListView.addItemDecoration(
                new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        mMeetingsListView.setHasFixedSize(true);
        //mMeetingsListView.setItemAnimator(new DefaultItemAnimator());

        mNextView.setOnClickListener(vw -> onNextClick());
        mPrevView.setOnClickListener(vw -> onPrevClick());
        mCreateBtn.setOnClickListener(vw -> onCreateClick());
    }

    @Override
    public void showMeetings(List<Meeting> meetings) {
        if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE) {
            MeetingsAdapterLand adapter = new MeetingsAdapterLand(meetings);
            mMeetingsListView.setAdapter(adapter);
        }
        else {
            MeetingsAdapter adapter = new MeetingsAdapter(meetings);//todo create once
            mMeetingsListView.setAdapter(adapter);
        }
        onFinishLoading();
    }

    @Override
    public void showHeader(String date) {
        //Log.d("durga", "show header");
        if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE) {
            //Log.d("durga", "orientation land");
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
            Date d = null;
            try {
                d = f.parse(date);
            }
            catch (ParseException e){
                showError("ERR...");
            }
            f.applyPattern("EEE, d MMM yyyy");
            //Log.d("durga", "date=" + f.format(d));
            mCurrentDate.setText(f.format(d));
        }
        else
            mCurrentDate.setText(date);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), "Err...", Toast.LENGTH_SHORT).show();
        //Log.d("durga", e.getMessage());
        onFinishLoading();
    }

    @Override
    public void onStartLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void onNextClick(){
        if (viewModel.shouldLoad()){
            onStartLoading();
            viewModel.onNextClick();
        }
    }

    private void onPrevClick(){
        onStartLoading();
        viewModel.onPrevClick();
    }

    private void onCreateClick(){
        Navigator.INSTANCE.toCreateScreen(viewModel.getDate().getValue());
    }
}
