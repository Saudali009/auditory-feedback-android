package com.example.saudm.auditory_menu_navigation.activities.listeners;

import android.os.Handler;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public abstract class DoubleClickListener implements View.OnClickListener {

    private Timer timer = null;
    private static final long DOUBLE_CLICK_TIME_DELTA_IN_MILLIS = 300;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA_IN_MILLIS){
            processDoubleClickEvent(v);
        } else {
            processSingleClickEvent(v);
        }
        lastClickTime = clickTime;
    }

    private void processSingleClickEvent(final View v){

        final Handler handler=new Handler();
        final Runnable mRunnable=new Runnable(){
            public void run(){
                onSingleClick(v);
            }
        };

        TimerTask timertask=new TimerTask(){
            @Override
            public void run(){
                handler.post(mRunnable);
            }
        };

        timer=new Timer();
        timer.schedule(timertask, 300);
    }

    private void processDoubleClickEvent(View v){
        if(timer!=null)
        {
            timer.cancel();
            timer.purge();
        }
        onDoubleClick(v);
    }

    public abstract void onSingleClick(View v);

    public abstract void onDoubleClick(View v);
}