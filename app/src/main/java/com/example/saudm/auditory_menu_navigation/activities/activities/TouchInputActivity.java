package com.example.saudm.auditory_menu_navigation.activities.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.saudm.auditory_menu_navigation.R;

import com.example.saudm.auditory_menu_navigation.activities.listeners.DoubleClickListener;
import com.example.saudm.auditory_menu_navigation.activities.models.TaskTracking;

import static com.example.saudm.auditory_menu_navigation.activities.constants.Constants.IS_ADAPTIVE_FEEDBACK;
import static com.example.saudm.auditory_menu_navigation.activities.constants.Constants.SINGLE_CLICKED;

public class TouchInputActivity extends AppCompatActivity{

    private RelativeLayout touchInputLayout;
    private final String TAG = TouchInputActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_input);
        initUI();
        startAdaptivaTime();
    }

    private void startAdaptivaTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(false, true);
            }
        },4000);
    }

    private void initUI() {
        touchInputLayout = findViewById(R.id.touchInputLayout);
        touchInputLayout.setOnClickListener(doubleClickListener);
    }

    private DoubleClickListener doubleClickListener = new DoubleClickListener() {

        @Override
        public void onSingleClick(View v) {
            Log.e(TAG,"User has clicked once.");
            navigate(true, false);
        }

        @Override
        public void onDoubleClick(View v) {
            Log.e(TAG,"User has clicked twice.");
            navigate(false, false);
        }
    };

    private void navigate(boolean singleClick, boolean adaptive) {

        Intent homeActivityIntent = new Intent(this, HomeActivityActivity.class);
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeActivityIntent.putExtra(SINGLE_CLICKED,singleClick);
        homeActivityIntent.putExtra(IS_ADAPTIVE_FEEDBACK,adaptive);
        startActivity(homeActivityIntent);
        finish();
    }
}
