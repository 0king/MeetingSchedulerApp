package com.example.meetingscheduler.UI.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.meetingscheduler.data.model.Meeting;
import com.example.meetingscheduler.data.model.Resource;
import com.example.meetingscheduler.data.model.StateLiveData;
import com.example.meetingscheduler.data.model.Value;
import com.example.meetingscheduler.data.repo.MainRepo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MeetingsViewModel extends ViewModel { //todo create interface
    private int dayCount = 0;
    private MutableLiveData<String> date = new MutableLiveData<>();
    private StateLiveData<List<Meeting>> list = new StateLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    private MainRepo repo;

    public MeetingsViewModel() {
        super();
        repo = MainRepo.INSTANCE;
        updateData(Value.ZERO);
    }

    public LiveData<String> getDate(){
        return date;
    }

    public StateLiveData<List<Meeting>> getMeetingsList(){//todo - why error if return type Livedata
        return list;
    }

    //event handling
    public void onNextClick(){
        if (dayCount<0){
            dayCount++;
            updateData(Value.POSITIVE);
        }
    }

    public void onPrevClick(){
        dayCount--;
        updateData(Value.NEGATIVE);
    }

    public boolean shouldLoad(){
        return dayCount < 0;
    }

    private void updateData(Value value){
        //change date
        date.setValue(repo.getDateFormatted(value));
        //get meetings list
        getMeetings();
    }

    private void getMeetings(){
        Disposable d = repo.getMeetings(date.getValue())
                .subscribe(
                        meetings -> {
                            //UI logic - empty list, sort the list
                            Collections.sort(meetings, new SortByDate());
                            list.postSuccess(meetings);
                        },
                        err -> {
                            list.postError(err);
                        }
                );
        disposable.add(d);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    class SortByDate implements Comparator<Meeting> {
        // Used for sorting in ascending order
        public int compare(Meeting a, Meeting b) {
            String startTime1 = a.getStartTime().replace(":","");
            String startTime2 = b.getStartTime().replace(":","");
            return Integer.parseInt(startTime1) - Integer.parseInt(startTime2);
        }
    }

}
