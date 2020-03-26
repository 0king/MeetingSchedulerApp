package com.example.meetingscheduler.UI.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetingscheduler.data.model.Meeting;
import com.example.meetingscheduler.R;

import java.util.List;

public class MeetingsAdapter extends RecyclerView.Adapter<MeetingsAdapter.MeetingHolder>{

    List<Meeting> mData;//todo use live data object
    MeetingsAdapter(List<Meeting> list){
        mData = list;
    }

    @NonNull
    @Override
    public MeetingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = View.inflate(parent.getContext(), R.layout.list_item, parent);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MeetingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MeetingHolder extends RecyclerView.ViewHolder{
        //todo use separate view class
        private TextView tvTimePeriod;
        private TextView tvDescription;

        MeetingHolder(View view){
            super(view);
            tvTimePeriod = view.findViewById(R.id.tvTime);
            tvDescription = view.findViewById(R.id.tvNotes);
        }

        void bind(Meeting meeting){
            //UI logic
            String timePeriod = meeting.getStartTime() + " - " + meeting.getEndTime();

            tvTimePeriod.setText(timePeriod);
            tvDescription.setText(meeting.getDescription());
        }
    }
}