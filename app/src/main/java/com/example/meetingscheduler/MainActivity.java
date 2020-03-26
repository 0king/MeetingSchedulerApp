package com.example.meetingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.example.meetingscheduler.UI.main.ListFragment;

public class MainActivity extends AppCompatActivity {

    private INavigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigator = Navigator.INSTANCE.init(getSupportFragmentManager()); //use DI
        if (savedInstanceState==null)
            mNavigator.toMainScreen();
    }

}
