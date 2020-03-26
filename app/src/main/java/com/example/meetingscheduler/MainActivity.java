package com.example.meetingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.example.meetingscheduler.UI.main.ListFragment;

public class MainActivity extends AppCompatActivity {

    //todo manage fragment back stack
    //navigation
    //history
    //on rotate, app killed, finish()
    //landscape UI

    //mvvm handle navigation
    private INavigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigator = Navigator.INSTANCE.init(getSupportFragmentManager()); //use DI
        if (savedInstanceState==null)
            mNavigator.toMainScreen();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        //mNavigator.toMainScreen();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        //Log.d("durga", "onConfigurationChanged");
        //orientationLand = (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
    }
}
