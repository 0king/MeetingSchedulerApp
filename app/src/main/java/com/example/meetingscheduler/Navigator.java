package com.example.meetingscheduler;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.meetingscheduler.UI.create.CreateFragment;
import com.example.meetingscheduler.UI.main.ListFragment;

public enum Navigator implements INavigator {
    INSTANCE;
    private FragmentManager manager;
    final static String TAG = "TAG_LIST_FRAGMENT";

    public Navigator init(FragmentManager fm){
        manager = fm;
        return this;
    }

    @Override
    public void toMainScreen() {

        if (manager == null)
            return;
            //throw new Exception("Please initialize the Navigator");

        Fragment fragment = manager.findFragmentByTag(TAG);
        if (fragment==null){
            fragment = new ListFragment();
        }

        manager.beginTransaction()
                .replace(R.id.containerActivity, fragment, TAG)
                .commit();
    }

    @Override
    public void toCreateScreen(String date) {
        if (manager == null)
            return;
        manager.beginTransaction()
                .replace(R.id.containerActivity, CreateFragment.newInstance(date))
                .addToBackStack(null)
                .commit();

    }
}
