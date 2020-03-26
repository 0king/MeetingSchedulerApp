package com.example.meetingscheduler.UI.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetingscheduler.R;
import com.example.meetingscheduler.data.model.Meeting;

import java.util.List;

public class MeetingsAdapterLand extends RecyclerView.Adapter<MeetingsAdapterLand.MeetingHolder>{

    private List<Meeting> mData;//todo use live data object
    MeetingsAdapterLand(List<Meeting> list){
        mData = list;
    }

    @NonNull
    @Override
    public MeetingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_land, parent, false);
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

        private TextView start, end, desc, participants;

        MeetingHolder(View view){
            super(view);
            start = view.findViewById(R.id.tvStartTime);
            end = view.findViewById(R.id.tvEndTime);
            desc = view.findViewById(R.id.tvDesc);
            participants = view.findViewById(R.id.tvParticipants);
        }

        void bind(Meeting meeting){
            start.setText(meeting.getStartTime());
            end.setText(meeting.getEndTime());
            desc.setText(meeting.getDescription());
            participants.setText(meeting.getParticipants().toString());
        }
    }
}
